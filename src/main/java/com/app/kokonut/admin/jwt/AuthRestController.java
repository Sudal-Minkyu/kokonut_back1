package com.app.kokonut.admin.jwt;

import com.app.kokonut.admin.AdminService;
import com.app.kokonut.admin.jwt.dto.AuthRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : 로그인, 회원가입 등 JWT토큰 필요없이 호출할 수 있는 컨트롤러
 */
@Slf4j
@RequestMapping("/api/Auth")
@RestController
public class AuthRestController {

    private final AdminService adminService;

    @Autowired
    public AuthRestController(AdminService adminService){
        this.adminService = adminService;
    }

    // 회원가입
    @PostMapping("/signUp")
    @ApiOperation(value = "사업자 회원가입" , notes = "" +
            "1. Param 값으로 이메일과, 비밀번호를 받는다." +
            "2. 해당 값을 통해 회원가입을 완료한다." +
            "현재는 미완성...")
    public ResponseEntity<Map<String,Object>> signUp(@Validated AuthRequestDto.SignUp signUp) {
        log.info("사업자 회원가입 API 호출");
        return adminService.signUp(signUp);
    }

    // 로그인 성공 이후 JWT Token 발급 및 업데이트
    @PostMapping("/authToken")
    @ApiOperation(value = "로그인 - OTP인증 이후 JWT 토큰 발급" , notes = "JWT 엑세스토큰과 리플레쉬토큰을 발급해준다.")
    public ResponseEntity<Map<String,Object>> authToken(@Validated AuthRequestDto.Login login) {
        log.info("로그인한 이메일 : "+login.getEmail());
        return adminService.authToken(login);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 새로고침" , notes = "" +
            "1. Param 값으로 accessToken, refreshToken을 받는다." +
            "2. 해당 리플레쉬 토큰을 통해 새 토큰을 발급하며 이전의 토큰은 사용 불가처리를 한다.")
    public ResponseEntity<Map<String,Object>> reissue(@Validated AuthRequestDto.Reissue reissue) {
        return adminService.reissue(reissue);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "로그아웃 처리" , notes = "" +
            "1. Param 값으로 accessToken, refreshToken을 받는다." +
            "2. 받은 두 토큰을 검사하고 해당 토큰을 삭제처리 한다.")
    public ResponseEntity<Map<String,Object>> logout(@Validated AuthRequestDto.Logout logout) {
        return adminService.logout(logout);
    }

