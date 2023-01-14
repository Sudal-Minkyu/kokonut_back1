package com.app.kokonut.woody.restcontroller;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.admin.AdminService;
import com.app.kokonut.company.CompanyService;
import com.app.kokonut.woody.common.component.AriaUtil;
import com.app.kokonut.woody.excel.ExcelService;
import com.app.kokonut.woody.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Woody
 * Date : 2022-10-24
 * Time :
 * Remark : Kokonut User RestController
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private final UserService userService;

    private final CompanyService companyService;
    private final ExcelService excelService;
    private final AdminService adminService;
    private final ActivityHistoryService activityHistoryService;

    private final AriaUtil ariaUtil;
//    private final LoginService loginService;
//    private final DynamicUserService dynamicUserService;
//    private final DynamicDormantService dynamicDormantService;

    @Autowired
    public UserRestController(UserService userService, CompanyService companyService, ExcelService excelService,
                              AdminService adminService, ActivityHistoryService activityHistoryService, AriaUtil ariaUtil) {
        this.userService = userService;
        this.companyService = companyService;
        this.excelService = excelService;
        this.adminService = adminService;
        this.activityHistoryService = activityHistoryService;
        this.ariaUtil = ariaUtil;
    }

////    private DBLogger dblogger = new DBLogger(UserApiController.class);
//
//    // UserApiController -> 기존 코코넛의 kokonutApi 관리자로그인 리펙토링
//    // 기존 url : /api/user/admin/signin
//    @ApiOperation(value="관리자 로그인", notes="관리자 아이디, 패스워드를 통한 로그인")
//    @PostMapping("/admin/signin")
//    public ResponseEntity<KokonutApiResponse> adminSignin(@Valid @RequestBody SigninRequest signinRequest, HttpServletRequest request){
////        HttpStatus status = HttpStatus.OK;
////        KokonutApiResponse body = new KokonutApiResponse();
////
////        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
////        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
////        final String IP = Utils.getClientIp(request);
////        final String LOG_HEADER = "[kokonut api admin singin]";
////
////        try {
////            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
////
////            String id = signinRequest.getId();
////            String password = signinRequest.getPw();
////            password = Sha256.Sha256Encrypt(id + password);
////
////            String encryptIdx = "";
////            KokonutUser user = null;
////            AuthUser authUser = null;
////
////            user = loginService.SelectAdminByIdAndPassword(id, password);
////
////            if (user == null) {
////                KokonutUser emailUser = loginService.SelectAdminByEmailCount(id);
////                if(emailUser != null) {
////                    encryptIdx = ariaUtil.Encrypt(String.valueOf(emailUser.getIdx()));
////                    loginService.UpdatePwdErrorCountByIdx(emailUser.getIdx());
////                    int pwdErrorCount = emailUser.getPwdErrorCount() + 1;
////                    if(pwdErrorCount == 5) {
////                        status = HttpStatus.BAD_REQUEST;
////                        throw new Exception("ERROR_CHANGE_PWD");
////                    }
////
////                    if(pwdErrorCount > 5) {
////                        if (pwdErrorCount == 6) {
////                            loginService.UpdateStateStopByIdx(emailUser.getIdx());
////                        }
////
////                        status = HttpStatus.LOCKED;
////                        throw new Exception("ERROR_LIMIT");
////                    }
////                }
////
////                Integer count = adminService.SelectAdminCountByEmail(id);
////                if(count > 0) {
////                    status = HttpStatus.UNAUTHORIZED;
////                    throw new Exception("mismatch of passwords");
////                } else {
////                    status = HttpStatus.NOT_FOUND;
////                    throw new Exception("not found admin user");
////                }
////            }
////
////            /* 로그인 정보 업데이트 */
//////			HashMap<String, Object> loginMap = new HashMap<String, Object>();
//////			loginMap.put("idx", user.getIdx());
//////			loginMap.put("ip", IP);
//////			loginService.UpdateLoginInfo(loginMap);
////
////        } catch (Exception e) {
////            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
////
////            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
////
////            body.setSuccess(false);
////            body.setError(e.getMessage());
////        }
////
////        return new ResponseEntity<KokonutApiResponse>(body, status);
//
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api admin singin]";
//
//        try {
//            String id = signinRequest.getId();
//            String password = signinRequest.getPw();
//            password = Sha256.Sha256Encrypt(id + password);
//
//            String encryptIdx = "";
//            KokonutUser user = null;
//            AuthUser authUser = null;
//
//            user = loginService.SelectAdminByIdAndPassword(id, password);
//
//            if (user == null) {
//                KokonutUser emailUser = loginService.SelectAdminByEmailCount(id);
//                if(emailUser != null) {
//                    encryptIdx = ariaUtil.Encrypt(String.valueOf(emailUser.getIdx()));
//                    loginService.UpdatePwdErrorCountByIdx(emailUser.getIdx());
//                    int pwdErrorCount = emailUser.getPwdErrorCount() + 1;
//                    if(pwdErrorCount == 5) {
//                        status = HttpStatus.BAD_REQUEST;
//                        throw new Exception("ERROR_CHANGE_PWD");
//                    }
//
//                    if(pwdErrorCount > 5) {
//                        if (pwdErrorCount == 6) {
//                            loginService.UpdateStateStopByIdx(emailUser.getIdx());
//                        }
//
//                        status = HttpStatus.LOCKED;
//                        throw new Exception("ERROR_LIMIT");
//                    }
//                }
//
//                Integer count = adminService.SelectAdminCountByEmail(id);
//                if(count > 0) {
//                    status = HttpStatus.UNAUTHORIZED;
//                    throw new Exception("mismatch of passwords");
//                } else {
//                    status = HttpStatus.NOT_FOUND;
//                    throw new Exception("not found admin user");
//                }
//            }
//
//            /* 로그인 정보 업데이트 */
////			HashMap<String, Object> loginMap = new HashMap<String, Object>();
////			loginMap.put("idx", user.getIdx());
////			loginMap.put("ip", IP);
////			loginService.UpdateLoginInfo(loginMap);
//
//        } catch (Exception e) {
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//
//    }

