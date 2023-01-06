package com.app.kokonut.setting;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.setting.dto.SettingDetailDto;
import com.app.kokonut.setting.entity.Setting;
import com.app.kokonut.woody.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private final AdminRepository adminRepository;

    public SettingService(SettingRepository settingRepository, AdminRepository adminRepository) {
        this.settingRepository = settingRepository;
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<Map<String, Object>> settingDetail(String userRole, String email) {
        log.info("settingDetail 호출, userRole : " + userRole);

        // 접속정보에서 companyIdx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
        Integer companyIdx = admin.getCompanyIdx();

        log.info("관리자 환경 설정 상세 조회. companyIdx : " +companyIdx);
        SettingDetailDto settingDetailDto = settingRepository.findSettingDetailByCompanyIdx(companyIdx);

        if(settingDetailDto.getOverseasBlock() == null){
            log.info("해외차단 아이피 기본값 세팅 0, 차단안함");
            // 해외차단 아이피 기본값 세팅 0 : (차단안함);
            settingDetailDto.setOverseasBlock(0);
        }
        if(settingDetailDto.getDormantAccount() == null){
            log.info("휴면회원 전환 시 정보처리방식 기본값 세팅 1, 이관 없이 삭제");
            // 휴면회원 전환 시 정보처리 기본값 세팅 1 : (이관 없이 삭제)
            settingDetailDto.setDormantAccount(1);
        }
        if("[SYSTEM]".equals(userRole)){
            // 세션타임 조회
            // data.put("", "")
        }

        data.put("settingDetailDto", settingDetailDto);
        return ResponseEntity.ok(res.success(data));
    }

    public ResponseEntity<Map<String, Object>> settingSave(String userRole, String email, SettingDetailDto settingDetailDto) {
        log.info("settingSave 호출, userRole : " + userRole);
        // 접속정보에서 companyIdx 가져오기
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
        Integer companyIdx = admin.getCompanyIdx();

        if(settingDetailDto.getIdx() == null){
            // 신규 등록
            Setting setting = new Setting();
            setting.setRegdate(LocalDateTime.now());
            setting.setModifyDate(LocalDateTime.now());
            setting.setCompanyIdx(companyIdx);
            setting.setDormantAccount(settingDetailDto.getDormantAccount()); // 휴먼회원 전환 시 DB 이관 여부
            setting.setOverseasBlock(settingDetailDto.getOverseasBlock()); // 관리자 해외 로그인 차단 설정
            settingRepository.save(setting);
        }else{
            // 수정
            Optional<Setting> setting = settingRepository.findById(settingDetailDto.getIdx());
            setting.get().setModifyDate(LocalDateTime.now());
            setting.get().setDormantAccount(settingDetailDto.getDormantAccount()); // 휴먼회원 전환 시 DB 이관 여부
            setting.get().setOverseasBlock(settingDetailDto.getOverseasBlock()); // 관리자 해외 로그인 차단 설정
            settingRepository.save(setting.get());
        }
        return ResponseEntity.ok(res.success(data));
    }

//    public ResponseEntity<Map<String, Object>> settingDelete(String userRole, String email) {
//        log.info("settingSave 호출, userRole : " + userRole);
//        // 접속정보에서 companyIdx 가져오기
//        Admin admin = adminRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다. : " + email));
//        Integer companyIdx = admin.getCompanyIdx();
//
//        Integer settingdIdx = settingRepository.findSettingDetailByCompanyIdx(companyIdx).getIdx();
//        settingRepository.deleteById(settingdIdx);
//
//        return ResponseEntity.ok(res.success(data));
//    }
}
