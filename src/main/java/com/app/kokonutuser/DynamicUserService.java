package com.app.kokonutuser;

import com.app.kokonut.company.CompanyService;
import com.app.kokonutuser.common.CommonRepositoryCustom;
import com.app.kokonutuser.common.dto.CommonFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

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

	private final CompanyService companyService;

	private final CommonRepositoryCustom commonRepositoryCustom;
//	private final DynamicUserRepositoryCustom dynamicUserRepositoryCustom;

//	@Autowired
//	ExcelService excelService;

	@Autowired
	public DynamicUserService(CompanyService companyService, CommonRepositoryCustom commonRepositoryCustom){
		this.companyService = companyService;
		this.commonRepositoryCustom = commonRepositoryCustom;
//		this.dynamicUserRepositoryCustom = dynamicUserRepositoryCustom;
	}


	public class EncryptedPasswordData {
		private String encryptedPassword;
		private String salt;

		public EncryptedPasswordData(String encryptedPassword, String salt) {
			this.encryptedPassword = encryptedPassword;
			this.salt = salt;
		}

		public String getEncryptedPassword() {
			return encryptedPassword;
		}
		public void setEncryptedPassword(String encryptedPassword) {
			this.encryptedPassword = encryptedPassword;
		}
		public String getSalt() {
			return salt;
		}
		public void setSalt(String salt) {
			this.salt = salt;
		}
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
	 * @param businessNumber : 사업자번호(고유값)
	 * @return
	 */
	@Transactional(value="secondTransactionManager", rollbackFor= {Exception.class})
	public boolean CreateDynamicTable(String businessNumber) {
		log.info("CreateDynamicTable 호출");

		boolean isSuccess = false;

		try {
//			List<HashMap<String, Object>> commonTable = SelectCommonUserTable();

			List<CommonFieldDto> commonTable = commonRepositoryCustom.selectCommonUserTable();
			log.info("commonTable : "+commonTable);

//
//			StringBuffer sb = new StringBuffer();
//
//			sb.append("SET sql_mode = '';");
//			sb.append("CREATE TABLE " + "`" + businessNumber + "` ( ");
//
//			int num = 0;
//			for (HashMap<String, Object> column : commonTable) {
//				String query = "";
//				String Field = column.get("Field").toString();
//				String Type = column.get("Type").toString();
//				String Null = column.get("Null").toString();
//				String Extra = column.get("Extra").toString();
//				String Key = column.get("Key").toString();
//				String Default = "";
//
//				if(column.containsKey("Default")) {
//					Default = column.get("Default").toString();
//				}
//
//				if(Extra.equals("auto_increment") && Key.equals("PRI")) {
//					query += "`"+ Field +"` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT";
//				} else {
//					query += "`"+ Field +"` "+ Type;
//					if(Null.equals("NO")) {
//						query += " NOT NULL";
//					} else {
//						query += " NULL";
//					}
//					if(!Default.equals(""))
//						if(Type.contains("varchar")) {
//							query += " DEFAULT " + "'" + Default + "'";
//						} else {
//							query += " DEFAULT " + Default;
//						}
//
//				}
//				String Comment = "";
//				if(column.containsKey("Comment")) {
//					Comment = column.get("Comment").toString();;
//					query += " COMMENT "  + "'" + Comment + "'";
//				}
//
//				if (num < commonTable.size() - 1) {
//					query +=",";
//				}
//
//				sb.append(query);
//				num++;
//			}
//
//			sb.append(")");
//			String createQuery = sb.toString();
//
//			HashMap<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("sql", createQuery);
////			companyService.CreateDynamicTable(paramMap);
//
//			isSuccess = true;
		} catch (Exception e) {
        	e.printStackTrace();
        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return isSuccess;
	}

}
