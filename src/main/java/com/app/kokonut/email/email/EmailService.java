package com.app.kokonut.email.email;

import com.app.kokonut.admin.entity.Admin;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminEmailInfoDto;
import com.app.kokonut.email.email.dtos.EmailDetailDto;
import com.app.kokonut.email.email.dtos.EmailListDto;

import com.app.kokonut.email.emailGroup.EmailGroupRepository;

import com.app.kokonut.email.emailGroup.dtos.EmailGroupAdminInfoDto;
import com.app.kokonut.email.emailGroup.dtos.EmailGroupListDto;
import com.app.kokonut.email.emailGroup.EmailGroup;
import com.app.kokonut.configs.MailSender;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;

import com.app.kokonut.common.component.ReqUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
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
    public ResponseEntity<Map<String,Object>> sendEmail(String email, EmailDetailDto emailDetailDto){
        log.info("### sendEmail 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // 접속한 사용자 인덱스
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+email));
        emailDetailDto.setSenderadminId(admin.getIdx());

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

        // 이메일 전송을 위한 준비 - reciverType에 따른 adminIdList 구하기
        String receiverType = emailDetailDto.getReceiverType();
        String adminIdList = "";

        if("I".equals(receiverType)){
            adminIdList = emailDetailDto.getReceiveradminIdList().toString();
        }else if(("G").equals(receiverType)){
            Long emailGroupIdx = emailDetailDto.getEmailGroupIdx();
            EmailGroupAdminInfoDto emailGroupAdminInfoDto;
            emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(emailGroupIdx);
            adminIdList = emailGroupAdminInfoDto.getadminIdList();
        }else{
            log.error("### 받는사람 타입(I:개별,G:그룹)을 알 수 없습니다. :" + receiverType);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO040.getCode(), ResponseErrorCode.KO040.getDesc()));
        }

        // mailSender 실질적인 이메일 전송 부분
        String[] toks = adminIdList.split(",");
        for(String tok : toks){
            AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Long.valueOf(tok));
            if(adminEmailInfoDto != null){
                String reciverEmail = adminEmailInfoDto.getEmail();
                String reciverName = adminEmailInfoDto.getName();

                log.info("### mailSender을 통해 건별 이메일 전송 시작");
                log.info("### reciver idx : "+tok + ", senderEmail : " +email+", reciverEmail : "+ reciverEmail);
                boolean mailSenderResult = mailSender.sendMail(reciverEmail, reciverName, title, contents);
                if(mailSenderResult){
                    // mailSender 성공
                    log.error("### 메일전송 성공했습니다.. reciver admin idx : "+ tok);
                }else{
                    // mailSender 실패
                    log.error("### 해당 메일 전송에 실패했습니다. 관리자에게 문의하세요. reciver admin idx : "+ tok+", reciverEmail : "+ reciverEmail);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO041.getCode(), ResponseErrorCode.KO041.getDesc()));
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
        reciveEmail.setSenderadminId(emailDetailDto.getSenderadminId());
        reciveEmail.setReceiverType(emailDetailDto.getReceiverType());
        reciveEmail.setTitle(emailDetailDto.getTitle());
        reciveEmail.setContents(emailDetailDto.getContents());

        // 조건에 따른 분기 처리
        if("I".equals(emailDetailDto.getReceiverType()) && emailDetailDto.getReceiveradminIdList() != null) {
            reciveEmail.setReceiveradminIdList(emailDetailDto.getReceiveradminIdList());
        }

        // 조건에 따른 분기 처리
        if("G".equals(emailDetailDto.getReceiverType()) && emailDetailDto.getEmailGroupIdx() != null) {
            reciveEmail.setEmailGroupIdx(emailDetailDto.getEmailGroupIdx());
        }
        reciveEmail.setRegdate(LocalDateTime.now());

        // save or update
        Email sendEmail = emailRepository.save(reciveEmail);

        log.info("### 이메일 이력 저장 처리 완료");

        // TODO 정상적으로 저장된 경우를 확인하는 방법 알아보기. save 처리가 되던 update 처리가 되던 결과적으로 해당 인덱스는 존재함.
        // sendEamil 객체에서 reciverType에 따라 어드민 인덱스를 조회, 해당 인덱스로 어드민 이메일을 확인한 다음 해당 이메일로 받는 내역을 조회한 다음. 해당 건수가 존재하면 받은걸로 친다고하기엔.
        // 하지만 이런 방법으로 할 경우 이전
        if(emailRepository.existsByIdx(sendEmail.getIdx())){
            log.info("### 이메일 이력 저장에 성공했습니다. : "+sendEmail.getIdx());
            return ResponseEntity.ok(res.success(data));
        }else{
            log.error("### 이메일 이력 저장에 실패했습니다. : "+sendEmail.getIdx());
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO041.getCode(), ResponseErrorCode.KO041.getDesc()));
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
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getDesc()));
        } else {
            log.info("### 이메일 상세보기 idx : "+idx);
            // 이메일 인덱스로 이메일 정보 조회
            EmailDetailDto emailDetailDto = emailRepository.findEmailByIdx(idx);
            if(emailDetailDto != null){
                String receiverType = emailDetailDto.getReceiverType();
                String adminIdList = "";
                if("I".equals(receiverType)){
                    // 개별 선택으로 발송한 경우
                    adminIdList = emailDetailDto.getReceiveradminIdList().toString();
                }else if("G".equals(receiverType)){
                    // 그룹 선택으로 메일을 발송한 경우
                    Long emailGroupIdx = emailDetailDto.getEmailGroupIdx();
                    // 메일 그룹 조회 쿼리 동작
                    EmailGroupAdminInfoDto emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(emailGroupIdx);
                    adminIdList = emailGroupAdminInfoDto.getadminIdList();
                }else{
                    log.error("### 받는사람 타입(I:개별,G:그룹)을 알 수 없습니다. :" + receiverType);
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO040.getCode(), ResponseErrorCode.KO040.getDesc()));
                }

                // 받는 사람 이메일 문자열 조회
                StringBuilder emailList = new StringBuilder();
                String[] toks = adminIdList.split(",");
                for(int i = 0; i < toks.length; i++) {
                    String tok = toks[i];
                    AdminEmailInfoDto adminEmailInfoDto = adminRepository.findByEmailInfo(Long.valueOf(tok));
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
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getDesc()));
            }
            return ResponseEntity.ok(res.success(data));
        }
    }

    /**
     * 이메일 발송 대상 목록 조회하기
     */
    public ResponseEntity<Map<String, Object>> emailTargetGroupList(Pageable pageable) {
        log.info("### emailTargetGroupList 호출");
        /*
         * 그룹명 : 미식플랫폼 00 관리자
         * 설 명 : 미식플랫폼 00의 관리자 그룹
         * 관리자 Idx : 2, 4, 5 ...
         * 이메일 : a001@00.oo.com, a002@00.oo.com, a003@00.oo.com
         *
         * findEmailGroupDatils()를 조회한다
         * 해당 결과에서 가져온 관리자 인덱스를 가지고 관리자 이메일을 조회한다.
         * EmailGroup Entity 클래스를 가지는 List에 각 값을 넣어서 던져준다.
         */

        AjaxResponse res = new AjaxResponse();

        EmailGroup emailGroup = new EmailGroup(); // TEMP
        List<EmailGroupListDto> resultDto = emailGroupRepository.findEmailGroupDetails();

        List<EmailGroup> resultList = new ArrayList<>();
        for(int i = 0; i<resultDto.size(); i++){
            emailGroup.setIdx(resultDto.get(i).getIdx());
            emailGroup.setName(resultDto.get(i).getName());
            emailGroup.setDesc(resultDto.get(i).getDesc());
            emailGroup.setadminIdList(resultDto.get(i).getadminIdList());

            String adminIds = resultDto.get(i).getadminIdList();
            String adminIdList[] = adminIds.split(",");

            List<String> emailList = new ArrayList<>();
            for (String adminId : adminIdList) {
                String adminEmail = adminRepository.findByEmailInfo(Long.parseLong(adminId)).getEmail();
                emailList.add(adminEmail); // a001@00.oo.com, a002@00.oo.com, a003@00.oo.com
            }
            StringBuilder adminEmailList = new StringBuilder();
            for(int j = 0; j < emailList.size(); j++){
                adminEmailList.append(emailList.get(j));
                if(j == emailList.size()-1){
                    // 마지막
                    adminEmailList.append("");
                }else {
                    adminEmailList.append(",");
                }
            }
            String stAdminEmailList = adminEmailList.toString();
            emailGroup.setAdminEmailList(stAdminEmailList);
            resultList.add(emailGroup);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
            System.out.println(">>>> idx: "+resultList.get(i).getIdx());
            System.out.println(">>>> name: "+resultList.get(i).getName());
            System.out.println(">>>> Desc: "+resultList.get(i).getDesc());
            System.out.println(">>>> adminId: "+resultList.get(i).getadminIdList());
            System.out.println(">>>> adminEmail: "+resultList.get(i).getAdminEmailList());
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
        }
        

        Page resultPage = new PageImpl<>(resultList, pageable, resultList.size());
        System.out.println("결과 List size : "+resultList.size());

    return ResponseEntity.ok(res.ResponseEntityPage(resultPage));
    }
}