    @PostMapping(value = "/loginVerify")
    public ResponseEntity<Map<String,Object>> loginVerify(@RequestBody HashMap<String,Object> paramMap, HttpServletRequest request, Authentication authentication) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
//        KokonutUser user = null;
//        returnMap.put("isSuccess", "false");
//        returnMap.put("errorCode", "ERROR_UNKNOWN");

//        if(paramMap.get("state").toString().equals("2")){
//            // 여기부터 우디(Wooody)가 작성
//            System.out.println("체험하기 모드 : 체험하기 로그인");
//
//            // 이메일이 test@kokonut.me 체크하는 로직추가
//            String email = paramMap.get("email").toString();
//            if(email.equals("test@kokonut.me") || email.equals("test2@kokonut.me")){
//                String password = Sha256.Sha256Encrypt(email+"testpassword");
////				System.out.println("email : "+email);
////				System.out.println("password : "+password);
//
//                user = loginService.SelectAdminByIdAndPassword(email, password); // 테스트계정 한번조회하기
////				System.out.println("user : "+user);
//
//                if(user == null){
//                    System.out.println("체험하기 모드 : 체험하기 계정 새로 생성합니다.");
//                    // - 체험하기 신규생성 -
//                    // 1. 기업생성로직
//                    String companyName;
//                    String businessNumber;
//                    if(email.equals("test@kokonut.me")) {
//                        companyName = "테스트기업";
//                        businessNumber = "0000123456";
//                    } else {
//                        companyName = "테스트기업2";
//                        businessNumber = "1111123456";
//                    }
//                    HashMap<String,Object> companyMap = new HashMap<>();
//                    companyMap.put("companyName", companyName);
//                    companyMap.put("representative", "테스트");
//                    companyMap.put("businessNumber", businessNumber);
//                    companyMap.put("businessType", "테스트용기업");
//
////					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
////					String currDate = formatter.format(new Date());
////					String encKey = currDate + businessNumber;
////					HashMap<String, Object> enc = awsKmsService.encrypt(encKey);
////					if(enc.get("errorMsg").equals("")) {
////						companyMap.put("encryptText", enc.get("encryptText").toString());
////						companyMap.put("dataKey", enc.get("dataKey").toString());
////					}
//
//                    companyService.InsertCompany(companyMap);
//
//                    HashMap<String, Object> saveCompany = companyService.SelectCompanyByName(companyName);
//
//                    // 2. 계정생성로직
//                    paramMap.put("masterIdx", 0);
//                    paramMap.put("companyIdx", saveCompany.get("IDX").toString());
//                    paramMap.put("userType", 1);
//                    paramMap.put("password", password);
//                    paramMap.put("name", "테스트");
//                    paramMap.put("phoneNumber", "01012123434");
//                    paramMap.put("adminLevelIdx", 1);
//                    paramMap.put("state", 1);
//                    paramMap.put("isEmailAuth", "Y");
//                    paramMap.put("roleName", KokonutUser.ROLE_MASTER);
//                    paramMap.put("approvalState", 2);
//                    adminService.InsertAdmin(paramMap);
//
//                    user = loginService.SelectAdminByIdAndPassword(email, password); // 재조회
//
//                    String reason = "테이블 생성(테이블명 :"+businessNumber+")";
//                    // ActivityHistory 데이터추가
//                    activityHistoryService.InsertActivityHistory(2, user.getCompanyIdx(), user.getIdx(), 16, "", reason, "127.0.0.1", 1);
//
//                    // 필수 테이블 생성
//                    dynamicUserService.CreateDynamicTable(businessNumber);
//                    dynamicRemoveService.CreateDynamicTable(businessNumber);
//                    dynamicDormantService.CreateDynamicTable(businessNumber);
//                }
//
//                // 체험하기 로그인처리
//                returnMap.put("userIdx", user.getIdx());
//                returnMap.put("isSuccess", "true");
//            }
//            return returnMap;
//        }
//        else{

//            do {
//                if(!paramMap.containsKey("email")) {
//                    returnMap.put("errorCode", "ERROR_1");
//                    logger.error("[loginVerify] ERROR_1");
//                    break;
//                }
//                if(!paramMap.containsKey("password")) {
//                    returnMap.put("errorCode", "ERROR_2");
//                    logger.error("[loginVerify] ERROR_2");
//                    break;
//                }
//
//                String email = paramMap.get("email").toString();
//                String password = paramMap.get("password").toString();
//                String encryptIdx = "";
//
//                user = loginService.SelectAdminByIdAndPassword(email, password);
//                if(user == null) {
//                    KokonutUser emailUser = loginService.SelectAdminByEmailCount(email);
//                    if(emailUser != null) {
//                        encryptIdx = ariaUtil.Encrypt(String.valueOf(emailUser.getIdx()));
//                        returnMap.put("encryptIdx", encryptIdx);
//                        loginService.UpdatePwdErrorCountByIdx(emailUser.getIdx());
//                        int pwdErrorCount = emailUser.getPwdErrorCount() + 1;
//                        if(pwdErrorCount == 5) {
//                            returnMap.put("errorCode", "ERROR_3");
//                            break;
//                        }
//
//                        if(pwdErrorCount > 5) {
//                            if (pwdErrorCount == 6) {
//                                loginService.UpdateStateStopByIdx(emailUser.getIdx());
//                            }
//
//                            returnMap.put("errorCode", "ERROR_4");
//                            break;
//                        }
//                    }
//
//                    returnMap.put("errorCode", "ERROR_5");
//                    break;
//                }
//
//                String isEmailAuth = user.getIsEmailAuth();
//                String state = user.getState();
//                encryptIdx = ariaUtil.Encrypt(String.valueOf(user.getIdx()));
//                returnMap.put("encryptIdx", encryptIdx);
//
//                if("0".equals(state)) {
//                    returnMap.put("errorCode", "ERROR_6");
//                    break;
//                }
//
//                if("3".equals(state)) {
//                    returnMap.put("errorCode", "ERROR_7");
//                    break;
//                }
//
//                if("4".equals(state)) {
//                    returnMap.put("errorCode", "ERROR_8");
//                    break;
//                }
//
//                if("2".equals(state)) {
//                    returnMap.put("errorCode", "ERROR_13");
//                    break;
//                }
//
//                if("N".equals(isEmailAuth)) {
//                    returnMap.put("errorCode", "ERROR_9");
//                    break;
//                }
//
//                String approvalState = user.getApprovalState();
//                if (KokonutUser.ROLE_MASTER.equals(user.getRoleName()) && !"2".equals(approvalState) ) {
//                    returnMap.put("errorCode", "ERROR_10");
//                    break;
//                }
//
//                int companyIdx = user.getCompanyIdx();
//                if (KokonutUser.ROLE_ADMIN.equals(user.getRoleName()) && companyIdx == 0) {
//                    returnMap.put("errorCode", "ERROR_11");
//                    break;
//                }
//
//                /* 해외 아이피 차단 여부 */
//                HashMap<String, Object> setting = settingService.SelectSettingByCompanyIdx(companyIdx);
//                if(setting != null && !setting.isEmpty()) {
//                    String overseasBlock = setting.get("OVERSEAS_BLOCK").toString();
//                    if("1".equals(overseasBlock)) {
//                        String ip = CommonUtil.clientIp();
//                        HashMap<String, Object> ipInfo = ipInfoService.GetIpInfo(ip);
//                        if(ipInfo.get("country")!= null && !"".equals("country") && !ipInfo.get("country").toString().equals("KR")){
//                            returnMap.put("errorCode", "ERROR_12");
//                            break;
//                        }
//                    }
//                }
//
//                loginService.ResetPwdError(user.getIdx());
//                adminService.UpdateIsLoginAuth(user.getIdx(), "N");
//                user.setOtpKey("");
//                returnMap.put("isSuccess", "true");
//                returnMap.put("loginUser", user);
//                returnMap.put("errorCode", "ERROR_SUCCESS");
//
//            } while (false);

        return null;
    }

}
