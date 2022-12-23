package com.app.kokonut.email.email;

import com.app.kokonut.auth.jwt.util.SecurityUtil;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.dto.EmailListDto;

import com.app.kokonut.email.emailGroup.EmailGroupRepository;

import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;

import com.app.kokonut.joy.common.component.ReqUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {
    //이메일 호스트
    @Value("${otp.hostUrl}")
    private String hostUrl;
    private final EmailGroupRepository emailGroupRepository;
    private final AdminRepository adminRepository;

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository,
                        AdminRepository adminRepository,
                        EmailGroupRepository emailGroupRepository) {
        this.emailRepository = emailRepository;
        this.adminRepository = adminRepository;
        this.emailGroupRepository = emailGroupRepository;
    }

    /**
     * 이메일 목록 조회
     */
    public ResponseEntity<Map<String,Object>> getEmail(Pageable pageable){
        log.info("### getEmail 호출");

        AjaxResponse res = new AjaxResponse();
        Page<EmailListDto> emailListDtos = emailRepository.findByEmailPage(pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(emailListDtos));
    }

    /**
     * 이메일 보내기
     */
    @Transactional
    public ResponseEntity<Map<String,Object>> sendEmail(EmailDetailDto emailDetailDto){
        log.info("### sendEmail 호출");
        AjaxResponse res = new AjaxResponse();
        // 접속한 사용자 이메일
        String email = SecurityUtil.getCurrentUserEmail();
        // 이메일 전송을 위한 전처리
        String title = ReqUtils.filter(emailDetailDto.getTitle());
        String originContents = ReqUtils.filter(emailDetailDto.getContents()); // ReqUtils.filter 처리 <p> -- > &lt;p&gt;, html 태그를 DB에 저장하기 위해 이스케이프문자로 치환
        String contents = ReqUtils.unFilter(emailDetailDto.getContents()); // &lt;br&gt;이메일내용 --> <br>이메일내용, html 화면에 뿌리기 위해 특수문자를 치환
        log.info("### unFilter After content : " + contents);
        String imgSrcToken = "src=\""; // TODO 메일에 이미지 첨부가 있을때 처리
        int index = contents.indexOf(imgSrcToken);
        if(index > -1){
            StringBuilder sb = new StringBuilder();
            sb.append(contents);
            sb.insert(index + imgSrcToken.length(), hostUrl);
            contents = sb.toString();
        }
        log.info("### img Operating content : " + contents);

        // 이메일 전송 처리 - contents로 메일전송
        String receiverType = emailDetailDto.getReceiverType();
        String adminIdxList = "";
        if(receiverType.equals("I")){
            // 개별 선택으로 발송한 경우
            adminIdxList = emailDetailDto.getReceiverAdminIdxList().toString();
        }else if(receiverType.equals("G")){
            // 그룹 선택으로 메일을 발송한 경우
            Integer emailGroupIdx = emailDetailDto.getEmailGroupIdx();
            // 메일 그룹 조회 쿼리 동작

            EmailGroupAdminInfoDto emailGroupAdminInfoDto;
            emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(emailGroupIdx);
            adminIdxList = emailGroupAdminInfoDto.getAdminIdxList();
        }else{
            log.error("### 받는사람 타입(I:개별,G:그룹)을 알 수 없습니다. :" + receiverType);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        }
        String[] toks = adminIdxList.split(",");
        for(String tok : toks){
            AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Integer.valueOf(tok));
            if(adminEmailInfoDto != null){
                String reciverEmail =  adminEmailInfoDto.getEmail();
            }
        }
        for(int i = 0; i < toks.length; i++) {
            String tok = toks[i];
            AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Integer.valueOf(tok)); // TODO name 도 받아오는걸로 쿼리 수정하기

            if(adminEmailInfoDto != null){
                // String email = adminEmailInfoDto.getEmail();
                // String name  = adminEmailInfoDto.getName();
                // mailSender.sendMail(email, name, title, contents);
            }else{
                // TODO 일부가 탈퇴하고 일부는 이메일 정보가 있을때 처리. 만약 이렇게 되면 바로 리턴이 되버림.
                log.error("### 이메일 정보가 존재 하지 않습니다. : " + tok);
                // return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
            }
        }
        // 전송 이력 저장 처리 - originContents로 DB 저장

        return null;
    }


    /**
     * 이메일 상세보기
     * @param idx email의 idx
     */
    public ResponseEntity<Map<String,Object>> sendEmailDetail(Integer idx){
        log.info("### sendEmailDetail 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        if(idx == null){
            log.error("### 이메일 호출할 idx가 존재 하지 않습니다. : "+idx);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        } else {
            log.info("### 이메일 상세보기 idx : "+idx);
            // 이메일 인덱스로 이메일 정보 조회
            EmailDetailDto emailDetailDto = emailRepository.findEmailByIdx(idx);
            if(emailDetailDto != null){
                String receiverType = emailDetailDto.getReceiverType();
                String adminIdxList = "";
                if(receiverType.equals("I")){
                    // 개별 선택으로 발송한 경우
                    adminIdxList = emailDetailDto.getReceiverAdminIdxList().toString();
                }else if(receiverType.equals("G")){
                    // 그룹 선택으로 메일을 발송한 경우
                    Integer emailGroupIdx = emailDetailDto.getEmailGroupIdx();
                    // 메일 그룹 조회 쿼리 동작
                    EmailGroupAdminInfoDto emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(emailGroupIdx);
                    adminIdxList = emailGroupAdminInfoDto.getAdminIdxList();
                }else{
                    log.error("### 받는사람 타입(I:개별,G:그룹)을 알 수 없습니다. :" + receiverType);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
                }

                // 받는 사람 이메일 문자열 조회
                StringBuilder emailList = new StringBuilder();
                String[] toks = adminIdxList.split(",");
                for(int i = 0; i < toks.length; i++) {
                    String tok = toks[i];
                    AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Integer.valueOf(tok));
                    if(adminEmailInfoDto != null){
                        emailList.append(adminEmailInfoDto.getEmail());
                        if(i < toks.length - 1) {
                            emailList.append(", ");
                        }
                    }else{
                        emailList.append("탈퇴한 사용자");
                        if(i < toks.length - 1) {
                            emailList.append(", ");
                        }
                    }
                    // --> kokonut_1@kokonut.me, kokonut_2@kokonut.me, 탈퇴한 사용자, kokonut_4@kokonut.me 형태로 이메일 문자열 반환, 추후 변경될 수 있음.
                }
                data.put("emailList", emailList); // 받는 사람 이메일 문자열
                data.put("title", emailDetailDto.getTitle()); // 제목
                data.put("contents", emailDetailDto.getContents()); // 내용
            } else {
                log.error("### 이메일 정보가 존재 하지 않습니다. : "+idx);
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
            }
            return ResponseEntity.ok(res.success(data));
        }
    }


}
