package com.app.kokonutuser;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-28
 * Time :
 * Remark : DynamicUserService -> DynamicUserRestController 에서 호출하는 서비스
 */
@Slf4j
@Service
public class DynamicUserService {

	private static final String USER_DB_NAME = "kokonut_user";
	private static final String REMOVE_DB_NAME = "kokonut_remove";
	private static final String DORMANT_DB_NAME = "kokonut_dormant";

//	@Autowired
//	DynamicUserDao dynamicUserDao;
//
//    @Autowired
//    DynamicDormantUserDao dynamicDormantUserDao;

	private final PasswordEncoder passwordEncoder;

	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;
	private final KokonutUserService kokonutUserService;

//	@Autowired
//	ExcelService excelService;

	@Autowired
	public DynamicUserService(PasswordEncoder passwordEncoder, AdminRepository adminRepository, CompanyRepository companyRepository, KokonutUserService kokonutUserService) {
		this.passwordEncoder = passwordEncoder;
		this.adminRepository = adminRepository;
		this.companyRepository = companyRepository;
		this.kokonutUserService = kokonutUserService;

	}

	/**
	 * 시스템 관리자가 지정한 기본 테이블 정보 조회
	 */
//	public List<HashMap<String, Object>> SelectCommonUserTable() {
//		return dynamicUserDao.SelectCommonUserTable();
//	}


	//====================================================================//
	// TABLE INSERT, UPDATE, DELETE
	//====================================================================//
	/**
	 * 동적테이블 생성
	 * @param email : 이메일
	 * @return
	 */
//	@Transactional(value="secondTransactionManager", rollbackFor= {Exception.class})
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

//		@@@@@@@@@@ - KokonutUserService 테스트 - @@@@@@@@@@@@

		// 회원DB 생성 (유저 테이블) - 테스트완료 woody
//		boolean result = kokonutUserService.createTableKokonutUser(businessNumber);
//		log.info("result : "+result);

		//회원DB 중복체크 - 테스트완료 woody
//		int check = kokonutUserService.selectExistTable(businessNumber);
//		log.info("check : "+check);

		// 회원DB 삭제 (유저 테이블) - 테스트완료 woody
//		String result = kokonutUserService.deleteTableKokonutUser(businessNumber);
//		log.info("result : "+result);

		// 회원DB 필드값 추가 - 테스트완료 woody
//		boolean result = kokonutUserService.alterAddColumnTableQuery(businessNumber, "test", "VARCHAR", 50, true, "");
//		log.info("result : "+result);

		// 회원DB 필드정보 수정 - 테스트완료 woody
//		boolean result = kokonutUserService.alterChangeColumnTableQuery(businessNumber, "test", "testchange", "VARCHAR", 50, true, "수정테스트", "기본값");
//		log.info("result : "+result);

		// 회원DB 필드 코멘트 수정 - 테스트완료 woody
//		boolean result = kokonutUserService.alterModifyColumnCommentQuery(businessNumber, "testchange", "VARCHAR", 50, true, "코멘트수정완료", "기본값");
//		log.info("result : "+result);

		// 회원DB 필드 삭제 - 테스트완료 woody
//		boolean result = kokonutUserService.alterDropColumnTableQuery(businessNumber, "testchangee");
//		log.info("result : "+result);

		// 회원DB 회원리스트 조회 - 테스트완료 woody
//		List<Map<String, Object>> result = kokonutUserService.selectUserList(businessNumber);
//		log.info("result : "+result);

		// 회원DB 회원 수 조회 - 테스트완료 woody
//		int result = kokonutUserService.selectUserListCount(businessNumber);
//		log.info("result : "+result);

		// 회원DB 회원등록 조회 - 테스트중 woody -> 인서트 쿼리문은 DynamicUserService 부분에서 직정 가공하여 호출
//		String nameString = "(`NAME`, `GENDER`, `BIRTH`, `PHONE_NUMBER`, `REGDATE`, `ID`, `PASSWORD`, `PERSONAL_INFO_AGREE`, `STATE`, `EMAIL`)";
//		String valueString = "('테스트임다','0','19910101','01012123344','2022-11-09 15:00:00','test10', '"+passwordEncoder.encode("123456")+"' ,'Y',1,'test10@kkn.me')";
//		boolean result = kokonutUserService.insertUserTable(businessNumber, nameString, valueString);
//		log.info("result : "+result);

		// 회원DB 회원등록 조회 - 테스트완료 woody -> 인서트 쿼리문은 DynamicUserService 부분에서 직정 가공하여 호출
//		String nameString = "(`NAME`, `GENDER`, `BIRTH`, `PHONE_NUMBER`, `REGDATE`, `ID`, `PASSWORD`, `PERSONAL_INFO_AGREE`, `STATE`, `EMAIL`)";
//		String valueString = "('테스트임다','0','19910101','01012123344','2022-11-09 15:00:00','test10', '"+passwordEncoder.encode("123456")+"' ,'Y',1,'test10@kkn.me')";
//		boolean result = kokonutUserService.insertUserTable(businessNumber, nameString, valueString);
//		log.info("result : "+result);

		// 회원DB 수정 - 테스트완료 woody -> 업데이트 쿼리문은 DynamicUserService 부분에서 직정 가공하여 호출
//		String queryString = "NAME = '이름변경', ID='idchange'";
//		boolean result = kokonutUserService.updateUserTable(businessNumber, 8, queryString);
//		log.info("result : "+result);

		// 회원DB 삭제 - 테스트완료 woody
//		boolean result = kokonutUserService.deleteUserTable(businessNumber, 8);
//		log.info("result : "+result);




//		@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

//			if(!dynamicUserService.CreateDynamicTable(businessNumber)) {
//				logger.info("###[회원DB 항목 관리] Create Table Field. Database : kokonut_user, Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "유저 테이블 생성에 실패했습니다.");
//				break;
//			}

		// 회원DB 생성 (삭제, 탈퇴용 테이블)
//			if(!dynamicRemoveService.CreateDynamicTable(businessNumber)) {
//				// RollBack
//				dynamicUserService.DeleteDynamicTable(businessNumber);
//
//				logger.info("###[회원DB 항목 관리] Create Table Field. Database : kokonut_remove, Table : " + businessNumber);
//				returnMap.put("isSuccess", "false");
//				returnMap.put("errorMsg", "탈퇴용 테이블 생성에 실패했습니다.");
//				break;
//			}
		// 회원DB 생성 (휴면용 테이블)
//			if(!dynamicDormantService.CreateDynamicTable(businessNumber)) {
//				// RollBack
//				dynamicRemoveService.DeleteDynamicTable(businessNumber);
//				dynamicUserService.DeleteDynamicTable(businessNumber);
//
//				logger.info("###[회원DB 항목 관리] Create Table Field. Database : kokonut_dormant, Table : " + businessNumber);
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

}
