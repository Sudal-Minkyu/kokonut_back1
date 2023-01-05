package com.app.kokonut.setting;

import com.app.kokonut.woody.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Joy
 * Date : 2023-01-05
 * Time :
 * Remark : SettingService 관리자 환경설정 서비스
 */
@Slf4j
@Service
public class SettingService {
    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();
    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public ResponseEntity<Map<String, Object>> details() {
        log.info("");
        if(1==1){

        }else{

        }
        return ResponseEntity.ok(res.success(data));
    }
}
