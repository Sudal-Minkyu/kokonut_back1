package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.dtos.EmailGroupAdminInfoDto;
import com.app.kokonut.email.emailGroup.dtos.EmailGroupDetailDto;
import com.app.kokonut.email.emailGroup.dtos.EmailGroupListDto;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class EmailGroupService {
    private final EmailGroupRepository emailGroupRepository;

    public EmailGroupService(EmailGroupRepository emailGroupRepository) {
        this.emailGroupRepository = emailGroupRepository;
    }

    /***
     * 메일 그룹 adminIdList 조회
     * @param idx - email_group IDX
     * 기존 코코넛 서비스 SelectEmailGroupByIdx
     */
    public ResponseEntity<Map<String,Object>> emailGroupDetail(Integer idx){
        log.info("### findEmailGroupadminIdByIdx 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(idx == null){
            log.error("### 해당 메일 그룹을 찾을 수 없습니다. : "+idx);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO042.getCode(), ResponseErrorCode.KO042.getDesc()));
        }else{
            EmailGroupAdminInfoDto emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(idx);
            data.put("adminIdList", emailGroupAdminInfoDto.getadminIdList());

            return ResponseEntity.ok(res.success(data));
        }
    }

    /***
     * 메일 그룹 목록 조회
     * 기존 코코넛 서비스 SelectEmailGroupList
     */
    public ResponseEntity<Map<String,Object>> emailGroupList(Pageable pageable){
        log.info("### emailGroupList 호출");

        AjaxResponse res = new AjaxResponse();
        Page<EmailGroupListDto> emailGroupListDto = emailGroupRepository.findEmailGroupPage(pageable);

        return ResponseEntity.ok(res.ResponseEntityPage(emailGroupListDto));
    }


    /***
     * 메일 그룹 등록
     * 기존 코코넛 서비스 InsertEmailGroup
     */
    public ResponseEntity<Map<String,Object>> saveEmailGroup(EmailGroupDetailDto emailGroupDetailDto) {
        log.info("### saveEmailGroup 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("### 이메일 그룹 저장");
        EmailGroup newEmailGroup =  new EmailGroup();
        newEmailGroup.setName(emailGroupDetailDto.getName());
        newEmailGroup.setDesc(emailGroupDetailDto.getDesc());
        newEmailGroup.setadminIdList(emailGroupDetailDto.getadminIdList());
        emailGroupRepository.save(newEmailGroup);

        return ResponseEntity.ok(res.success(data));
    }

    /***
     * 메일 그룹 삭제
     * @param idx - email_group IDX
     * 기존 코코넛 서비스 DeleteEmailGroupUseYn
     */
    public ResponseEntity<Map<String,Object>> deleteEmailGroup(Integer idx) {
        log.info("### deleteEmailGroup 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        if(idx == null){
            log.error("### 삭제할 이메일 그룹의 idx가 존재하지 않습니다. : "+idx);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO042.getCode(), ResponseErrorCode.KO042.getDesc()));
        } else {
            log.info("### 이메일 그룹 삭제");
            EmailGroup deleteEmailGroup =  new EmailGroup();
            deleteEmailGroup.setUseYn("N");
            deleteEmailGroup.setIdx(idx);
            emailGroupRepository.save(deleteEmailGroup);
            log.info("### 이메일 그룹 삭제, 삭제된 이메일 그룹 idx : " + idx);
            return ResponseEntity.ok(res.success(data));
        }
    }

    /***
     * 메일 그룹 수정
     * 기존 코코넛 서비스 UpdateEmailGroup
     */
    public ResponseEntity<Map<String,Object>> UpdateEmailGroup(EmailGroupDetailDto emailGroupDetailDto) {
        log.info("### UpdateEmailGroup 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        log.info("### 이메일 그룹 수정");
        EmailGroup updateEmailGroup =  new EmailGroup();
        updateEmailGroup.setIdx(emailGroupDetailDto.getIdx());
        updateEmailGroup.setName(emailGroupDetailDto.getName());
        updateEmailGroup.setDesc(emailGroupDetailDto.getDesc());
        updateEmailGroup.setadminIdList(emailGroupDetailDto.getadminIdList());
        emailGroupRepository.save(updateEmailGroup);
        log.info("### 이메일 그룹 수정, 수정된 이메일 그룹 idx : " + emailGroupDetailDto.getIdx());
        return ResponseEntity.ok(res.success(data));

    }

}
