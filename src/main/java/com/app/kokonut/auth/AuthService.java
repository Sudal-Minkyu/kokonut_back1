package com.app.kokonut.auth;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.entity.Admin;
import com.app.kokonut.admin.entity.enums.AuthorityRole;
import com.app.kokonut.auth.dtos.AdminGoogleOTPDto;
import com.app.kokonut.auth.jwt.been.JwtTokenProvider;
import com.app.kokonut.common.component.AwsKmsUtil;
import com.app.kokonut.common.component.AwsS3Util;
import com.app.kokonut.configs.GoogleOTP;
import com.app.kokonut.auth.jwt.dto.AuthRequestDto;
import com.app.kokonut.auth.jwt.dto.AuthResponseDto;
import com.app.kokonut.auth.jwt.dto.GoogleOtpGenerateDto;
import com.app.kokonut.awsKmsHistory.AwsKmsHistory;
import com.app.kokonut.awsKmsHistory.AwsKmsHistoryRepository;
import com.app.kokonut.awsKmsHistory.dto.AwsKmsResultDto;
import com.app.kokonut.company.Company;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.companyFile.CompanyFile;
import com.app.kokonut.companyFile.CompanyFileRepository;
import com.app.kokonut.keydata.KeyDataService;
import com.app.kokonut.common.AjaxResponse;
import com.app.kokonut.common.ResponseErrorCode;
import com.app.kokonut.common.component.AriaUtil;;
import com.app.kokonut.common.component.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author Woody
 * Date : 2022-12-09
 * Time :
 * Remark : AuthService(인증서비스), 기존의 LoginService와 동일
 */
@Slf4j
@Service
public class AuthService {

    @Value("${kokonut.aws.s3.businessS3Folder}")
    private String businessS3Folder;

//    @Value("${kokonut.aws.s3.url}")
    private final String AWSURL;

    private final AwsS3Util awsS3Util;
    private final AwsKmsUtil awsKmsUtil;

    private final AdminRepository adminRepository;

    private final CompanyRepository companyRepository;
    private final CompanyFileRepository companyFileRepository;
    private final AwsKmsHistoryRepository awsKmsHistoryRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final StringRedisTemplate redisTemplate;
    private final GoogleOTP googleOTP;

    @Autowired
    public AuthService(KeyDataService keyDataService, AwsS3Util awsS3Util, AdminRepository adminRepository, AwsKmsUtil awsKmsUtil, CompanyRepository companyRepository, CompanyFileRepository companyFileRepository,
                       AwsKmsHistoryRepository awsKmsHistoryRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
                       AuthenticationManagerBuilder authenticationManagerBuilder,
                       StringRedisTemplate redisTemplate, GoogleOTP googleOTP) {
        this.awsS3Util = awsS3Util;
        this.adminRepository = adminRepository;
        this.awsKmsUtil = awsKmsUtil;
        this.companyRepository = companyRepository;
        this.companyFileRepository = companyFileRepository;
        this.awsKmsHistoryRepository = awsKmsHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.redisTemplate = redisTemplate;
        this.googleOTP = googleOTP;
        this.AWSURL = keyDataService.findByKeyValue("aws_s3_url");
    }

