package com.app.kokonutuser;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.admin.dtos.AdminOtpKeyDto;
import com.app.kokonut.auth.jwt.config.GoogleOTP;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.company.CompanyService;
import com.app.kokonut.totalDBDownload.TotalDbDownload;
import com.app.kokonut.totalDBDownload.TotalDbDownloadRepository;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.app.kokonut.woody.common.component.AesCrypto;
import com.app.kokonut.woody.common.component.CommonUtil;
import com.app.kokonut.woody.excel.ExcelService;
import com.app.kokonutdormant.KokonutDormantService;
import com.app.kokonutdormant.dtos.KokonutDormantFieldCheckDto;
import com.app.kokonutdormant.dtos.KokonutDormantFieldInfoDto;
import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutremove.KokonutRemoveService;
import com.app.kokonutuser.dtos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Woody
 * Date : 2022-12-28
 * Time :
 * Remark : DynamicUserService -> DynamicUserRestController 에서 호출하는 서비스
 */
@Slf4j
@Service
public class DynamicUserService {

	private final GoogleOTP googleOTP;
	private final PasswordEncoder passwordEncoder;

	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;
	private final TotalDbDownloadRepository totalDbDownloadRepository;

	private final ExcelService excelService;
	private final KokonutUserService kokonutUserService;
	private final CompanyService companyService;
	private final ActivityHistoryService activityHistoryService;

	private final KokonutDormantService kokonutDormantService;
	private final KokonutRemoveService kokonutRemoveService;


	@Autowired
	public DynamicUserService(GoogleOTP googleOTP, PasswordEncoder passwordEncoder, AdminRepository adminRepository,
							  CompanyRepository companyRepository, ExcelService excelService,
							  KokonutUserService kokonutUserService, KokonutDormantService kokonutDormantService, CompanyService companyService,
							  ActivityHistoryService activityHistoryService, KokonutRemoveService kokonutRemoveService, TotalDbDownloadRepository totalDbDownloadRepository) {
		this.googleOTP = googleOTP;
		this.passwordEncoder = passwordEncoder;
		this.adminRepository = adminRepository;
		this.companyRepository = companyRepository;
		this.excelService = excelService;
		this.kokonutUserService = kokonutUserService;
		this.kokonutDormantService = kokonutDormantService;
		this.companyService = companyService;
		this.activityHistoryService = activityHistoryService;
		this.kokonutRemoveService = kokonutRemoveService;
		this.totalDbDownloadRepository = totalDbDownloadRepository;
	}

	/**
	 * 시스템 관리자가 지정한 기본 테이블 정보 조회
	 */
//	public List<HashMap<String, Object>> SelectCommonUserTable() {
//		return dynamicUserDao.SelectCommonUserTable();
//	}

