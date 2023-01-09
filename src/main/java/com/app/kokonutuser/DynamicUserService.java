package com.app.kokonutuser;

import com.app.kokonut.activityHistory.ActivityHistoryService;
import com.app.kokonut.activityHistory.dto.ActivityCode;
import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.company.CompanyService;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import com.app.kokonut.woody.common.component.AesCrypto;
import com.app.kokonut.woody.common.component.CommonUtil;
import com.app.kokonutdormant.KokonutDormantService;
import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutremove.KokonutRemoveService;
import com.app.kokonutuser.dtos.KokonutUserFieldDto;
import com.app.kokonutuser.dtos.KokonutUserListDto;
import com.app.kokonutuser.dtos.KokonutUserSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//	@Autowired
//	DynamicUserDao dynamicUserDao;
//
//    @Autowired
//    DynamicDormantUserDao dynamicDormantUserDao;

	private final PasswordEncoder passwordEncoder;

	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;

	private final KokonutUserService kokonutUserService;
	private final CompanyService companyService;
	private final ActivityHistoryService activityHistoryService;

	// Service 테스트중
	private final KokonutDormantService kokonutDormantService;

	private final KokonutRemoveService kokonutRemoveService;

//	@Autowired
//	ExcelService excelService;

	@Autowired
	public DynamicUserService(PasswordEncoder passwordEncoder, AdminRepository adminRepository, CompanyRepository companyRepository, KokonutUserService kokonutUserService, KokonutDormantService kokonutDormantService, CompanyService companyService, ActivityHistoryService activityHistoryService, KokonutRemoveService kokonutRemoveService) {
		this.passwordEncoder = passwordEncoder;
		this.adminRepository = adminRepository;
		this.companyRepository = companyRepository;
		this.kokonutUserService = kokonutUserService;
		this.kokonutDormantService = kokonutDormantService;
		this.companyService = companyService;
		this.activityHistoryService = activityHistoryService;

		this.kokonutRemoveService = kokonutRemoveService;
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
//		int check = kokonutUserService.selectExistTable(businessNumber);
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

		// kokonut_user DB 회원등록 조회 - 테스트중 woody -> 인서트 쿼리문은 DynamicUserService 부분에서 직접 가공하여 호출
//		String nameString = "(`NAME`, `GENDER`, `BIRTH`, `PHONE_NUMBER`, `REGDATE`, `ID`, `PASSWORD`, `PERSONAL_INFO_AGREE`, `STATE`, `EMAIL`)";
//		String valueString = "('테스트임다','0','19910101','01012123344','2022-11-09 15:00:00','test10', '"+passwordEncoder.encode("123456")+"' ,'Y',1,'test10@kkn.me')";
//		boolean result = kokonutUserService.insertUserTable(businessNumber, nameString, valueString);
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
		Integer activityHistoryIDX = activityHistoryService.insertActivityHistory(1, companyIdx, adminIdx, activityCode, businessNumber+activityCode.getDesc()+" 시도 이력", "", ip, 0);

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

			List<KokonutUserFieldDto> columns = kokonutUserService.getColumns(businessNumber); // columns
			List<KokonutUserFieldDto> encryptColumns = kokonutUserService.selectEncryptColumns(businessNumber); // encryptColumns

			StringBuilder nameString = new StringBuilder();
			StringBuilder valueString = new StringBuilder();

			nameString.append("(");
			valueString.append("(");

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
//					if(controlType.equals("수정")) {
//						String beforeId = paramMap.get("beforeId").toString();
//						// 기존 ID 값인 경우 스킵
//						if(value.equals(beforeId)) continue;
//					}

					// 아이디 중복검사
					boolean isExistCheck = kokonutUserService.isExistId(businessNumber, value);

					if(isExistCheck) {
						log.error("이미 존재한 ID 입니다. 아이디 : "+value);
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
			activityHistoryService.updateActivityHistory(activityHistoryIDX,businessNumber+activityCode.getDesc()+" 완료 이력", "", 1);

			log.info("nameString : "+ nameString);
			log.info("valueString : "+ valueString);
			boolean result = kokonutUserService.insertUserTable(businessNumber, nameString.toString(), valueString.toString());
			log.info("result : "+result);

		} catch (Exception e) {
			log.error("회원등록 에러확인 필요");
			log.error("e : "+e.getMessage());
		}


//			String nameStringtest = "(`ID`, `PASSWORD`, `PERSONAL_INFO_AGREE`, `OFFER_INFO_AGREE`, `REGISTER_DATE`, `REGDATE`)";
//			String valueStringtest = "('savetest01', '"+passwordEncoder.encode("123456")+"' ,'Y','Y', '2021-10-13 15:00:00', '2023-01-09 17:30:12' )";
//			boolean result = kokonutUserService.insertUserTable(businessNumber, nameStringtest, valueStringtest);
//			log.info("result : "+result);

//			if(controlType.equals("등록")) {
//				resultMap = dynamicUserService.InsertUserTable(tableName, list);
//				activityHistoryMap.put("idx", resultMap.get("idx").toString());
//
//				if(resultMap.get("isSuccess").toString().equals("false")) {
//					logger.error("###[회원 관리 " + controlType + "] Insert Fail Table : kokonut.user_" + tableName);
//					returnMap.put("isSuccess", "false");
//					returnMap.put("errorMsg", "회원 " + controlType + "에 실패했습니다.");
//					break;
//				}
//
//			}
//			else if(controlType.equals("수정")) {
//				int idx = Integer.parseInt(paramMap.get("idx").toString());
//				activityHistoryMap.put("idx", idx);
//				activityNumber = 18; //회원 관리 변경
//
//				// 상태값 업데이트
//				if(!dynamicUserService.UpdateUserTable(tableName, idx, list)) {
//					logger.error("###[회원 관리 " + controlType + "] Update Fail Table : kokonut.user_" + tableName);
//					returnMap.put("isSuccess", "false");
//					returnMap.put("errorMsg", "회원 " + controlType + "에 실패했습니다.");
//					break;
//				}
//
//				// 상태값이 변경 되었을시 처리
//				String isChangedState = paramMap.get("isChangedState").toString();
//				if(isChangedState.equals("true")) {
//					// 상태값이 휴면에서 사용으로 변경시
//					if(state.equals("1")) {
//						if(!dynamicDormantService.Restore(tableName, idx)) {
//							throw new Exception("restore failed");
//						}
//						// 상태값이 사용에서 휴면으로 변경시
//					} else if(state.equals("2")) {
//						if(!dynamicUserService.Restore(tableName, idx)) {
//							throw new Exception("restore failed");
//						}
//					}
//				}
//
//			}

		return ResponseEntity.ok(res.success(data));
	}







}