//    @ApiOperation(value="로그인", notes="아이디, 패스워드를 통한 로그인", response=KokonutApiResponse.class)
//    @PostMapping("/signin")
//    public ResponseEntity<KokonutApiResponse> signin(@Valid @RequestBody SigninRequest signinRequest, HttpServletRequest request){
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api singin]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            String id = signinRequest.getId();
//            String password = signinRequest.getPw();
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            // 비밀번호 암호화
//            String salt = dynamicUserService.SelectSalt(tableName, id);
//            if(salt == null || salt.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found salt");
//            }
//
//            password = Sha512.encrypt(password, salt);
//            if(password == null || password.isEmpty()) {
//                throw new Exception("cannot encryt password");
//            }
//
//            Integer count = dynamicUserService.SelectCount(tableName, id, password);
//            if(count == null || count <= 0) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user info");
//            }
//
//            /* 로그인 성공시 로그인 일시 업데이트 */
//            dynamicUserService.UpdateLastLoginDate(tableName, id, password);
//        } catch (Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원가입", notes="사용자 정보 저장", response=KokonutApiResponse.class)
//    @PostMapping("/signup")
//    public ResponseEntity<KokonutApiResponse> signup(
//            @ApiParam(value="사용자 정보 데이터(JSON String), *NOTE: 회원 테이블의 컬럼명과 타입에 맞게 작성해주세요. (단, IDX, SALT 컬럼 제외)", required=true) @RequestBody String jsonString,
//            HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api signup]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            if(!companyService.CheckUserCount(apiKeyInfo.getCompanyIdx())) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("등록가능한 인원 수(5,000명)를 초과하였습니다.");
//            }
//
//            Map<String, Object> info = new ObjectMapper().readValue(jsonString, new TypeReference<Map<String,Object>>(){});
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            // 차단 컬럼 검사
//            if(info.containsKey("IDX")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("IDX 컬럼 저장할 수 없습니다.");
//            }
//            if(info.containsKey("SALT")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("SALT 컬럼 저장할 수 없습니다.");
//            }
//
//            // 아이디 중복검사
//            if(!info.containsKey("ID") || info.get("ID") == null) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("not found field: ID");
//            }
//
//            if(dynamicUserService.IsExistId(tableName, info.get("ID").toString())) {
//                status = HttpStatus.CONFLICT;
//                throw new Exception("이미 존재하는 아이디 입니다.");
//            }
//
//            // 데이터를 field-value 형태의 리스트로 가공
//            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//
//            for(Map.Entry<String, Object> entry : info.entrySet()) {
//                String field = entry.getKey();
//                Object value = entry.getValue();
//
//                // 비밀번호 암호화
//                if(field.equals("PASSWORD")) {
//                    EncryptedPasswordData encryptedPasswordData = dynamicUserService.encryptPassword((String)value);
//
//                    // SALT 컬럼 데이터 추가
//                    Map<String, Object> item = new HashMap<String, Object>();
//                    item.put("field", "SALT");
//                    item.put("value", encryptedPasswordData.getSalt());
//                    list.add(item);
//
//                    // 암호화된 비밀번호로 변경
//                    value = encryptedPasswordData.getEncryptedPassword();
//                }
//                // 암호화 속성을 갖는 컬럼의 데이터 암호화
//                else {
//                    for(Column column : encryptColumns) {
//                        if(column.getField().equals(field)) {
//                            String encryptedValue = AesCrypto.encrypt((String)value, DECRYPTED_KEY);
//
//                            // 암호화된 데이터로 변경
//                            value = encryptedValue;
//                            break;
//                        }
//                    }
//                }
//
//                Map<String, Object> item = new HashMap<String, Object>();
//                item.put("field", field);
//                item.put("value", value);
//                list.add(item);
//            }
//
//            if(!dynamicUserService.InsertUser(tableName, list)) {
//                throw new Exception("save user info fail");
//            }
//        } catch (Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="아이디 찾기", notes="하나의 조건으로 아이디 찾기 요청", response=IdInquiryResponse.class)
//    @PostMapping("/id/inquiry")
//    public ResponseEntity<IdInquiryResponse> idInquiry(
//            @ApiParam(value="사용자 정보 데이터(JSON String), *NOTE: 회원 테이블의 컬럼명과 타입에 맞게 하나의 필드만 작성해주세요.", required=true) @RequestBody String jsonString,
//            HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        IdInquiryResponse body = new IdInquiryResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api id inquiry]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            Map<String, Object> info = new ObjectMapper().readValue(jsonString, new TypeReference<Map<String,Object>>(){});
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            String id = "";
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//            String field = null;
//            Object value = null;
//
//            for(Map.Entry<String, Object> entry : info.entrySet()) {
//                field = entry.getKey();
//                value = entry.getValue();
//
//                // 암호화 속성을 갖는 컬럼의 데이터 암호화
//                for(Column column : encryptColumns) {
//                    if(column.getField().equals(field)) {
//                        String encryptedValue = AesCrypto.encrypt((String)value, DECRYPTED_KEY);
//
//                        // 암호화된 데이터로 변경
//                        value = encryptedValue;
//                        break;
//                    }
//                }
//
//                break;
//            }
//
//            id = dynamicUserService.SelectId(tableName, field, value);
//            if(id.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user id");
//            }
//
//            body.setId(id);
//
//            // 고객정보 열람 이력 저장
//            String reason = "아이디 찾기 API(테이블 :kokonut_user." + tableName + ", ID : "+ id +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 8, "", reason, CommonUtil.clientIp(), 1);
//
//        } catch (Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<IdInquiryResponse>(body, status);
//    }
//
//    @ApiOperation(value="비밀번호 변경", notes="해당 아이디에 대해 새로운 비밀번호를 저장", response=KokonutApiResponse.class)
//    @PostMapping("/pw/inquiry")
//    public ResponseEntity<KokonutApiResponse> pwInquiry(@Valid @RequestBody PwInquiryRequest pwInquiryRequest, HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api password inquiry]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            String id = pwInquiryRequest.getId();
//            String password = pwInquiryRequest.getPw();
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            // 비밀번호 암호화
//            String salt = dynamicUserService.SelectSalt(tableName, id);
//            if(salt == null || salt.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found salt");
//            }
//
//            password = Sha512.encrypt(password, salt);
//            if(password == null || password.isEmpty()) {
//                throw new Exception("cannot encryt password");
//            }
//
//            if(!dynamicUserService.UpdatePassword(tableName, id, password)) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user info");
//            }
//
//            // 고객정보 처리 이력 저장
//            String reason = "비밀번호 변경 API(테이블 :kokonut_user." + tableName + ", ID : "+ id +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 10, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원정보 수정", notes="사용자 정보 수정, *변경 불가 컬럼: IDX, ID, PASSWORD, SALT", response=KokonutApiResponse.class)
//    @PutMapping("/{idx}")
//    public ResponseEntity<KokonutApiResponse> modify(
//            @ApiParam(value="사용자 키", example="0", required=true) @PathVariable Integer idx,
//            @ApiParam(value="사용자 정보 데이터(JSON String), *NOTE: 회원 테이블의 컬럼명과 타입에 맞게 작성해주세요.", required=true) @RequestBody String jsonString,
//            HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api user modify]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            Map<String, Object> info = new ObjectMapper().readValue(jsonString, new TypeReference<Map<String,Object>>(){});
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            // 차단 컬럼 검사
//            if(info.containsKey("IDX")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("cannot change user key");
//            }
//
//            if(info.containsKey("ID")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("cannot change user id");
//            }
//
//            if(info.containsKey("PASSWORD")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("cannot change user password");
//            }
//
//            if(info.containsKey("SALT")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("cannot change user salt");
//            }
//
//            // 데이터를 field-value 형태의 리스트로 가공
//            List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//
//            for(Map.Entry<String, Object> entry : info.entrySet()) {
//                String field = entry.getKey();
//                Object value = entry.getValue();
//
//                // 암호화 속성을 갖는 컬럼의 데이터 암호화
//                for(Column column : encryptColumns) {
//                    if(column.getField().equals(field)) {
//                        String encryptedValue = AesCrypto.encrypt((String)value, DECRYPTED_KEY);
//
//                        // 암호화된 데이터로 변경
//                        value = encryptedValue;
//                        break;
//                    }
//                }
//
//                Map<String, Object> item = new HashMap<String, Object>();
//                item.put("field", field);
//                item.put("value", value);
//                list.add(item);
//            }
//
//            if(!dynamicUserService.UpdateUser(tableName, idx, list)) {
//                throw new Exception("modify user info fail");
//            }
//
//            // 고객정보 처리 이력 저장
//            String reason = "회원정보 수정 API(테이블 :kokonut_user." + tableName + ", IDX : "+ idx +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 10, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원 목록 조회", notes="사용자 정보 목록을 조회", response=GetUserListResponse.class)
//    @GetMapping("/list")
//    public ResponseEntity<GetUserListResponse> getUserList(HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        GetUserListResponse body = new GetUserListResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api get user list]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            List<Map<String, Object>> userList = null;
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            userList = dynamicUserService.SelectUserListByTableName(tableName);
//
//            if(userList == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user list");
//            }
//
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//
//            for(Map<String, Object> user : userList) {
//                user.remove("PASSWORD");
//                user.remove("SALT");
//
//                // 암호화 속성을 갖는 컬럼의 데이터 복호화
//                for(Map.Entry<String, Object> entry : user.entrySet()) {
//                    String key = entry.getKey();
//                    Object value = entry.getValue();
//
//                    for(Column column : encryptColumns) {
//                        if(column.getField().equals(key)) {
//                            String decryptedValue = AesCrypto.decrypt(new String(value.toString().getBytes()), DECRYPTED_KEY);
//
//                            // 복호화된 데이터로 변경
//                            user.put(key, decryptedValue);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            body.setUserList(userList);
//
//            // 고객정보 열람 이력 저장
//            String reason = "회원목록 조회 API(테이블 :kokonut_user." + tableName +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 8, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<GetUserListResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원정보 조회", notes="사용자 정보 조회", response=GetUserResponse.class)
//    @GetMapping("/{idx}")
//    public ResponseEntity<GetUserResponse> getUser(
//            @ApiParam(value="사용자 키", example="0", required=true) @PathVariable Integer idx,
//            HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        GetUserResponse body = new GetUserResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api get user]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            Map<String, Object> user = null;
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            user = dynamicUserService.SelectUserDataByIdx(tableName, idx);
//
//            if(user == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user");
//            }
//
//            user.remove("PASSWORD");
//            user.remove("SALT");
//
//            // 암호화 속성을 갖는 컬럼의 데이터 복호화
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//            for(Map.Entry<String, Object> entry : user.entrySet()) {
//                String key = entry.getKey();
//                Object value = entry.getValue();
//
//                for(Column column : encryptColumns) {
//                    if(column.getField().equals(key)) {
//                        String decryptedValue = AesCrypto.decrypt(new String(value.toString().getBytes()), DECRYPTED_KEY);
//
//                        // 복호화된 데이터로 변경
//                        user.put(key, decryptedValue);
//                        break;
//                    }
//                }
//            }
//
//            body.setUser(user);
//
//            // 고객정보 열람 이력 저장
//            String reason = "회원정보 조회 API(테이블 :kokonut_user." + tableName + ", IDX : "+ idx +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 8, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<GetUserResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원 테이블 컬럼 목록 조회", notes="사용자 테이블 컬럼 목록 조회", response=GetTableColumnsResponse.class)
//    @GetMapping("/table/columns")
//    public ResponseEntity<GetTableColumnsResponse> getTableColumns(HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        GetTableColumnsResponse body = new GetTableColumnsResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api get user table columns]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//
//            List<Column> columns = null;
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            columns = dynamicUserService.SelectColumns(tableName);
//
//            if(columns == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table desc");
//            }
//
//            body.setColumns(columns);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<GetTableColumnsResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원 상태 조회", notes="사용자 상태 조회", response=GetUserStateResponse.class)
//    @GetMapping("/{idx}/state")
//    public ResponseEntity<GetUserStateResponse> getUserState(
//            @ApiParam(value="사용자 키", example="0", required=true) @PathVariable Integer idx,
//            HttpServletRequest request) {
//        HttpStatus status = HttpStatus.OK;
//        GetUserStateResponse body = new GetUserStateResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api get user state]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//
//            String state = "";
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//
//            // 일반계정 테이블 검사
//            Integer count = dynamicUserService.SelectCount(tableName, idx);
//
//            if(count == null || count <= 0) {
//                // 휴면계정 테이블 검사
//                count = dynamicDormantService.SelectDormantCount(tableName, idx);
//
//                if(count == null || count <= 0) {
//                    status = HttpStatus.NOT_FOUND;
//                    throw new Exception("not found user state");
//                }
//                else {
//                    state = "휴면";
//                }
//            }
//            else {
//                state = "사용";
//            }
//
//            body.setState(state);
//
//            // 고객정보 열람 이력 저장
//            String reason = "회원상태 조회 API(테이블 :kokonut_user." + tableName + ", IDX : "+ idx +")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 8, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<GetUserStateResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원 목록 엑셀 다운로드", notes="사용자 정보 목록을 엑셀 파일로 다운로드", response=KokonutApiResponse.class)
//    @GetMapping("/excel/download")
//    public ResponseEntity<KokonutApiResponse> excelDownload(HttpServletRequest request, HttpServletResponse response) {
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api user excel download]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            // 다운로드 이력을 남기기 위한 데이터 수집
//            List<String> fileNameToks = new ArrayList<String>();
//            Map<String, Object> companyMap = companyService.SelectCompanyByIdx(apiKeyInfo.getCompanyIdx());
//            if(companyMap.containsKey("COMPANY_NAME")) {
//                String companyName = companyMap.get("COMPANY_NAME").toString();
//                fileNameToks.add(companyName);
//            }
//            fileNameToks.add("회원정보");
//
//            String fileName = excelService.generateFileName(fileNameToks);
//            final String TYPE = "API";
//            final String REASON = "회원정보 조회 API 요청";
//            final Integer ADMIN_IDX = apiKeyInfo.getAdminIdx();
//            final String REGISTER_NAME = apiKeyInfo.getRegisterName();
//
//            // 엑셀 다운로드
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            List<Column> columns = dynamicUserService.SelectColumns(tableName);
//            if(columns == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table columns");
//            }
//
//            List<Map<String, Object>> userList = dynamicUserService.SelectUserListByTableName(tableName);
//            if(userList == null) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found user list");
//            }
//
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//
//            for(Map<String, Object> user : userList) {
//                user.remove("PASSWORD");
//                user.remove("SALT");
//
//                for(Map.Entry<String, Object> entry : user.entrySet()) {
//                    String key = entry.getKey();
//                    Object value = entry.getValue();
//
//                    // 암호화 속성을 갖는 컬럼의 데이터 복호화
//                    for(Column column : encryptColumns) {
//                        if(column.getField().equals(key)) {
//                            String decryptedValue = AesCrypto.decrypt(new String(value.toString().getBytes()), DECRYPTED_KEY);
//
//                            // 복호화된 데이터로 변경
//                            user.put(key, decryptedValue);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            List<String> headerList = new ArrayList<String>();
//            List<List<String>> dataList = new ArrayList<List<String>>();
//
//            for(Column column : columns) {
//                String header = column.getField();
//
//                if(header.equals("PASSWORD") || header.equals("SALT")) {
//                    continue;
//                }
//
//                headerList.add(header);
//            }
//
//            for(Map<String, Object> user : userList) {
//                List<String> data = new ArrayList<String>();
//
//                for(String header : headerList) {
//                    if(user.containsKey(header)) {
//                        data.add(user.get(header).toString());
//                    }
//                    else {
//                        data.add("NULL");
//                    }
//                }
//
//                dataList.add(data);
//            }
//
//            if(fileName.isEmpty()) fileName= "userlist";
//
//            excelService.download(request, response, fileName, headerList, dataList);
//
//            // 다운로드 이력 저장
//            HashMap<String, Object> downloadHistoryMap = new HashMap<String, Object>();
//            downloadHistoryMap.put("type", TYPE);
//            downloadHistoryMap.put("fileName", fileName);
//            downloadHistoryMap.put("reason", REASON);
//            downloadHistoryMap.put("adminIdx", ADMIN_IDX);
//            downloadHistoryMap.put("registerName", REGISTER_NAME);
//
//            downloadHistoryService.InsertDownloadHistory(downloadHistoryMap);
//
//            // 고객정보 열람 이력 저장
//            String reason = "회원목록 엑셀 다운로드 API(테이블 :kokonut_user." + tableName + ")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 9, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="회원 목록 엑셀 업로드", notes="사용자 정보 엑셀 파일을 업로드하여 사용자 테이블에 저장", response=KokonutApiResponse.class)
//    @PostMapping("/excel/upload")
//    public ResponseEntity<KokonutApiResponse> excelUpload(
//            @ApiParam(value="사용자 정보 엑셀 파일, *NOTE: 회원 테이블의 컬럼명과 타입에 맞게 작성해주세요. (단, IDX, SALT 컬럼 제외)", required=true) @RequestBody MultipartFile file,
//            HttpServletRequest request){
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api user excel upload]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//            final String DECRYPTED_KEY = companyService.SelectCompanyEncryptKey(apiKeyInfo.getCompanyIdx());
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            if(file == null) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("not found file");
//            }
//
//            List<List<String>> rows = excelService.read(file.getInputStream());
//            if(rows == null) {
//                throw new Exception("cannot read file");
//            }
//
//            List<String> headerList = rows.get(0);
//            List<List<String>> rowList = new ArrayList<List<String>>();
//            for(int i = 1; i < rows.size(); i++) {
//                rowList.add(rows.get(i));
//            }
//
//            // 차단 컬럼 검사
//            if(headerList.contains("IDX")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("IDX 컬럼 저장할 수 없습니다.");
//            }
//            if(headerList.contains("SALT")) {
//                status = HttpStatus.BAD_REQUEST;
//                throw new Exception("SALT 컬럼 저장할 수 없습니다.");
//            }
//
//            // 데이터를 field-value 형태의 리스트로 가공
//            List<List<Map<String, Object>>> insertList = new ArrayList<List<Map<String,Object>>>();
//            List<Column> encryptColumns = dynamicUserService.SelectEncryptColumns(tableName);
//
//            for(List<String> row : rowList) {
//                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
//
//                for(int i = 0; i < headerList.size(); i++) {
//                    String field = headerList.get(i);
//                    String value = null;
//                    if(i < row.size()) {
//                        value = row.get(i);
//                    }
//
//                    if(value.equals("NULL")) {
//                        value = null;
//                    }
//
//                    // 아이디 중복검사
//                    if(field.equals("ID")) {
//                        if(dynamicUserService.IsExistId(tableName, value)) {
//                            status = HttpStatus.CONFLICT;
//                            throw new Exception("이미 존재하는 아이디 입니다.: " + value);
//                        }
//                    }
//                    // 비밀번호 암호화
//                    else if(field.equals("PASSWORD")) {
//                        EncryptedPasswordData encryptedPasswordData = dynamicUserService.encryptPassword((String)value);
//
//                        // SALT 컬럼 데이터 추가
//                        Map<String, Object> item = new HashMap<String, Object>();
//                        item.put("field", "SALT");
//                        item.put("value", encryptedPasswordData.getSalt());
//                        list.add(item);
//
//                        // 암호화된 비밀번호로 변경
//                        value = encryptedPasswordData.getEncryptedPassword();
//                    }
//                    // 암호화 속성을 갖는 컬럼의 데이터 암호화
//                    else {
//                        for(Column column : encryptColumns) {
//                            if(column.getField().equals(field)) {
//                                String encryptedValue = AesCrypto.encrypt((String)value, DECRYPTED_KEY);
//
//                                // 암호화된 데이터로 변경
//                                value = encryptedValue;
//                                break;
//                            }
//                        }
//                    }
//
//                    Map<String, Object> info = new HashMap<String, Object>();
//                    info.put("field", field);
//                    info.put("value", value);
//                    list.add(info);
//                }
//
//                insertList.add(list);
//            }
//
//            dynamicUserService.InsertAllUserData(tableName, insertList);
//
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }
//
//    @ApiOperation(value="휴면 해제", notes="휴면계정 복구", response=GetUserResponse.class)
//    @GetMapping("/restore/dormant/{idx}")
//    public ResponseEntity<KokonutApiResponse> restoreDormant(
//            @ApiParam(value="사용자 키", example="0", required=true)
//            @PathVariable Integer idx, HttpServletRequest request){
//        HttpStatus status = HttpStatus.OK;
//        KokonutApiResponse body = new KokonutApiResponse();
//
//        ApiKeyInfo apiKeyInfo = (ApiKeyInfo)request.getAttribute("apiKeyInfo");
//        final Integer API_KEY_IDX = apiKeyInfo.getIdx();
//        final String IP = Utils.getClientIp(request);
//        final String LOG_HEADER = "[kokonut api restore dormant]";
//
//        try {
//            dblogger.save(DBLogger.LEVEL.INFO, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, "start");
//
//            String tableName = dynamicUserService.SelectTableName(apiKeyInfo.getCompanyIdx());
//
//            if(tableName.isEmpty()) {
//                status = HttpStatus.NOT_FOUND;
//                throw new Exception("not found table name");
//            }
//
//            if(!dynamicDormantService.Restore(tableName, idx)) {
//                throw new Exception("restore failed");
//            }
//
//            // 고객정보 처리 이력 저장
//            String reason = "휴면해제 API(테이블 :kokonut_user." + tableName + ", IDX : " + idx + ")";
//            activityHistoryService.InsertActivityHistory(1, apiKeyInfo.getCompanyIdx(), apiKeyInfo.getAdminIdx(), 10, "", reason, CommonUtil.clientIp(), 1);
//        } catch(Exception e) {
//            dblogger.save(DBLogger.LEVEL.ERROR, API_KEY_IDX, IP, DBLogger.TYPE.READ, LOG_HEADER, e.getMessage());
//
//            if(status == HttpStatus.OK) status = HttpStatus.INTERNAL_SERVER_ERROR;
//
//            body.setSuccess(false);
//            body.setError(e.getMessage());
//        }
//
//        return new ResponseEntity<KokonutApiResponse>(body, status);
//    }

}