	// KokonutUserService 테스트용 메서드
	public ResponseEntity<Map<String,Object>> serviceTest(String email) {
		log.info("serviceTest 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx();
			companyIdx = adminCompanyInfoDto.getCompanyIdx();
			businessNumber = adminCompanyInfoDto.getBusinessNumber();
		}

		log.info("adminIdx : "+adminIdx);
		log.info("companyIdx : "+companyIdx);
		log.info("businessNumber : "+businessNumber);

		// 사업자번호가 정상적으로 등록되어 있는지 확인
		if (!companyRepository.existsByBusinessNumber(businessNumber)) {
			log.error("등록되지 않는 사업자번호입니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO000.getCode(), ResponseErrorCode.KO000.getDesc()));
		}

		// 테스트 실행 메서드
		testStart(businessNumber);

		return ResponseEntity.ok(res.success(data));
	}

	// 테스트 실행 메서드
	public void testStart(String businessNumber) {
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ - KokonutUserService 테스트 - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// kokonut_user DB 생성 (유저 테이블) - 테스트완료 woody
//		boolean result = kokonutUserService.createTableKokonutUser(businessNumber);
//		log.info("result : "+result);

		//kokonut_user DB 중복체크 - 테스트완료 woody
//		int check = kokonutUserService.selectExistUserTable(businessNumber);
//		log.info("check : "+check);

		// kokonut_user DB 삭제 (유저 테이블) - 테스트완료 woody
//		String result = kokonutUserService.deleteTableKokonutUser(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 필드값 추가 - 테스트완료 woody
//		boolean result = kokonutUserService.alterAddColumnTableQuery(businessNumber, "test", "VARCHAR", 50, true, "");
//		log.info("result : "+result);

		// kokonut_user DB 필드정보 수정 - 테스트완료 woody
//		boolean result = kokonutUserService.alterChangeColumnTableQuery(businessNumber, "test", "testchange", "VARCHAR", 50, true, "수정테스트", "기본값");
//		log.info("result : "+result);

		// kokonut_user DB 필드 코멘트 수정 - 테스트완료 woody
//		boolean result = kokonutUserService.alterModifyColumnCommentQuery(businessNumber, "testchange", "VARCHAR", 50, true, "코멘트수정완료", "기본값");
//		log.info("result : "+result);

		// kokonut_user DB 필드 삭제 - 테스트완료 woody
//		boolean result = kokonutUserService.alterDropColumnTableQuery(businessNumber, "testchangee");
//		log.info("result : "+result);

		// kokonut_user DB 회원리스트 조회 - 테스트완료 woody
//		List<Map<String, Object>> result = kokonutUserService.selectUserList(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 회원 수 조회 - 테스트완료 woody
//		int result = kokonutUserService.selectUserListCount(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 회원등록 - 테스트완료 woody -> 인서트 쿼리문은 DynamicUserService 부분에서 직접 가공하여 호출
//		String nameString = "(`NAME`, `GENDER`, `BIRTH`, `PHONE_NUMBER`, `REGDATE`, `ID`, `PASSWORD`, `PERSONAL_INFO_AGREE`, `STATE`, `EMAIL`)";
//		String valueString = "('테스트임다','0','19910101','01012123344','2022-11-09 15:00:00','test10', '"+passwordEncoder.encode("123456")+"' ,'Y',1,'test10@kkn.me')";
//		boolean result = kokonutUserService.insertUserTable(businessNumber, nameString, valueString);
//		log.info("result : "+result);

		// kokonut_user DB 회원수정 - 테스트완료 woody -> 업데이트 쿼리문은 DynamicUserService 부분에서 직접 가공하여 호출
//		String queryString = "NAME = '이름변경', ID='idchange'";
//		boolean result = kokonutUserService.updateUserTable(businessNumber, 8, queryString);
//		log.info("result : "+result);

		// kokonut_user DB 회원삭제 - 테스트완료 woody
//		boolean result = kokonutUserService.deleteUserTable(businessNumber, 8);
//		log.info("result : "+result);

		// kokonut_user DB 단일조회 - 테스트완료 woody
//		List<KokonutUserRemoveInfoDto> result = kokonutUserService.selectUserDataByIdx(businessNumber, 8);
//		log.info("result.get(0).getIDX() : "+result.get(0).getIDX());
//		log.info("result.get(0).getID() : "+result.get(0).getID());

		// kokonut_user DB 1년전에 가입한 회원 목록조회 - 테스트완료 woody
//		List<KokonutOneYearAgeRegUserListDto> result = kokonutUserService.selectOneYearAgoRegUserListByTableName(businessNumber);
//		log.info("result.get(0).getIDX() : "+result.get(0).getIDX());
//		log.info("result.get(0).getNAME() : "+result.get(0).getNAME());

		// kokonut_user DB 회원 등록여부 조회 - 테스트완료 woody
//		Integer result = kokonutUserService.selectCount(businessNumber, 8);
//		log.info("result : "+result);

		// kokonut_user DB 회원 마지막 IDX 조회 - 테스트완료 woody
//		Integer result = kokonutUserService.selectTableLastIdx(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 회원테이블의 컬럼리스트 조회 - 테스트완료 woody
//		List<KokonutUserFieldDto> result = kokonutUserService.getColumns(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 회원테이블의 암호화 속성의 컬럼리스트 조회 - 테스트완료 woody
//		List<KokonutUserFieldDto> result = kokonutUserService.selectEncryptColumns(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 회원테이블의 암호화 속성의 컬럼리스트 조회 - 테스트완료 woody -> 바뀔 필요가 있어보임
//		String result = kokonutUserService.selectIdByFieldAndValue(businessNumber, "STATE", 1);
//		log.info("result : "+result);

		// kokonut_user DB 회원테이블의 암호화 속성의 컬럼리스트 조회 - 테스트완료 woody
//		List<KokonutUserFieldInfoDto> result = kokonutUserService.selectFieldList(businessNumber, "STATE");
//		log.info("result : "+result);

		// kokonut_user DB 회원의 비밀번호 검증 - 테스트완료 woody
		// -> result : "-1" 입력한비밀번호와 현재비밀번호가 맞지 않음, "IDX값" 변경성공, "0" 변경할 id(아이디)가 존재하지 않음
//		Long passwordConfirm = kokonutUserService.passwordConfirm(businessNumber, "idchange", "123456");
//		log.info("passwordConfirm : "+passwordConfirm);

		// kokonut_user DB 회원의 비밀번호 확인 후 결과의 대해 비밀번호 변경 로직 시작 - 테스트완료 woody
//		if(passwordConfirm != 0L && passwordConfirm != -1L){
//			boolean result = kokonutUserService.updatePassword(businessNumber, passwordConfirm, "456789");
//			log.info("result : "+result);
//		}

		// kokonut_user DB 회원의 비밀번호 확인 후 결과의 대해 로그인 접속시간 업데이트 로직 시작 - 테스트완료 woody
//		boolean result = kokonutUserService.updateLastLoginDate(businessNumber, passwordConfirm);
//		log.info("result : "+result);

		// kokonut_user DB 현재로부터 한달안에 가입한 유저의 수 조회 - 테스트완료 woody
//		Integer result = kokonutUserService.selectCountByThisMonth(businessNumber);
//		log.info("result : "+result);

		// kokonut_user DB 유저리스트 조회(listUserDatabase 메서드) - 테스트완료 woody

		// kokonut_user DB ID가 존재하는지 체크 - 테스트완료 woody
//		boolean result = kokonutUserService.isExistId(businessNumber, "test6");
//		log.info("result : "+result);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ - KokonutRemoveService 테스트 - @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

		// kokonut_remove DB 회원등록 - 테스트완료 woody -> 인서트 쿼리문은 DynamicUserService 부분에서 직접 가공하여 호출
//		String nameString = "(`IDX`, `NAME`, `GENDER`, `BIRTH`)";
//		String valueString = "(1, '테스트임다','0','19910101')";
//		boolean result = kokonutRemoveService.insertRemoveTable(businessNumber, nameString, valueString);
//		log.info("result : "+result);

		// kokonut_remove DB 회원삭제 - 테스트완료 woody
//		boolean result2 = kokonutRemoveService.deleteRemoveTable(businessNumber, 1);
//		log.info("result2 : "+result2);

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	}



	// 동적테이블 생성
	@Transactional
	public ResponseEntity<Map<String,Object>> createTable(String email) {
		log.info("createTable 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx();
			companyIdx = adminCompanyInfoDto.getCompanyIdx();
			businessNumber = adminCompanyInfoDto.getBusinessNumber();
		}

		log.info("adminIdx : "+adminIdx);
		log.info("companyIdx : "+companyIdx);
		log.info("businessNumber : "+businessNumber);

		/* 활동이력 추가 */
//			String reason = "테이블 생성" + "(테이블명 :" + businessNumber + ")";
//            activityHistory = activityHistoryService.InsertActivityHistory(2, companyIdx, adminIdx, 16, "", reason, CommonUtil.clientIp(), 0);

		// 사업자번호가 정상적으로 등록되어 있는지 확인
		if (!companyRepository.existsByBusinessNumber(businessNumber)) {
			log.error("등록되지 않는 사업자번호입니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO000.getCode(), ResponseErrorCode.KO000.getDesc()));
		}

//			if(dynamicUserService.ExistTable(businessNumber) || dynamicRemoveService.ExistTable(businessNumber) || dynamicDormantService.ExistTable(businessNumber)) {
//				logger.info("### Duplicate kokonut_user DB Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "유저 테이블 생성에 실패했습니다.");
//				break;
//			}

		// kokonut_user DB 생성 (유저 테이블) - 테스트완료 woody
//		boolean result = kokonutUserService.createTableKokonutUser(businessNumber);
//		log.info("result : "+result);

//			if(!dynamicUserService.CreateDynamicTable(businessNumber)) {
//				logger.info("###[kokonut_user DB 항목 관리] Create Table Field. Database : kokonut_user, Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "유저 테이블 생성에 실패했습니다.");
//				break;
//			}

		// kokonut_user DB 생성 (삭제, 탈퇴용 테이블)
//			if(!dynamicRemoveService.CreateDynamicTable(businessNumber)) {
//				// RollBack
//				dynamicUserService.DeleteDynamicTable(businessNumber);
//
//				logger.info("###[kokonut_user DB 항목 관리] Create Table Field. Database : kokonut_remove, Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "탈퇴용 테이블 생성에 실패했습니다.");
//				break;
//			}
		// kokonut_user DB 생성 (휴면용 테이블)
//			if(!dynamicDormantService.CreateDynamicTable(businessNumber)) {
//				// RollBack
//				dynamicRemoveService.DeleteDynamicTable(businessNumber);
//				dynamicUserService.DeleteDynamicTable(businessNumber);
//
//				logger.info("###[kokonut_user DB 항목 관리] Create Table Field. Database : kokonut_dormant, Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "휴면용 테이블 생성에 실패했습니다.");
//				break;
//			}

		/* 활동이력 정상으로 변경 */
//            int activityHistoryIdx = Integer.parseInt(activityHistory.get("idx").toString());
//            activityHistoryService.UpdateActivityHistory(activityHistoryIdx, "", reason, 1);

//			returnMap.put("isSuccess", "true");


//        // break로 처리 중단시 활동이력에 사유입력
//        String isSuccess = returnMap.get("isSuccess").toString();
//        String errorMsg = returnMap.get("errorMsg").toString();
//        if(isSuccess.equals("false")) {
//            int activityHistoryIdx = Integer.parseInt(activityHistory.get("idx").toString());
//            activityHistoryService.UpdateActivityHistory(activityHistoryIdx, "", errorMsg, 0);
//        }

//		return null;



		return ResponseEntity.ok(res.success(data));
	}

	// 유저DB(테이블) 리스트조회
	public ResponseEntity<Map<String, Object>> userListCall(KokonutUserSearchDto kokonutUserSearchDto, String email) {
		log.info("userListCall 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("kokonutUserListSearchDto : "+ kokonutUserSearchDto);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		String businessNumber = adminCompanyInfoDto.getBusinessNumber();

		LocalDateTime nowDate = LocalDateTime.now();
		LocalDateTime sixMonthBefore = nowDate.minusMonths(6);
		log.info("현재 날짜 : "+nowDate);
		log.info("6개월전 날짜 : "+sixMonthBefore);
		if(kokonutUserSearchDto.getStimeStart() == null) {
			kokonutUserSearchDto.setStimeStart(sixMonthBefore); // 기본값 6개월전 날짜
		}
		if(kokonutUserSearchDto.getStimeEnd() == null) {
			kokonutUserSearchDto.setStimeEnd(nowDate); // 기본값 현재 날짜
		}

		List<KokonutUserListDto> userList = new ArrayList<>();
		List<KokonutDormantListDto> dormantList = new ArrayList<>();

		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Object> result;
		if(kokonutUserSearchDto.getStatus().equals("1")) {

			log.info("사용중만 조회");
			userList = kokonutUserService.listUser(kokonutUserSearchDto, businessNumber);
			log.info("userList : "+userList);

		}
		else if(kokonutUserSearchDto.getStatus().equals("2")) {

			log.info("휴먼만 조회");
			dormantList = kokonutDormantService.listDormant(kokonutUserSearchDto, businessNumber);
			log.info("dormantList : "+dormantList);

		}
		else {

			log.info("둘다 조회");

			userList = kokonutUserService.listUser(kokonutUserSearchDto, businessNumber);
			log.info("userList : "+userList);

			dormantList = kokonutDormantService.listDormant(kokonutUserSearchDto, businessNumber);
			log.info("dormantList : "+dormantList);
		}

		for(KokonutUserListDto kokonutUserListDto : userList) {
			result = new HashMap<>();
			result.put("ID", kokonutUserListDto.getID());
			result.put("IDX", kokonutUserListDto.getIDX());
			result.put("REGDATE", kokonutUserListDto.getREGDATE());
			result.put("LAST_LOGIN_DATE", kokonutUserListDto.getLAST_LOGIN_DATE());
			result.put("TYPE", "USER");
			resultList.add(result);
		}

		for(KokonutDormantListDto kokonutDormantListDto : dormantList) {
			result = new HashMap<>();
			result.put("ID", kokonutDormantListDto.getID());
			result.put("IDX", kokonutDormantListDto.getIDX());
			result.put("REGDATE", kokonutDormantListDto.getREGDATE());
			result.put("LAST_LOGIN_DATE", kokonutDormantListDto.getLAST_LOGIN_DATE());
			result.put("TYPE", "DORMANT");
			resultList.add(result);
		}

		// 등록날짜 ->내림차순 정렬
		if(!kokonutUserSearchDto.getStatus().equals("1") && !kokonutUserSearchDto.getStatus().equals("2")) {
			resultList.sort(
					Comparator.comparing((Map<String, Object> map) -> (Timestamp) map.get("REGDATE")).reversed()
			);
		}

//		// 등록날짜 -> 오름차순 정렬
//		resultList.sort(
//				Comparator.comparing((Map<String, Object> map) -> (Timestamp) map.get("REGDATE"))
//					.thenComparing((Map<String, Object> map) -> (Long) map.get("IDX")).reversed() // 다중컬럼 정렬
//		);

		log.info("resultList : "+resultList);

		data.put("resultList", resultList);

		return ResponseEntity.ok(res.success(data));
	}

	// 유저생성(회원생성)
	@Transactional
	@SuppressWarnings("unchecked")
	public ResponseEntity<Map<String, Object>> userSaveCall(HashMap<String, Object> paramMap, String email) {
		log.info("userSaveCall 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("paramMap : "+ paramMap);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		String controlType = paramMap.get("controlType").toString();
		if(controlType.equals("1")) {
			controlType = "사용";
		} else if(controlType.equals("2")) {
			controlType = "휴면";
		} else {
			log.error("회원상태 여부를 선택해주세요. controlType : "+controlType);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO044.getCode(), ResponseErrorCode.KO044.getDesc()));
		}

		// 회원등록 코드
		ActivityCode activityCode = ActivityCode.AC_13;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(1, companyIdx, adminIdx, activityCode, businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);
		String id = null;
		try {

			// 회사의 암호화 키
			String DECRYPTED_KEY = companyService.selectCompanyEncryptKey(companyIdx);

			if(DECRYPTED_KEY == null) {
				log.error("해당 기업의 암호화 키가 존재하지 않습니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO043.getCode(), ResponseErrorCode.KO043.getDesc()));
			}

			log.info(controlType+" 저장입니다.");

			List<HashMap<String,Object>> userData = (List<HashMap<String, Object>>) paramMap.get("userData");

			boolean isFieldCheck = false; // 필드 체크

			List<KokonutUserFieldDto> columns = kokonutUserService.getUserColumns(businessNumber); // columns
			List<KokonutUserFieldDto> encryptColumns = kokonutUserService.selectUserEncryptColumns(businessNumber); // encryptColumns

			StringBuilder nameString = new StringBuilder();
			StringBuilder valueString = new StringBuilder();

			for (int i=0; i<userData.size(); i++) {
				String field = userData.get(i).get("name").toString(); // 필드 이름
				String value = userData.get(i).get("value").toString(); // 필드 값

				if("".equals(value)) {
					continue; // 입력된 값이 없을 경우 패스
				}

				// 입력한 필드가 존재하는지 검증
				for(KokonutUserFieldDto column : columns) {
					if(field.equals(column.getField())) {
						isFieldCheck = true;
						columns.remove(column);
						break;
					}
				}

				// 필드가 존재하지 않을 경우 리턴처리
				if(!isFieldCheck) {
					log.error("회원정보에 필드가 존재하지 않습니다. 핃드명 : "+field);
					return ResponseEntity.ok(res.fail(ResponseErrorCode.KO045.getCode(), field+ResponseErrorCode.KO045.getDesc()));
				}

				// 필드 이름이 ID일 경우
				if(field.equals("ID")) {
					// 아이디 중복검사
					boolean isUserExistCheck = kokonutUserService.isUserExistId(businessNumber, value);
					boolean isDormantExistCheck = kokonutDormantService.isDormantExistId(businessNumber, value);
					if(isUserExistCheck || isDormantExistCheck) {
						log.error("이미 존재한 ID 입니다. 아이디 : "+value);
						return ResponseEntity.ok(res.fail(ResponseErrorCode.KO046.getCode(), ResponseErrorCode.KO046.getDesc()));
					} else {
						log.info("사용하실 수 있는 아이디 입니다. : "+value);
						id = value;
					}
				}

				try {
					// 비밀번호 암호화
					if(field.equals("PASSWORD")) {
						// 암호화된 비밀번호로 변경
						value = passwordEncoder.encode(value);
					}
					// 암호화 속성을 갖는 컬럼의 데이터 암호화
					else {
						for(KokonutUserFieldDto column : encryptColumns) {
							if(column.getField().equals(field)) {
								// 암호화된 데이터로 변경
								value = AesCrypto.encrypt(value, DECRYPTED_KEY);
								break;
							}
						}
					}

					if(i == userData.size()-1) {
						nameString.append("`").append(field).append("`)");
						valueString.append("'").append(value).append("')");
					} else {
						nameString.append("`").append(field).append("`,");
						valueString.append("'").append(value).append("',");
					}

				} catch (Exception e) {
					log.error("암호화 정보를 복호화하는데 실패했습니다.");
					return ResponseEntity.ok(res.fail(ResponseErrorCode.KO046.getCode(), ResponseErrorCode.KO046.getDesc()));
				}

			}

			boolean userInsertResult = kokonutUserService.insertUserTable(businessNumber,
					nameString.insert(0, "(").toString(), valueString.insert(0, "(").toString());
			log.info("userInsertResult : "+userInsertResult);

			if(userInsertResult && controlType.equals("휴면")) {
				log.info("휴면계정 가입일 경우 휴면테이블로 이관 시작");

				// 첫번째 괄호 문자 제거
				nameString.deleteCharAt(0);
				valueString.deleteCharAt(0);

				// 해당 개인정보가 잘 저장됬는지 조회
				Long idx = kokonutUserService.selectUserIdx(businessNumber, id);
				// 휴면테이블로 insert
				boolean dormantInsertResult = kokonutDormantService.insertDormantTable(businessNumber,
						nameString.insert(0, "(`IDX`,").toString(), valueString.insert(0, "("+idx+",").toString());

				if(dormantInsertResult) {
					log.info("휴면테이블로 이관 성공");
					boolean kokonutUserDelete = kokonutUserService.deleteUserTable(businessNumber, idx);
					if(kokonutUserDelete) {
						log.error("인서트한 유저테이블 삭제 성공");
					} else {
						log.error("인서트한 유저테이블 삭제 실패");
					}
				} else {
					log.error("휴면테이블로 이관 실패");
				}
			}

			activityHistoryService.updateActivityHistory(activityHistoryIDX,businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);

		} catch (Exception e) {
			log.error("회원등록 에러확인 필요");
			log.error("e : "+e.getMessage());
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO059.getCode(), "회원등록 "+ResponseErrorCode.KO059.getDesc()));
		}

		return ResponseEntity.ok(res.success(data));
	}

	// 유저정보 수정(회원수정)
	@Transactional
	@SuppressWarnings("unchecked")
	public ResponseEntity<Map<String, Object>> userUpdateCall(HashMap<String, Object> paramMap, String email) {
		log.info("userUpdateCall 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("paramMap : "+ paramMap);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		String controlType = String.valueOf(paramMap.get("controlType"));
		if(controlType.equals("1")) {
			controlType = "사용";
		} else if(controlType.equals("2")) {
			controlType = "휴면";
		} else {
			log.error("개인정보 상태를 선택해주세요. controlType : "+controlType);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO060.getCode(), ResponseErrorCode.KO060.getDesc()));
		}

		// 개인정보수정 코드
		ActivityCode activityCode = ActivityCode.AC_02;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(1, companyIdx, adminIdx, activityCode, businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

		try {

			// 회사의 암호화 키
			String DECRYPTED_KEY = companyService.selectCompanyEncryptKey(companyIdx);

			if(DECRYPTED_KEY == null) {
				log.error("해당 기업의 암호화 키가 존재하지 않습니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO043.getCode(), ResponseErrorCode.KO043.getDesc()));
			}

			List<HashMap<String,Object>> userData = (List<HashMap<String, Object>>) paramMap.get("userData");

			Long idx = Long.parseLong(String.valueOf(paramMap.get("IDX")));
//			log.info("idx : "+ idx);

			boolean isFieldCheck = false; // 필드 체크

			List<KokonutUserFieldDto> columns;
			List<KokonutUserFieldDto> encryptColumns;
			if(controlType.equals("사용")) {
				columns = kokonutUserService.getUserColumns(businessNumber);
				encryptColumns = kokonutUserService.selectUserEncryptColumns(businessNumber);
			} else {
				columns = kokonutDormantService.getDormantColumns(businessNumber);
				encryptColumns = kokonutDormantService.selectDormantEncryptColumns(businessNumber);
			}

			StringBuilder updateString = new StringBuilder();

			for (int i=0; i<userData.size(); i++) {
				String field = userData.get(i).get("name").toString(); // 필드 이름
				String value = userData.get(i).get("value").toString(); // 필드 값

				if("".equals(value)) {
					continue; // 입력된 값이 없을 경우 패스
				}

				// 입력한 필드가 존재하는지 검증
				for(KokonutUserFieldDto column : columns) {
					if(field.equals(column.getField())) {
						isFieldCheck = true;
						columns.remove(column);
						break;
					}
				}

				// 필드가 존재하지 않을 경우 리턴처리
				if(!isFieldCheck) {
					log.error("해당 개인정보에 필드가 존재하지 않습니다. 핃드명 : "+field);
					return ResponseEntity.ok(res.fail(ResponseErrorCode.KO045.getCode(), field+ResponseErrorCode.KO045.getDesc()));
				}

				// 필드 이름이 ID일 경우
				if(field.equals("ID")) {
					// 아이디 중복검사
					boolean isUserExistCheck = kokonutUserService.isUserExistId(businessNumber, value);
					boolean isDormantExistCheck = kokonutDormantService.isDormantExistId(businessNumber, value);
					if(isUserExistCheck || isDormantExistCheck) {
						log.error("이미 사용중인 개인정보 ID 입니다. 아이디 : "+value);
						return ResponseEntity.ok(res.fail(ResponseErrorCode.KO046.getCode(), ResponseErrorCode.KO046.getDesc()));
					} else {
						log.info("사용하실 수 있는 아이디 입니다. : "+value);
					}
				}

				try {
					// 비밀번호 암호화
					if(field.equals("PASSWORD")) {
						// 암호화된 비밀번호로 변경
						value = passwordEncoder.encode(value);
					}
					// 암호화 속성을 갖는 컬럼의 데이터 암호화
					else {
						for(KokonutUserFieldDto column : encryptColumns) {
							if(column.getField().equals(field)) {
								// 암호화된 데이터로 변경
								value = AesCrypto.encrypt(value, DECRYPTED_KEY);
								break;
							}
						}
					}

//					if((!field.equals("NAME") && !field.equals("EMAIL")) || !controlType.equals("수정")) {
//						Map<String, Object> item = new HashMap<String, Object>();
//						item.put("field", field);
//						item.put("value", value);
//						list.add(item);
//					}

					if(i == userData.size()-1) {
						updateString.append(field).append("='").append(value).append("',");
						updateString.append("MODIFY_DATE").append("='").append(LocalDateTime.now()).append("'");
					} else {
						updateString.append(field).append("='").append(value).append("',");
					}

				} catch (Exception e) {
					log.error("암호화 정보를 복호화하는데 실패했습니다.");
					return ResponseEntity.ok(res.fail(ResponseErrorCode.KO046.getCode(), ResponseErrorCode.KO046.getDesc()));
				}

			}


			activityHistoryService.updateActivityHistory(activityHistoryIDX,businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);

//			log.info("updateString : "+ updateString);

			boolean result;
			if(controlType.equals("사용")) {
				result = kokonutUserService.updateUserTable(businessNumber, idx, updateString.toString());
			} else {
				result = kokonutDormantService.updateDormantTable(businessNumber, idx, updateString.toString());
			}

			if(result) {
				log.info("개인정보 수정 성공");
			} else {
				log.error("개인정보 수정 실패");
			}

		} catch (Exception e) {
			log.error("회원수정 에러확인 필요");
			log.error("e : "+e.getMessage());
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO059.getCode(), "회원수정 "+ResponseErrorCode.KO059.getDesc()));
		}

		return ResponseEntity.ok(res.success(data));
	}

