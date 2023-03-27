package com.app.kokonut.admin;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminInfoDto;
import com.app.kokonut.admin.dtos.AdminMyInfoDto;
import com.app.kokonut.auth.jwt.dto.JwtFilterDto;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.common.component.CommonUtil;
import com.app.kokonut.company.Company;
import com.app.kokonut.company.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : AdminService + 인증서비스
 */
@Slf4j
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final CompanyRepository companyRepository;
    private final ActivityHistoryService activityHistoryService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, CompanyRepository companyRepository,
                        ActivityHistoryService activityHistoryService, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.companyRepository = companyRepository;
        this.activityHistoryService = activityHistoryService;
        this.passwordEncoder = passwordEncoder;
    }

    // 마이페이지(내정보) 데이터 호출
    public ResponseEntity<Map<String, Object>> myInfo(JwtFilterDto jwtFilterDto) {
        log.info("myInfo 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = jwtFilterDto.getEmail();

        log.info("마이페이지 조회 이메일 : "+email);
        AdminMyInfoDto adminMyInfoDto = adminRepository.findByAdminMyInfo(email);
        log.info("adminMyInfoDto : "+adminMyInfoDto);
        if(adminMyInfoDto != null) {
            data.put("knEmail",adminMyInfoDto.getKnEmail());
            data.put("knName",adminMyInfoDto.getKnName());
            data.put("knPhoneNumber",adminMyInfoDto.getKnPhoneNumber());
            data.put("cpName",adminMyInfoDto.getCpName());
            data.put("knDepartment",adminMyInfoDto.getKnDepartment());
        } else{
            log.error("사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO009.getCode(),ResponseErrorCode.KO009.getDesc()));
        }

        return ResponseEntity.ok(res.success(data));
    }

    // 휴대전화번호 변경
    @Transactional
    public ResponseEntity<Map<String, Object>> phoneChange(String knName, String knPhoneNumber, JwtFilterDto jwtFilterDto) {
        log.info("phoneChange 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = jwtFilterDto.getEmail();

        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(jwtFilterDto.getEmail());
        Long adminId = adminCompanyInfoDto.getAdminId();
//        Long companyId = adminCompanyInfoDto.getCompanyId();
        String companyCode = adminCompanyInfoDto.getCompanyCode();

        // 휴대전화변경 코드
        ActivityCode activityCode = ActivityCode.AC_35;
        // 활동이력 저장 -> 비정상 모드
        String ip = CommonUtil.clientIp();

        log.info("휴대전화번호 변경 이메일 : "+email);

        Optional<Admin> optionalAdmin = adminRepository.findByKnEmail(email);
        if(optionalAdmin.isPresent()) {
            Long activityHistoryId = activityHistoryService.insertActivityHistory(4, adminId, activityCode,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0, jwtFilterDto.getEmail());

            optionalAdmin.get().setKnName(knName);
            optionalAdmin.get().setKnPhoneNumber(knPhoneNumber);
            optionalAdmin.get().setModify_email(email);
            optionalAdmin.get().setModify_date(LocalDateTime.now());
            adminRepository.save(optionalAdmin.get());

            activityHistoryService.updateActivityHistory(activityHistoryId,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", 1);
        } else{
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }

        return ResponseEntity.ok(res.success(data));
    }

    // 소속명 변경
    @Transactional
    public ResponseEntity<Map<String, Object>> cpChange(String cpContent, String knPassword, Integer state, JwtFilterDto jwtFilterDto) {
        log.info("cpChange 호출");

        log.info("변경내용 : "+cpContent);
        log.info("state : "+state);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = jwtFilterDto.getEmail();

        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(jwtFilterDto.getEmail());
        Long adminId = adminCompanyInfoDto.getAdminId();
//        Long companyId = adminCompanyInfoDto.getCompanyId();
        String companyCode = adminCompanyInfoDto.getCompanyCode();

        // 활동 코드
        ActivityCode activityCode;
        if(state == 1) {
            // 소속명 변경
            activityCode = ActivityCode.AC_36;
        } else {
            // 부서 변경/등록
            activityCode = ActivityCode.AC_37;
        }

        String ip = CommonUtil.clientIp();

        Optional<Admin> optionalAdmin = adminRepository.findByKnEmail(email);
        if(optionalAdmin.isPresent()) {

            // 비밀번호 검증
            if (!passwordEncoder.matches(knPassword, optionalAdmin.get().getKnPassword())){
                log.error("입력하신 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            // 활동이력 저장 -> 비정상 모드
            Long activityHistoryId = activityHistoryService.insertActivityHistory(4, adminId, activityCode,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0, jwtFilterDto.getEmail());

            if(state == 1) {
                // 소속명 변경
                Optional<Company> optionalCompany = companyRepository.findById(optionalAdmin.get().getCompanyId());
                if(optionalCompany.isPresent()) {
                    optionalCompany.get().setCpName(cpContent);
                    optionalCompany.get().setModify_email(email);
                    optionalCompany.get().setModify_date(LocalDateTime.now());
                    companyRepository.save(optionalCompany.get());
                } else {
                    log.error("회사 정보가 존재하지 않습니다.");
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "회사 정보가 " + ResponseErrorCode.KO004.getDesc()));
                }
            } else {
                optionalAdmin.get().setKnDepartment(cpContent);
                optionalAdmin.get().setModify_email(email);
                optionalAdmin.get().setModify_date(LocalDateTime.now());
                adminRepository.save(optionalAdmin.get());
            }

            activityHistoryService.updateActivityHistory(activityHistoryId,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", 1);
        } else{
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }

        return ResponseEntity.ok(res.success(data));
    }

    // 비밀번호 변경
    @Transactional
    public ResponseEntity<Map<String, Object>> pwdChange(String oldknPassword, String newknPassword, String newknPasswordCheck, JwtFilterDto jwtFilterDto) {
        log.info("pwdChange 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = jwtFilterDto.getEmail();

        AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(jwtFilterDto.getEmail());
        Long adminId = adminCompanyInfoDto.getAdminId();
//        Long companyId = adminCompanyInfoDto.getCompanyId();
        String companyCode = adminCompanyInfoDto.getCompanyCode();

        // 활동 코드
        ActivityCode activityCode = ActivityCode.AC_38;
        String ip = CommonUtil.clientIp();

        Optional<Admin> optionalAdmin = adminRepository.findByKnEmail(email);
        if(optionalAdmin.isPresent()) {

            // 비밀번호 검증
            if (!passwordEncoder.matches(oldknPassword, optionalAdmin.get().getKnPassword())){
                log.error("입력하신 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            // 비밀번호 확인비교
            if (!newknPassword.equals(newknPasswordCheck)){
                log.error("새로운 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO083.getCode(), ResponseErrorCode.KO083.getDesc()));
            }

            // 활동이력 저장 -> 비정상 모드
            Long activityHistoryId = activityHistoryService.insertActivityHistory(4, adminId, activityCode,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0, jwtFilterDto.getEmail());

            optionalAdmin.get().setKnPassword(passwordEncoder.encode(newknPassword));
            optionalAdmin.get().setKnPwdChangeDate(LocalDateTime.now());
            optionalAdmin.get().setKnPwdErrorCount(0);
            optionalAdmin.get().setModify_email(email);
            optionalAdmin.get().setModify_date(LocalDateTime.now());
            adminRepository.save(optionalAdmin.get());

            activityHistoryService.updateActivityHistory(activityHistoryId,
                    companyCode+" - "+activityCode.getDesc()+" 시도 이력", "", 1);
        } else{
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }

        return ResponseEntity.ok(res.success(data));
    }



    // 유저의 권한 및 jwt 토큰확인
    public ResponseEntity<Map<String,Object>> authorityCheck(JwtFilterDto jwtFilterDto) {
        log.info("authorityCheck 호출");

        log.info("jwtFilterDto : "+jwtFilterDto);

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        String email = jwtFilterDto.getEmail();
        if(email.equals("anonymousUser")){
            log.error("사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO009.getCode(),ResponseErrorCode.KO009.getDesc()));
        } else{
            log.info("해당 유저의 이메일 : "+email);

            AdminInfoDto adminInfoDto = adminRepository.findByAdminInfo(email);

            log.info("해당 유저의 권한 : "+adminInfoDto.getKnRoleCode());
            data.put("knName",adminInfoDto.getKnName());
            data.put("cpName",adminInfoDto.getCpName());
        }

        return ResponseEntity.ok(res.success(data));
    }

    // 비밀번호 틀릴시 에러횟수 카운팅하는 함수
    public void adminErrorPwd(Admin admin) {
        log.info("비밀번호 틀릴시 에러횟수 카운팅하는 함수 호출");

        admin.setKnPwdErrorCount(admin.getKnPwdErrorCount()+1);
        admin.setModify_email(admin.getKnEmail());
        admin.setModify_date(LocalDateTime.now());
        adminRepository.save(admin);
    }

}
