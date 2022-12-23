package com.app.kokonut.email.emailGroup;

import com.app.kokonut.email.emailGroup.dto.EmailGroupAdminInfoDto;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
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
     * 메일 그룹 adminIdxList 조회
     * @param idx - email_group IDX
     */
    public ResponseEntity<Map<String,Object>> findEmailGroupAdminIdxByIdx(Integer idx){
        log.info("findEmailGroupAdminIdxByIdx 호출");
        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        if(idx == null){
            log.error("해당 메일 그룹을 찾을 수 없습니다. : "+idx);
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO031.getCode(), ResponseErrorCode.KO031.getCode()));
        }else{
            EmailGroupAdminInfoDto emailGroupAdminInfoDto = emailGroupRepository.findEmailGroupAdminInfoByIdx(idx);
            data.put("adminIdxList", emailGroupAdminInfoDto.getAdminIdxList());

            return ResponseEntity.ok(res.success(data));
        }
    }
}