	// 유저삭제(회원삭제)
	@Transactional
	public ResponseEntity<Map<String, Object>> userDeleteCall(String TYPE, Integer IDX, String email) {
		log.info("userDeleteCall 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("TYPE : "+ TYPE);
		log.info("IDX : "+ IDX);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		// TYPE -> "USER" 이면 사용중인 테이블, "DORMANT" 이면 휴면인 테이블
		List<KokonutRemoveInfoDto> kokonutRemoveInfoDtos;
		// 해당 테이블에 데이터 조회
		if(TYPE.equals("USER")) {
			kokonutRemoveInfoDtos = kokonutUserService.selectUserDataByIdx(businessNumber, IDX);
		} else if(TYPE.equals("DORMANT")) {
			kokonutRemoveInfoDtos = kokonutDormantService.selectDormantDataByIdx(businessNumber, IDX);
		} else {
			log.error("해당 유저의 TYPE 유형이 존재하지 않습니다.");
			kokonutRemoveInfoDtos = null;
		}

		assert kokonutRemoveInfoDtos != null;
		if(kokonutRemoveInfoDtos.size() == 0) {
			log.error("존재하지 않은 유저입니다. 새로고침이후 다시 시도해주세요.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO058.getCode(), ResponseErrorCode.KO058.getDesc()));
		}

		// 회원삭제 코드
		ActivityCode activityCode = ActivityCode.AC_03;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(1, companyIdx, adminIdx, activityCode,
				businessNumber+" - "+activityCode.getDesc()+" 시도 이력 ID : "+kokonutRemoveInfoDtos.get(0).getID(), "", ip, 0);

		try {

			boolean kokonutUserDelete = kokonutUserService.deleteUserTable(businessNumber, kokonutRemoveInfoDtos.get(0).getIDX());
			if(kokonutUserDelete) {
				log.error("회원삭제 성공 -> 삭제 테이블로 이관");

				StringBuilder nameString = new StringBuilder();
				StringBuilder valueString = new StringBuilder();

				nameString.append("(`").append("IDX").append("`,");
				nameString.append("`").append("ID").append("`,");
				nameString.append("`").append("REGDATE").append("`,");
				nameString.append("`").append("DELETE_DATE").append("`)");
				valueString.append("('").append(kokonutRemoveInfoDtos.get(0).getIDX()).append("',");
				valueString.append("'").append(kokonutRemoveInfoDtos.get(0).getID()).append("',");
				valueString.append("'").append(kokonutRemoveInfoDtos.get(0).getREGDATE()).append("',");
				valueString.append("'").append(LocalDateTime.now()).append("')");

				log.info("nameString : "+ nameString);
				log.info("valueString : "+ valueString);

				boolean result = kokonutRemoveService.insertRemoveTable(businessNumber, nameString.toString(), valueString.toString());
				if(result) {
					log.info("유저 삭제이관 성공");
				} else {
					log.error("유저 삭제이관 실패");
				}
			}

		} catch (Exception e ){
			log.error("회원삭제 에러확인 필요");
			log.error("e : "+e.getMessage());
			activityHistoryService.deleteActivityHistoryByIdx(activityHistoryIDX);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO059.getCode(), "회원삭제 "+ResponseErrorCode.KO059.getDesc()));
		}

		activityHistoryService.updateActivityHistory(activityHistoryIDX,
				businessNumber+" - "+activityCode.getDesc()+" 완료 이력 ID : "+kokonutRemoveInfoDtos.get(0).getID(), "", 1);

		return ResponseEntity.ok(res.success(data));
	}

