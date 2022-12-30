package com.app.kokonutuser;

import com.app.kokonut.admin.AdminRepository;
import com.app.kokonut.admin.dtos.AdminCompanyInfoDto;
import com.app.kokonut.company.CompanyRepository;
import com.app.kokonut.woody.common.AjaxResponse;
import com.app.kokonut.woody.common.ResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

	private final AdminRepository adminRepository;
	private final CompanyRepository companyRepository;
	private final KokonutUserService kokonutUserService;

//	@Autowired
//	ExcelService excelService;

	@Autowired
	public DynamicUserService(AdminRepository adminRepository, CompanyRepository companyRepository, KokonutUserService kokonutUserService) {
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

		// 회원DB 생성 (유저 테이블)
//		boolean result = kokonutUserService.createTableKokonutUser(businessNumber);
//		log.info("result : "+result);

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
