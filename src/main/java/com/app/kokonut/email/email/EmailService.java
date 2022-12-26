package com.app.kokonut.email.email;

import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.auth.jwt.util.SecurityUtil;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.email.email.dto.EmailDetailDto;
import com.app.kokonut.email.email.dto.EmailListDto;

import com.app.kokonut.email.email.entity.Email;
import com.app.kokonut.email.emailGroup.EmailGroupRepository;

import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;
import com.app.kokonut.joy.email.MailSender;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;

import com.app.kokonut.joy.common.component.ReqUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailService {

    @Value("${otp.hostUrl}")
    private String hostUrl;
    private final EmailGroupRepository emailGroupRepository;
    private final AdminRepository adminRepository;
    private final EmailRepository emailRepository;
    private final MailSender mailSender;

    @Autowired
    public EmailService(EmailRepository emailRepository,
                        AdminRepository adminRepository,
                        EmailGroupRepository emailGroupRepository, MailSender mailSender) {
        this.emailRepository = emailRepository;
        this.adminRepository = adminRepository;
        this.emailGroupRepository = emailGroupRepository;
        this.mailSender = mailSender;
    }

    /**
     * 이메일 목록 조회
     */
    public ResponseEntity<Map<String,Object>> emailList(Pageable pageable){
        log.info("### emailList 호출");

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
        HashMap<String, Object> data = new HashMap<>();

        // 접속한 사용자 이메일
        String email = SecurityUtil.getCurrentUserEmail();
        // 접속한 사용자 인덱스
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
        emailDetailDto.setSenderAdminIdx(admin.getIdx());

        // 이메일 전송을 위한 전처리 - filter, unfilter
        String title = ReqUtils.filter(emailDetailDto.getTitle());
        String originContents = ReqUtils.filter(emailDetailDto.getContents()); // ReqUtils.filter 처리 <p> -- > &lt;p&gt;, html 태그를 DB에 저장하기 위해 이스케이프문자로 치환
        String contents = ReqUtils.unFilter(emailDetailDto.getContents()); // &lt;br&gt;이메일내용 --> <br>이메일내용, html 화면에 뿌리기 위해 특수문자를 치환
        log.info("### unFilter After content : " + contents);

        // 이메일 전송을 위한 전처리 - 첨부 이미지 경로 처리
        String imgSrcToken = "src=\"";
        int index = contents.indexOf(imgSrcToken);
        if(index > -1){
            StringBuilder sb = new StringBuilder();
            sb.append(contents);
            sb.insert(index + imgSrcToken.length(), hostUrl);
            contents = sb.toString();
        }

        // 이메일 전송을 위한 준비 - reciverType에 따른 adminIdxList 구하기
        String receiverType = emailDetailDto.getReceiverType();
        String adminIdxList = "";

        if(receiverType.equals("I")){
            adminIdxList = emailDetailDto.getReceiverAdminIdxList().toString();
        }else if(receiverType.equals("G")){
            Integer emailGroupIdx = emailDetailDto.getEmailGroupIdx();
            EmailGroupAdminInfoDto emailGroupAdminInfoDto;
            emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(emailGroupIdx);
            adminIdxList = emailGroupAdminInfoDto.getAdminIdxList();
        }else{
            log.error("### 받는사람 타입(I:개별,G:그룹)을 알 수 없습니다. :" + receiverType);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        }

        // mailSender 실질적인 이메일 전송 부분
        String[] toks = adminIdxList.split(",");
        for(String tok : toks){
            AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Integer.valueOf(tok));
            if(adminEmailInfoDto != null){
                String reciverEmail = adminEmailInfoDto.getEmail();
                String reciverName = adminEmailInfoDto.getName();

                log.info("### mailSender을 통해 건별 이메일 전송 시작");
                log.info("### reciver idx : "+tok + ", senderEmail : " +email+", reciverEmail : "+ reciverEmail);
                boolean mailSenderResult = mailSender.sendMail(reciverEmail, reciverName, title, contents);
                if(mailSenderResult){
                    // mailSender 성공
                    log.error("### 해당 idx에 해당하는 회원 이메일을 찾을 수 없습니다. reciver admin idx : "+ tok);
                }else{
                    // mailSender 실패
                    log.error("### 해당 메일 전송에 실패했습니다. 관리자에게 문의하세요. reciver admin idx : "+ tok+", reciverEmail : "+ reciverEmail);
                }
            }else{
                // TODO 일부가 탈퇴하고 일부는 이메일 정보가 있을때 처리에 대한 고민
                log.error("### 해당 idx에 해당하는 회원 이메일을 찾을 수 없습니다. reciver admin idx : "+ tok);
            }
        }

        // 전송 이력 저장 처리 - originContents로 DB 저장
        log.info("### 이메일 이력 저장 처리");
        Email reciveEmail = new Email();

        emailDetailDto.setContents(originContents);
        reciveEmail.setEmailGroupIdx(emailDetailDto.getSenderAdminIdx());
        reciveEmail.setReceiverType(emailDetailDto.getReceiverType());
        reciveEmail.setTitle(emailDetailDto.getTitle());
        reciveEmail.setContents(emailDetailDto.getContents());

        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("I")
                && emailDetailDto.getReceiverAdminIdxList() != null
                && emailDetailDto.getReceiverAdminIdxList().equals("")){
            reciveEmail.setReceiverAdminIdxList(emailDetailDto.getReceiverAdminIdxList());
        }
        // 조건에 따른 분기 처리
        if(emailDetailDto.getReceiverType().equals("G")
                && emailDetailDto.getEmailGroupIdx() != null
                && !emailDetailDto.getEmailGroupIdx().equals(0)){
            reciveEmail.setEmailGroupIdx(emailDetailDto.getEmailGroupIdx());
        }


        // save or update
        Email sendEmail = emailRepository.save(reciveEmail);

        log.info("### 이메일 이력 저장 처리 완료");

        // TODO 정상적으로 저장된 경우를 확인하는 방법 알아보기. save 처리가 되던 update 처리가 되던 결과적으로 해당 인덱스는 존재함.
        // sendEamil 객체에서 reciverType에 따라 어드민 인덱스를 조회, 해당 인덱스로 어드민 이메일을 확인한 다음 해당 이메일로 받는 내역을 조회한 다음. 해당 건수가 존재하면 받은걸로 친다고하기엔.
        // 하지만 이런 방법으로 할 경우 이전
        if(emailRepository.existsById(sendEmail.getIdx())){
            log.info("### 이메일 이력 저장에 성공했습니다. : "+sendEmail.getIdx());
            return ResponseEntity.ok(res.success(data));
        }else{
            log.error("### 이메일 이력 저장에 실패했습니다. : "+sendEmail.getIdx());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        }

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
                    }else{ // kokonut_1@kokonut.me, kokonut_2@kokonut.me, 탈퇴한 사용자, kokonut_4@kokonut.me 형태로 이메일 문자열 반환, 추후 변경될 수 있음.
                        emailList.append("탈퇴한 사용자");
                        if(i < toks.length - 1) {
                            emailList.append(", ");
                        }
                    }
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
