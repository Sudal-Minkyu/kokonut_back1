package com.app.kokonut.admin;

import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : AdminService + 인증서비스
 */
@Slf4j
@Service
public class AdminService {

    private final AjaxResponse res = new AjaxResponse();
    private final HashMap<String, Object> data = new HashMap<>();

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 유저의 권한 및 jwt 토큰확인
    public ResponseEntity<Map<String,Object>> authorityCheck(String userEmail) {
        log.info("authorityCheck 호출");

        if(userEmail.equals("anonymousUser")){
            log.error("사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO009.getCode(),ResponseErrorCode.KO009.getDesc()));
        } else{
            log.info("관리자로 바꿀 이메일 : "+userEmail);

            Admin admin = adminRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : "+userEmail));

            log.info("해당 유저의 권한 : "+admin.getARoleCode().getDesc());
        }

        return ResponseEntity.ok(res.success(data));
    }

}