	// 개인정보 등록 - 엑셀파일 양식 다운로드
	public void downloadExcelForm(HttpServletRequest request, HttpServletResponse response, String email) {
		log.info("downloadExcelForm 호출");

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return;
		}
		else {
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		try {
			List<KokonutUserFieldDto> columns = kokonutUserService.getUserColumns(businessNumber);

			List<String> headerList = new ArrayList<>();
			List<List<String>> dataArrayList = new ArrayList<>();

			for (KokonutUserFieldDto kokonutUserFieldDto : columns) {
				String comment = kokonutUserFieldDto.getComment();

				// Field 옵션 명 및 암호화 정형화
				String fieldOptionName = comment;

				if(comment.contains("(")) {
					String[] fieldOptionNameList = comment.split("\\(");
					fieldOptionName = fieldOptionNameList[0];
				}

				// 엑셀 파일 헤더에 제외 할 필드
				if("인덱스".equals(fieldOptionName)) continue;
				if("개인정보 동의".equals(fieldOptionName)) continue;
				if("이용내역보낸 날짜".equals(fieldOptionName)) continue;
				if("최종 로그인 일시".equals(fieldOptionName)) continue;
				if("등록 일시".equals(fieldOptionName)) continue;
				if("수정 일시".equals(fieldOptionName)) continue;

				headerList.add(fieldOptionName);
			}

			// 보조 설명문 추가
			List<String> headerInfoList = new ArrayList<>();
			for (String header : headerList) {
				if ("비밀번호".equals(header)) {
					headerInfoList.add("암호화된 데이터가 저장됨");
				} else if ("제3자제공 동의".equals(header)) {
					headerInfoList.add("N:동의하지않음 / Y:동의");
				} else if ("회원가입 날짜".equals(header)) {
					headerInfoList.add("YYYYMMDD 형식의 값");
				} else {
					headerInfoList.add("");
				}
			}
			dataArrayList.add(headerInfoList);

			excelService.download(request, response, "회원 엑셀 양식.xlsx", headerList, dataArrayList);
			log.error("엑셀 양식파일 다운로드 성공");

		} catch(Exception e) {
			log.error("엑셀 파일 다운로드 에러");
			log.error("e : "+e.getMessage());
		}

	}

