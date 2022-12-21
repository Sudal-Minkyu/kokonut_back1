package com.app.kokonut.email;

import com.app.kokonut.email.dto.EmailListDto;
import com.app.kokonut.email.dto.EmailDto;

import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * 이메일 목록 조회
     */
    public ResponseEntity<Map<String,Object>> getEmail(Pageable pageable){
        log.info("getEmail 호출");

        AjaxResponse res = new AjaxResponse();
        Page<EmailListDto> emailListDtos = emailRepository.findByEmailPage(pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(emailListDtos));
    }

    /**
     * 이메일 상세보기
     * @param idx email idx
     */
    public ResponseEntity<Map<String,Object>> sendEmailDetail(Integer idx){
        log.info("sendEmailDetail 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        // woody
        // idx null 일경우 빠져나올 수 있게 해주시는게 좋아요.
        // log.info, info.error 적극 활용해서 로그 남겨주세요~ ex) 조회성공 할 경우, 저장성공할경우, 실패할경우 등..
        if(idx == null){
            log.error("이메일 호출할 idx가 존재 하지 않습니다. : "+idx);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        } else {
            log.info("이메일 상세보기 idx : "+idx);

            // 이메일 인덱스로 이메일 정보 조회
            EmailDto emailDto = emailRepository.findEmailByIdx(idx); // emailDto 값이 null일 경우 널포인트익셉션이 떨어지니 null체크 추가해주세요.(DB에 idx값이 존재하지 않을 경우)

            if(emailDto != null){
                String receiverType = emailDto.getReceiverType();
                String adminIdxList = "";
                if(receiverType.equals("I")){
                    // 개별 선택으로 발송한 경우
                    adminIdxList = emailDto.getReceiverAdminIdxList().toString();
                }else if(receiverType.equals("G")){
                    // 그룹 선택으로 메일을 발송한 경우
                    String emailGroupIdx = emailDto.getEmailGroupIdx().toString();
                    // 메일 그룹 조회 쿼리 동작
                    // TODO :: emailGroup 관련하여 entity, dto, repository, restController, service 리팩토링 작업 후 추가.
                    // HashMap<String, Object> emailGroup = emailGroupService.SelectEmailGroupByIdx(Integer.parseInt(emailGroupIdx));
                    // adminIdxList = emailGroup.get("ADMIN_IDX_LIST").toString();

                }else{
                    log.error("unknown receiver type: {}", receiverType);
                }
                String toks[] = adminIdxList.split(",");

                for(int i = 0; i < toks.length; i++) {
                    String tok = toks[i];
                    // TODO :: adminDto에서 이메일 꺼내서 세팅, 아래 기존 코코넛 소스
                    //AdminDto adminDto = adminRepository.findById(Integer.valueOf(tok));
                    //HashMap<String, Object> admin = adminService.SelectAdminByIdx(Integer.parseInt(tok));
                    //if(admin != null) {
                    //    emailList.append(admin.get("EMAIL").toString());
                    // if(i < toks.length - 1) {
                    // emailList.append(", ");}
                    // }
                }

                //data.put("EMAIL_LIST", "");
            } else {
                log.error("이메일 정보가 존재 하지 않습니다. : "+idx);
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
            }

            return ResponseEntity.ok(res.success(data));

        }
    }

}