    // 회원가입 기능
    @Transactional
    public ResponseEntity<Map<String,Object>> signUp(AuthRequestDto.SignUp signUp, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("signUp 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();

        if (adminRepository.existsByEmail(signUp.getEmail())) {
            log.error("이미 회원가입된 이메일입니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO005.getCode(), ResponseErrorCode.KO005.getDesc()));
        }

        log.info("회원가입 시작");
        log.info("받아온 값 signUp : "+signUp);

//        String fileGroupId = "FATT_" + Calendar.getInstance().getTimeInMillis();
//        String fileGroupId = "FATT_" + UUID.randomUUID();  // 파일그룹ID? 바뀔수도..
        String phoneNumber = signUp.getPhoneNumber(); // 휴대폰번호
        String emailAuthNumber = CommonUtil.makeRandomChar(15); // 이메일인증 코드
        String businessNumber = signUp.getBusinessNumber(); // 사업자등록번호

        /* NICEID를 통해 휴대폰 인증을 완료했는 지 확인 */
        if(!signUp.getEmail().equals("kokonut@kokonut.me")) { // 테스트일 경우 패스
            String mobileno = "";
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                log.info("현재 쿠키값들 : "+ Arrays.toString(cookies));
                for(Cookie c : cookies) {
                    if(c.getName().equals("mobileno") ) {
                        mobileno = c.getValue();
                        log.info("본인인증 된 핸드폰번호 : "+mobileno);
                    }
                }
            }

            // 본인인증 체크
            if(!phoneNumber.equals(mobileno)) {
                log.info("본인인증으로 입력된 핸드폰 번호가 아닙니다. 본인인증을 완료해주세요.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO033.getCode(), ResponseErrorCode.KO033.getDesc()));
            }
        }

        // 중복체크 로직
        // 핸드폰번호 중복 체크
        if (adminRepository.existsByPhoneNumber(phoneNumber)) {
            log.error("이미 회원가입된 핸드폰번호 입니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO034.getCode(), ResponseErrorCode.KO034.getDesc()));
        }

        // 사업자번호 중복 체크
        if (companyRepository.existsByBusinessNumber(businessNumber)) {
            log.error("이미 회원가입된 사업자등록번호 입니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO035.getCode(), ResponseErrorCode.KO035.getDesc()));
        } else if(businessNumber.length() != 10){
            log.error("입력하신 사업자등록번호는 10자리가 아닙니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO035.getCode(), ResponseErrorCode.KO035.getDesc()));
        }

        // 비밀번호 일치한지 체크
        if (!signUp.getPassword().equals(signUp.getPasswordConfirm())) {
            log.error("입력한 비밀번호가 서로 일치하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO037.getCode(), ResponseErrorCode.KO037.getDesc()));
        }

        MultipartFile multipartFile = signUp.getMultipartFile();
        // 사업자등록증을 올렸는지 체크
        if(multipartFile == null && !signUp.getEmail().equals("kokonut@kokonut.me")) { // 테스트일 경우 패스
            log.error("사업자등록증을 업로드해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO038.getCode(), ResponseErrorCode.KO038.getDesc()));
        }

        // 이메일 인증번호 생성
        while(true) {
            // 해당 emailAuthNumber로 가입된 회원이 존재한다면 재생성
            if(!adminRepository.existsByEmailAuthNumber(emailAuthNumber)) {
                break;
            } else {
                emailAuthNumber = CommonUtil.makeRandomChar(15);
            }
        }


        log.info("Company 저장(Insert) 로직 시작");
        Company company = new Company();

        // 저장할 KMS 암호화키, 복호화키 생성
        String encryptText = "";
        String dataKey = "";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String currDate = formatter.format(new Date());
        String encKey = currDate + businessNumber; // -> 기존의 encKey 생성은 가입한 날짜시간+사업자등록번호
        log.info("encKey : "+encKey);
        // -> woody 수정 가입날짜 년도+월+사업자등록번호 ex) 가입날짜 년월 : 202301, 사업자등록번호(10자리) 1234567890 -> encKey = 2023011234567890 으로 수정
        // -> 이유는 개인정보등록시 암호화가 필요한 필드일 경우 해당 key를 통해 암호화처리를 할때 Aes암호화를 해야하는데 encKey가 16byte가 넘어가게 되면 예외처리를 하여 16byte로 맞춤.
        // 사업자 등록번호는 10자리를 넘어가면 안되고, 10자리 미만이여도 안되는 조건 추가한다.

//        20230112 3488101536
//        if(!signUp.getEmail().equals("kokonut@kokonut.me")) { // 테스트일 경우 패스
            AwsKmsResultDto awsKmsResultDto = awsKmsUtil.encrypt(encKey);
            if (awsKmsResultDto.getResult().equals("success")) {
                encryptText = awsKmsResultDto.getEncryptText();
                dataKey = awsKmsResultDto.getDataKey();
            } else {
                // 리턴처리를 어떻게해야 할지 내용 정하기 - 2022/12/22 to.woody
                log.error("암호화 키 생성 실패");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO036.getCode(), ResponseErrorCode.KO036.getDesc()));
            }
//        }

        company.setCompanyName(signUp.getCompanyName());
        company.setRepresentative(signUp.getRepresentative());
        company.setCompanyTel(signUp.getCompanyTel());
        company.setBusinessType(signUp.getBusinessType());
        company.setBusinessNumber(businessNumber);
        company.setCompanyAddress(signUp.getCompanyAddress());
        company.setCompanyAddressNumber(signUp.getCompanyAddressNumber());
        company.setCompanyAddressDetail(signUp.getCompanyAddressDetail());
        company.setEncryptText(encryptText);
        company.setDataKey(dataKey);
        company.setRegdate(LocalDateTime.now());
        Company saveCompany = companyRepository.save(company);
        log.info("기업 정보 저장 saveCompany : "+saveCompany.getIdx());

        log.info("KMS 발급 이력 저장(Insert) 로직 시작");
        AwsKmsHistory awsKmsHistory = new AwsKmsHistory();
        awsKmsHistory.setType("ENC");
        awsKmsHistory.setRegdate(LocalDateTime.now());
        AwsKmsHistory saveAwsKmsHistory =  awsKmsHistoryRepository.save(awsKmsHistory);
        log.info("KMS 이력 저장 saveAwsKmsHistory : "+saveAwsKmsHistory.getIdx());

        log.info("Admin 저장(Insert) 로직 시작");
        // 회원가입 사업자 Defalut 값
        Integer userType = 1; // "1"일 경우 사업자, "2"일 경우 관리자
        Integer state = 1; // 0:정지(권한해제), 1:사용, 2:로그인제한(비번5회오류), 3:탈퇴, 4:휴면계정

        Admin admin = new Admin();
        admin.setEmail(signUp.getEmail());
        admin.setPassword(passwordEncoder.encode(signUp.getPassword()));
        admin.setCompanyIdx(saveCompany.getIdx());
        admin.setMasterIdx(0); // 사업자는 0, 관리자가 등록한건 관리자IDX ?
        admin.setName(signUp.getName());
        admin.setPhoneNumber(signUp.getPhoneNumber());
        admin.setState(state);
        admin.setUserType(userType);
        admin.setRoleName(AuthorityRole.ROLE_MASTER);
        admin.setRegdate(LocalDateTime.now());
        Admin saveAdmin = adminRepository.save(admin);
        log.info("사용자 정보 저장 saveAdmin : "+saveAdmin.getIdx());

        if(multipartFile != null && !signUp.getEmail().equals("kokonut@kokonut.me")) {
            log.info("사업자등록증 업로드 시작 multipartFile : "+multipartFile);
            log.info("CompanyFile 저장(Insert) 로직 시작");

            CompanyFile companyFile = new CompanyFile();
            companyFile.setCompanyIdx(saveCompany.getIdx());

            // 파일 오리지널 Name
            String originalFilename = Normalizer.normalize(Objects.requireNonNull(multipartFile.getOriginalFilename()), Normalizer.Form.NFC);
            log.info("originalFilename : "+originalFilename);
            companyFile.setCfOriginalFilename(originalFilename);

            // 파일 Size
            long fileSize = multipartFile.getSize();
            log.info("fileSize : "+fileSize);
            companyFile.setCfVolume(fileSize);

            // 확장자
            String ext;
            ext = '.'+originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            log.info("ext : "+ext);

            // 파일 중복명 처리
            String fileName = UUID.randomUUID().toString().replace("-", "")+ext;
            log.info("fileName : "+fileName);
            companyFile.setCfFilename(fileName);

            // S3에 저장 할 파일주소
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
            String filePath = AWSURL+businessS3Folder+date.format(new Date());
            log.info("filePath : "+filePath);
            companyFile.setCfPath(filePath);

            companyFile.setRegIdx(saveAdmin.getIdx());
            companyFile.setRegDate(LocalDateTime.now());

            // S3 파일업로드
            String storedFileName = awsS3Util.imageFileUpload(multipartFile, fileName, businessS3Folder+date.format(new Date()));
            if(storedFileName == null) {
                log.error("이미지 업로드를 실패했습니다. -관리자에게 문의해주세요-");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO039.getCode(), ResponseErrorCode.KO039.getDesc()));
            } else {
                companyFileRepository.save(companyFile);
            }
        }

        // 메일보내는 로직
        // 메일 서비스 완료되면 할 것 - 2022/12/23 to.woody
//        List<HashMap<String, Object>> systemAdminList = adminService.SelectSystemAdminList();
        List<String> systemAdminList = new ArrayList<>();
//            try {
//                String mailData = URLEncoder.encode(parameter.get("email").toString(), "UTF-8");
//                String title = "사업자 회원가입 승인요청";
//                String contents = mailSender.getHTML2("/mail/emailForm/" + Integer.toString(MailController.EmailFormType.REQ_AGREE_BIZ_MEM_JOIN.ordinal()) + "?data=" + mailData);
//
//                for(HashMap<String, Object> systemAdmin : systemAdminList) {
//                    if(systemAdmin.containsKey("EMAIL") && systemAdmin.containsKey("NAME")) {
//                        String toEmail = systemAdmin.get("EMAIL").toString();
//                        String toName = systemAdmin.get("NAME").toString();
//
//                        mailSender.sendMail(toEmail, toName, title, contents);
//                    } else {
//                        logger.error("not found system admin info.");
//                    }
//                }
//            } catch (IOException e) {
//                logger.error(e.getMessage());
//            }

        /* NICEID 본인인증 핸드폰 번호 쿠키 삭제 */
        if(!signUp.getEmail().equals("kokonut@kokonut.me")) { // 테스트일 경우 패스
            Cookie cookie = new Cookie("mobileno", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

        data.put("email", saveAdmin.getEmail());

        return ResponseEntity.ok(res.success(data));
    }

    // 로그인, 구글OTP 확인후 -> JWT 발급기능
    public ResponseEntity<Map<String,Object>> authToken(AuthRequestDto.Login login) {
        log.info("authToken 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
        // 이메일이 test@kokonut.me 체크하는 로직추가
        String email = login.getEmail();

        if (email.equals("test@kokonut.me") || email.equals("test2@kokonut.me")) {
            // 여기부터 우디(Wooody)가 작성
            System.out.println("체험하기 모드 : 체험하기 로그인");

            data.put("jwtToken", "체험하기JWT");
            return ResponseEntity.ok(res.success(data));
        }
        else {
            if(login.getOtpValue() == null) {
                log.error("구글 OTP 값이 존재하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO010.getCode(),ResponseErrorCode.KO010.getDesc()));
            }

            Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

            if (optionalAdmin.isEmpty()) {
                log.error("해당 유저가 존재하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
            }
            else {
                try {
                    // Login ID/PW 를 기반으로 Authentication 객체 생성 -> 아아디 / 비번 검증
                    // 이때 authentication은 인증 여부를 확인하는 authenticated 값이 false
                    UsernamePasswordAuthenticationToken authenticationToken = login.toAuthentication();

                    if(optionalAdmin.get().getOtpKey() == null){
                        log.error("등록된 OTP가 존재하지 않습니다. 구글 OTP 2단계 인증을 등록해주세요.");
                        return ResponseEntity.ok(res.fail(ResponseErrorCode.KO011.getCode(),ResponseErrorCode.KO011.getDesc()));
                    }
                    else {

                        // OTP 검증 절차
                        boolean auth = googleOTP.checkCode(login.getOtpValue(), optionalAdmin.get().getOtpKey());
                        log.info("auth : " + auth);

                        if (!auth) {
                            log.error("입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요.");
                            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO012.getCode(), ResponseErrorCode.KO012.getDesc()));
                        } else {
                            log.info("OTP인증완료 -> JWT토큰 발급");


                            // 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
                            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
                            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                            // 인증 정보를 기반으로 JWT 토큰 생성
                            AuthResponseDto.TokenInfo jwtToken = jwtTokenProvider.generateToken(authentication);

                            data.put("jwtToken", jwtToken);

                            // RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
                            redisTemplate.opsForValue().set("RT: " + authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

                            /* 해외 아이피 차단 여부 */
//                            loginService.ResetPwdError(user.getIdx()); 패스워드에러카운트 리셋

                            return ResponseEntity.ok(res.success(data));
                        }
                    }
                }
                catch (BadCredentialsException e) {
                    log.error("아이디 또는 비밀번호가 일치하지 않습니다.");
                    return ResponseEntity.ok(res.fail(ResponseErrorCode.KO016.getCode(),ResponseErrorCode.KO016.getDesc()));
                }
            }
        }
    }

    // JWT 토큰 새로고침 기능
    public ResponseEntity<Map<String,Object>> reissue(AuthRequestDto.Reissue reissue) {
        log.info("reissue 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            log.error("유효하지 않은 토큰정보임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO007.getCode(),ResponseErrorCode.KO007.getDesc()));
        }

        // Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = redisTemplate.opsForValue().get("RT: "+authentication.getName());

        // 로그아웃되어 Redis 에 RefreshToken 이 존재하지 않는 경우 처리
        if(ObjectUtils.isEmpty(refreshToken)) {
            log.error("로그아웃된 토큰임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO006.getCode(),ResponseErrorCode.KO006.getDesc()));
        }

        if(!refreshToken.equals(reissue.getRefreshToken())) {
            log.error("Redis토큰과 받은 리플레쉬 토큰이 맞지않음");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO008.getCode(),ResponseErrorCode.KO008.getDesc()));
        }

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(reissue.getAccessToken());
        redisTemplate.opsForValue().set(reissue.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        // 새로운 토큰 생성
        AuthResponseDto.TokenInfo jwtToken = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 업데이트
        redisTemplate.opsForValue().set("RT: "+authentication.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        data.put("jwtToken", jwtToken);

        return ResponseEntity.ok(res.success(data));
    }

    // 로그아웃 기능
    public ResponseEntity<Map<String,Object>> logout(AuthRequestDto.Logout logout) {
        log.info("logout 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
        // Access Token 검증
        if (!jwtTokenProvider.validateToken(logout.getAccessToken())) {
            log.error("유효하지 않은 토큰정보임");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO006.getCode(),ResponseErrorCode.KO006.getDesc()));
        }

        // Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(logout.getAccessToken());

        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT: "+authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT: "+authentication.getName());
        }

        // 해당 Access Token 유효시간 가지고 와서 BlackList로 저장하기
        Long expiration = jwtTokenProvider.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue().set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return ResponseEntity.ok(res.success(data));
    }

    // 구글 QR코드 생성(1번)
    public ResponseEntity<Map<String, Object>> otpQRcode(String email) {
        log.info("otpQRcode 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
//        String userEmail = SecurityUtil.getCurrentUserEmail();
//        if(userEmail.equals("anonymousUser")){
//            log.error("사용하실 수 없는 토큰정보 입니다. 다시 로그인 해주세요.");
//            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO009.getCode(),ResponseErrorCode.KO009.getDesc()));
//        } else {

        Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

        if (optionalAdmin.isEmpty()) {
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {
            log.info("구글 QR코드 보낼 이메일 : " + email);

            // 서프의 Map관련 이슈
//            HashMap<String, String> map = googleOTP.generate(userEmail);
            GoogleOtpGenerateDto googleOtpGenerateDto = googleOTP.generate(email);
//            String otpKey = map.get("encodedKey");
            String url = googleOtpGenerateDto.getUrl().replace("200x200", "160x160");

//            url = url.replace("200x200", "160x160");

            data.put("CSRF_TOKEN", UUID.randomUUID().toString());
            data.put("otpKey", googleOtpGenerateDto.getOtpKey());
            data.put("url", url);

            return ResponseEntity.ok(res.success(data));
        }
    }

    // 구글 OTP 값 확인(2번)
    public ResponseEntity<Map<String,Object>> checkOTP(AdminGoogleOTPDto.GoogleOtpCertification googleOtpCertification) {
        log.info("checkOTP 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
        Pattern pattern = Pattern.compile("[+-]?\\d+");
        if(!pattern.matcher(googleOtpCertification.getOtpValue()).matches()) {
            log.error("OTP는 숫자 형태로 입력하세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO015.getCode(),ResponseErrorCode.KO015.getDesc()));
        }

        boolean auth;
        auth = googleOTP.checkCode(googleOtpCertification.getOtpValue(), googleOtpCertification.getOtpKey());


        if(!auth){
            log.error("입력된 구글 OTP 값이 일치하지 않습니다. 확인해주세요.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO012.getCode(),ResponseErrorCode.KO012.getDesc()));
        }
        else {
            data.put("auth", true);
            return ResponseEntity.ok(res.success(data));
        }

    }

    // 구글 OTPKey 등록(3번)
    public ResponseEntity<Map<String, Object>> saveOTP(AdminGoogleOTPDto.GoogleOtpSave googleOtpSave) {
        log.info("saveOTP 호출");

        AjaxResponse res = new AjaxResponse();
        HashMap<String, Object> data = new HashMap<>();
        
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(googleOtpSave.getEmail());

        if (optionalAdmin.isEmpty()) {
            log.error("해당 유저가 존재하지 않습니다.");
            return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(),"해당 유저가 "+ResponseErrorCode.KO004.getDesc()));
        }
        else {

            log.info("OTP Key 등록 할 이메일 : " + optionalAdmin.get().getEmail());

            //현재암호비교
            if (!passwordEncoder.matches(googleOtpSave.getPassword(), optionalAdmin.get().getPassword())){
                log.error("입력한 비밀번호가 일치하지 않습니다.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            String authOtpKey = googleOtpSave.getAuthOtpKey();
            AriaUtil aria = new AriaUtil();
            String decValue = aria.Decrypt(authOtpKey);
            System.out.println("decValue : "+decValue);

            if (!decValue.equals("authOtpKey11")) { // 본인인증 이후 나오는 값
                log.error("인증되지 않은 절차로 진행되었습니다. 올바른 방법으로 진행해주세요.");
                return ResponseEntity.ok(res.fail(ResponseErrorCode.KO013.getCode(), ResponseErrorCode.KO013.getDesc()));
            }

            optionalAdmin.get().setModifierIdx(optionalAdmin.get().getModifierIdx());
            optionalAdmin.get().setModifierName(optionalAdmin.get().getName());
            optionalAdmin.get().setModifyDate(LocalDateTime.now());

            optionalAdmin.get().setOtpKey(googleOtpSave.getOtpKey());
            adminRepository.save(optionalAdmin.get());

            // 변경이후 시스템관리자에게 메일보내기 시작 추가하기 12/05 Woody

            return ResponseEntity.ok(res.success(data));
        }
    }

}