	// 개인정보 일괄등록 - 엑셀파일 검사 -> 미리보여주기 기능
	public ResponseEntity<Map<String, Object>> readUploadExcelFile(MultipartHttpServletRequest request, String type, String email) {
		log.info("readUploadExcelFile 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("request : "+ request);
		log.info("type : "+type);
		log.info("email : "+email);

		try {
			MultipartFile file = null;
			Iterator<String> iterator = request.getFileNames();
			if(iterator.hasNext()) {
				file = request.getFile(iterator.next());
			}

			if(file == null) {
				log.error("검사 할 엑셀파일이 존재 하지 않습니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO061.getCode(), ResponseErrorCode.KO061.getDesc()));
			}

			List<List<String>> rows = excelService.read(file.getInputStream());
			if(rows == null) {
				log.error("읽을 수 없는 엑셀파일 입니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO062.getCode(), ResponseErrorCode.KO062.getDesc()));
			}

			List<String> headerList = rows.get(0);
			List<List<String>> rowList = new ArrayList<List<String>>();
			for(int i = 1; i < rows.size(); i++) {
				rowList.add(rows.get(i));
			}

			// 차단 컬럼 검사
			if(headerList.contains("IDX")) {
				log.error("IDX 컬럼은 저장할 수 없습니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO063.getCode(), ResponseErrorCode.KO063.getDesc()));
			}

			// 해당 이메일을 통해 회사 IDX 조회
			AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

			int adminIdx;
			int companyIdx;
			String businessNumber;

			if(adminCompanyInfoDto == null) {
				log.error("이메일 정보가 존재하지 않습니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
			}
			else {
				adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
				companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
				businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
			}

			List<KokonutUserFieldDto> columns;
			if(type.equals("1")){ // "1"일 경우 User
				columns = kokonutUserService.getUserColumns(businessNumber);
			} else { // "2"일 경우 Dormant
				columns = kokonutDormantService.getDormantColumns(businessNumber);
			}

			List<List<Map<String, Object>>> dataList = new ArrayList<>();

			// 엑셀파일 내 아이디 중복값 체크
//			String idDuplicationCheck = "";
//			for(List<String> row : rowList) {
//				// 헤더 보조 설명 스킵용
//				if (row.toString().contains("YYYYMMDD 형식의 값")) {
//					continue;
//				}
//				//한 row에 모든 값이 빈 값이면 스킵
//				String tempRow = row.toString().replaceAll(" ", "");
//				tempRow = tempRow.toString().replaceAll(",", "");
//				if (tempRow.length() <= 2) {
//					continue;
//				}
//				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//			}

		} catch (Exception e) {
			log.error("e : "+e.getMessage());
		}

		return ResponseEntity.ok(res.success(data));
	}

	// 개인정보 테이블 + 휴면 테이블 필드 추가
	@Transactional
	public ResponseEntity<Map<String, Object>> columSave(KokonutColumSaveDto kokonutColumSaveDto, String email) {
		log.info("columSave 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("kokonutColumSaveDto : "+ kokonutColumSaveDto);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		int check = kokonutUserService.selectExistUserTable(businessNumber);
		if(check == 0) {
			log.error("유저 테이블이 존재하지 않습니다. businessNumber : "+businessNumber);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "유저 테이블이 "+ResponseErrorCode.KO004.getDesc()));
		}

		String fieldName = kokonutColumSaveDto.getFieldName(); // 필드명
		String fieldOptionName = kokonutColumSaveDto.getFieldOptionName(); // Comment 내용
 		String dataType = kokonutColumSaveDto.getDataType(); // 데이터 타입 : "2" -> BIGINT, "3" -> DOUBLE, "4" -> VARCHAR,  "5" -> LONGTEXT,  "6" -> BOOLEAN,  "7" -> TIMESTAMP
		Integer dataLength = kokonutColumSaveDto.getDataLength(); // 데이터 길이
		String isNullYn = kokonutColumSaveDto.getIsNullYn(); // Null값 허용여부 true/false
		String defaultValue = kokonutColumSaveDto.getDefaultValue(); // 기본값
		String isEncryption = kokonutColumSaveDto.getIsEncryption(); // 암호화여부 - "0" 필요, "1" 불필요

		// DATA TYPE 정형화
		String type = "";
		if(dataType.equals("2")) {
			type = "BIGINT";
		} else if(dataType.equals("3")) {
			type = "DOUBLE";
		} else if(dataType.equals("4")) {
			type = "VARCHAR";
		} else if(dataType.equals("5")) {
			type = "LONGTEXT";
		} else if(dataType.equals("6")) {
			type = "BOOLEAN";
		} else if(dataType.equals("7")) {
			type = "TIMESTAMP";
		}
		log.info("저장할 필드 데이터타입 : "+type);

		// 길이 정형화
		int length = 0;
		if(dataLength != null) {
			length = dataLength;
		}

		// Null check 정형화
		boolean isNull = Boolean.parseBoolean(isNullYn);

		// 대상 테이블 정보를 조회
		List<KokonutUserFieldDto> targetTable = kokonutUserService.getUserColumns(businessNumber);

		// Field명과 Comment내용 중복 컬럼 체크
		for (KokonutUserFieldDto column : targetTable) {
			String Field = column.getField();
			String Comment = column.getComment();

			if(Comment.contains("(")) {
				String[] CommentList = Comment.split("\\(");
				Comment = CommentList[0];
			}

			if(fieldName.equals(Field.toUpperCase())) {
				log.error("이미 존재하는 컬럼명 입니다.");
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO064.getCode(), ResponseErrorCode.KO064.getDesc()));
			}

			if(Comment.equals(fieldOptionName)) {
				log.error("이미 존재하는 개인정보 항목입니다. fieldOptionName : "+fieldOptionName);
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO065.getCode(), ResponseErrorCode.KO065.getDesc()));
			}
		}

		// Comment 정형화
		String comment;
		if(isEncryption.equals("0")) {
			comment = fieldOptionName + "(암호화,수정가능)";
		} else {
			comment = fieldOptionName + "(수정가능)";
		}

		// 회원컬럼저장 코드
		ActivityCode activityCode = ActivityCode.AC_19;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(3, companyIdx, adminIdx, activityCode,
				businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

		// 사용테이블에 컬럼 추가
		kokonutUserService.alterAddColumnTableQuery(businessNumber, fieldName, type, length, isNull, defaultValue, comment);
		// 휴면테이블에 컬럼 추가
		kokonutDormantService.alterAddColumnTableQuery(businessNumber, fieldName, type, length, isNull, defaultValue, comment);

		activityHistoryService.updateActivityHistory(activityHistoryIDX,
				businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);

		return ResponseEntity.ok(res.success(data));
	}

	// 개인정보 테이블 + 휴면 테이블 필드 수정
	@Transactional
	public ResponseEntity<Map<String, Object>> columUpdate(KokonutColumUpdateDto kokonutColumUpdateDto, String email) throws Exception {
		log.info("columUpdate 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("kokonutColumUpdateDto : "+ kokonutColumUpdateDto);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		int check = kokonutUserService.selectExistUserTable(businessNumber);
		if(check == 0) {
			log.error("유저 테이블이 존재하지 않습니다. businessNumber : "+businessNumber);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "유저 테이블이 "+ResponseErrorCode.KO004.getDesc()));
		}

		String fieldName;
		String beforField = kokonutColumUpdateDto.getBeforField(); // 현재 필드명
		String afterField = kokonutColumUpdateDto.getAfterField(); // 수정할 필드명
		if (afterField == null) {
			fieldName = beforField;
		} else {
			fieldName = afterField;
		}

		String fieldOptionName = kokonutColumUpdateDto.getFieldOptionName(); // Comment 내용
		// 데이터 타입 : "2" -> BIGINT, "3" -> DOUBLE, "4" -> VARCHAR,  "5" -> LONGTEXT,  "6" -> BOOLEAN,  "7" -> TIMESTAMP
		String dataType = kokonutColumUpdateDto.getDataType();
		Integer dataLength = kokonutColumUpdateDto.getDataLength(); // 데이터 길이
		String isNullYn = kokonutColumUpdateDto.getIsNullYn(); // Null값 허용여부 true/false
		String defaultValue = kokonutColumUpdateDto.getDefaultValue(); // 기본값
		String isEncryption = kokonutColumUpdateDto.getIsEncryption(); // 암호화여부 - "0" 필요, "1" 불필요

		// 데이터타입 정형화
		String type = "";
		if(dataType.equals("2")) {
			type = "BIGINT";
		} else if(dataType.equals("3")) {
			type = "DOUBLE";
		} else if(dataType.equals("4")) {
			type = "VARCHAR";
		} else if(dataType.equals("5")) {
			type = "LONGTEXT";
		} else if(dataType.equals("6")) {
			type = "BOOLEAN";
		} else if(dataType.equals("7")) {
			type = "TIMESTAMP";
		}
		log.info("수정할 필드 데이터타입 : "+type);

		// 길이 정형화
		int length = 0;
		if(dataLength != null) {
			length = dataLength;
		}

		// Null check 정형화
		boolean isNull = Boolean.parseBoolean(isNullYn);

		// 대상 테이블 정보를 조회
		List<KokonutUserFieldDto> targetTable = kokonutUserService.getUserColumns(businessNumber);

		// Field명과 Comment내용 중복 컬럼 체크
		for (KokonutUserFieldDto column : targetTable) {
			String Field = column.getField();
			String Comment = column.getComment();

			if(Comment.contains("(")) {
				String[] CommentList = Comment.split("\\(");
				Comment = CommentList[0];
			}

			// 필드를 수정하지 않을 경우
			if(afterField != null) {
				if(fieldName.equals(Field)) {
					log.error("이미 존재하는 컬럼명 입니다.");
					return ResponseEntity.ok(res.fail(ResponseErrorCode.KO064.getCode(), ResponseErrorCode.KO064.getDesc()));
				}
			}

			if(!beforField.equals(Field) && Comment.equals(fieldOptionName)) {
				log.error("이미 존재하는 개인정보 항목입니다. fieldOptionName : "+fieldOptionName);
				return ResponseEntity.ok(res.fail(ResponseErrorCode.KO065.getCode(), ResponseErrorCode.KO065.getDesc()));
			}
		}

		// Comment 정형화
		String comment;

		// 사업자 DECRYPTED_KEY
		String DECRYPTED_KEY;

		// 현재 바꾼 필드의 커멘트 가져오기
		String changeColumnComment = kokonutUserService.selectUserColumnComment(businessNumber, beforField);
//		log.info("changeColumnComment : "+changeColumnComment);

		// 회원컬럼수정 코드
		ActivityCode activityCode = ActivityCode.AC_20;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(3, companyIdx, adminIdx, activityCode,
				businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

		// 암호화, 복호화 전환로직
		if(changeColumnComment != null) {
			if(changeColumnComment.contains("(암호화,수정가능)")) {
				if(isEncryption.equals("1")) {
					comment = fieldOptionName + "(수정가능)";

					log.info("이전에 암호화필드였지만 암호화 불필요로 수정하여 모든데이터 복호화시작");
					DECRYPTED_KEY = companyService.selectCompanyEncryptKey(companyIdx);

					// 개인정보(유저)테이블 필드의 데이터 복호화
					List<KokonutUserFieldInfoDto> fieldList = kokonutUserService.selectUserFieldList(businessNumber, beforField);
					for (KokonutUserFieldInfoDto fieldMap : fieldList) {
						Long userIdx = fieldMap.getIDX();
						String fieldValue = String.valueOf(fieldMap.getVALUE());

						if (fieldValue.isEmpty() || fieldValue.equals("null")) {
							continue;
						}

						// 암호화 -> 복호화 시작
						String decryptedValue = AesCrypto.decrypt(new String(fieldValue.getBytes()), DECRYPTED_KEY);
						String queryString = beforField + "='"+decryptedValue+"'";
//					log.info("queryString : "+queryString);

						kokonutUserService.updateUserTable(businessNumber, userIdx, queryString);
					}

					// 개인정보(휴면)테이블 필드의 데이터 복호화
					List<KokonutDormantFieldInfoDto> fieldListDormant = kokonutDormantService.selectDormantFieldList(businessNumber, beforField);
					for (KokonutDormantFieldInfoDto fieldMap : fieldListDormant) {
						Long userIdx = fieldMap.getIDX();
						String fieldValue = String.valueOf(fieldMap.getVALUE());

						if (fieldValue.isEmpty() || fieldValue.equals("null")) {
							continue;
						}

						// 암호화 -> 복호화 시작
						String decryptedValue = AesCrypto.decrypt(new String(fieldValue.getBytes()), DECRYPTED_KEY);
						String queryString = beforField + "='"+decryptedValue+"'";
//					log.info("queryString : "+queryString);

						kokonutUserService.updateUserTable(businessNumber, userIdx, queryString);
					}

				}
				else {
					comment = fieldOptionName + "(암호화,수정가능)";
				}
			}
			else {
				if(isEncryption.equals("0")) {
					comment = fieldOptionName + "(암호화,수정가능)";

					log.info("이전에 암호화필드가 아니였지만 암호화필요로 수정하여 이전 데이터를 암호화시작");
					DECRYPTED_KEY = companyService.selectCompanyEncryptKey(companyIdx);

					// 개인정보(유저)테이블 필드의 데이터 암호화
					List<KokonutUserFieldInfoDto> fieldListUser = kokonutUserService.selectUserFieldList(businessNumber, beforField);
					for (KokonutUserFieldInfoDto fieldMap : fieldListUser) {
						Long userIdx = fieldMap.getIDX();
						String fieldValue = String.valueOf(fieldMap.getVALUE());

						if (fieldValue.isEmpty() || fieldValue.equals("null")){
							continue;
						}

						// 평문 -> 암호화 시작
						String encryptedValue = AesCrypto.encrypt(fieldValue, DECRYPTED_KEY);

						String queryString = beforField + "='"+encryptedValue+"'";
//					log.info("queryString : "+queryString);

						kokonutUserService.updateUserTable(businessNumber, userIdx, queryString);
					}

					// 휴면테이블 필드의 데이터 암호화
					List<KokonutDormantFieldInfoDto> fieldListDormant = kokonutDormantService.selectDormantFieldList(businessNumber, beforField);
					for (KokonutDormantFieldInfoDto fieldMap : fieldListDormant) {
						Long userIdx = fieldMap.getIDX();
						String fieldValue = String.valueOf(fieldMap.getVALUE());

						if (fieldValue.isEmpty() || fieldValue.equals("null")){
							continue;
						}

						// 평문 -> 암호화 시작
						String encryptedValue = AesCrypto.encrypt(fieldValue, DECRYPTED_KEY);

						String queryString = beforField + "='"+encryptedValue+"'";
//					log.info("queryString : "+queryString);

						kokonutDormantService.updateDormantTable(businessNumber, userIdx, queryString);
					}
				} else {
					comment = fieldOptionName + "(수정가능)";
				}
			}

			if (afterField == null) {
				// 사용테이블에 컬럼 수정
				kokonutUserService.alterModifyColumnCommentQuery(businessNumber, fieldName, type, length, isNull, defaultValue, comment);
				// 휴면테이블에 컬럼 수정
				kokonutDormantService.alterModifyColumnCommentQuery(businessNumber, fieldName, type, length, isNull, defaultValue, comment);
			} else {
				// 사용테이블에 컬럼 수정
				kokonutUserService.alterChangeColumnTableQuery(businessNumber, beforField, fieldName, type, length, isNull, defaultValue, comment);
				// 휴면테이블에 컬럼 수정
				kokonutDormantService.alterChangeColumnTableQuery(businessNumber, beforField, fieldName, type, length, isNull, defaultValue, comment);
			}

			activityHistoryService.updateActivityHistory(activityHistoryIDX,
					businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);

		}
		else {
			log.error("수정할 필드가 테이블에 존재하지 않습니다.");
			activityHistoryService.updateActivityHistory(activityHistoryIDX,
					businessNumber+" - "+activityCode.getDesc()+" 실패 이력", "수정할 필드가 존재하지 않습니다.", 1);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO067.getCode(), "수정할 필드가 "+ResponseErrorCode.KO067.getDesc()));
		}

		return ResponseEntity.ok(res.success(data));
	}

	// 개인정보 테이블 + 휴면 테이블 필드 삭제 - 기존코코넛 메서드 : 없음
	@Transactional
	public ResponseEntity<Map<String, Object>> columDelete(String fieldName, String email) {
		log.info("columDelete 호출");

		AjaxResponse res = new AjaxResponse();
		HashMap<String, Object> data = new HashMap<>();

		log.info("fieldName : "+ fieldName);
		log.info("email : "+email);

		// 해당 이메일을 통해 회사 IDX 조회
		AdminCompanyInfoDto adminCompanyInfoDto = adminRepository.findByCompanyInfo(email);

		int adminIdx;
		int companyIdx;
		String businessNumber;

		if(adminCompanyInfoDto == null) {
			log.error("이메일 정보가 존재하지 않습니다.");
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "해당 이메일의 정보가 "+ResponseErrorCode.KO004.getDesc()));
		}
		else {
			adminIdx = adminCompanyInfoDto.getAdminIdx(); // modifierIdx
			companyIdx = adminCompanyInfoDto.getCompanyIdx(); // companyIdx
			businessNumber = adminCompanyInfoDto.getBusinessNumber(); // tableName
		}

		int userCheckTable = kokonutUserService.selectExistUserTable(businessNumber);
		if(userCheckTable == 0) {
			log.error("유저 테이블이 존재하지 않습니다. businessNumber : "+businessNumber);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "유저 테이블이 "+ResponseErrorCode.KO004.getDesc()));
		}
		int dormantCheckTable = kokonutDormantService.selectExistDormantTable(businessNumber);
		if(dormantCheckTable == 0) {
			log.error("휴면 테이블이 존재하지 않습니다. businessNumber : "+businessNumber);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "휴면 테이블이 "+ResponseErrorCode.KO004.getDesc()));
		}

		// 개인정보테이블에 삭제할 필드가 존재하는지 여부 체크
		List<KokonutUserFieldCheckDto> userTableCheck = kokonutUserService.selectUserTableNameAndFieldName(businessNumber, fieldName);
		if(userTableCheck.size() == 0) {
			log.error("삭제할 필드가 개인정보 테이블에 존재하지 않습니다. businessNumber : "+businessNumber+", fieldName : "+fieldName);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "삭제할 필드가 개인정보 "+ResponseErrorCode.KO004.getDesc()));
		}

		// 휴면테이블에 삭제할 필드가 존재하는지 여부 체크
		List<KokonutDormantFieldCheckDto> dormantTableCheck = kokonutDormantService.selectDormantTableNameAndFieldName(businessNumber, fieldName);
		if(dormantTableCheck.size() == 0) {
			log.error("삭제할 필드가 휴면 테이블에 존재하지 않습니다. businessNumber : "+businessNumber+", fieldName : "+fieldName);
			return ResponseEntity.ok(res.fail(ResponseErrorCode.KO004.getCode(), "삭제할 필드가 휴면 "+ResponseErrorCode.KO004.getDesc()));
		}

		// 회원컬럼수정 코드
		ActivityCode activityCode = ActivityCode.AC_21;
		// 활동이력 저장 -> 비정상 모드
		String ip = CommonUtil.clientIp();
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(3, companyIdx, adminIdx, activityCode, businessNumber+" - "+activityCode.getDesc()+" 시도 이력", "", ip, 0);

		if(businessNumber.equals(userTableCheck.get(0).getTABLE_NAME()) && fieldName.equals(userTableCheck.get(0).getCOLUMN_NAME()) &&
				businessNumber.equals(dormantTableCheck.get(0).getTABLE_NAME()) && fieldName.equals(dormantTableCheck.get(0).getCOLUMN_NAME())){
			// 위 조건이 충족할 시 두테이블필드 삭제(Drop) 처리
			kokonutUserService.alterDropColumnUserTableQuery(businessNumber, fieldName);
			kokonutDormantService.alterDropColumnDormantTableQuery(businessNumber, fieldName);

			activityHistoryService.updateActivityHistory(activityHistoryIDX,
					businessNumber+" - "+activityCode.getDesc()+" 완료 이력", "", 1);
		} else {
			activityHistoryService.updateActivityHistory(activityHistoryIDX,
					businessNumber+" - "+activityCode.getDesc()+" 실패 이력", "필드 삭제 조건에 부합하지 않습니다.", 1);
		}

		return ResponseEntity.ok(res.success(data));
	}

}
