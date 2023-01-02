package com.app.kokonutuser;

import com.app.kokonut.company.CompanyService;
import com.app.kokonutuser.common.CommonRepositoryCustom;
import com.app.kokonutuser.common.dto.CommonFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2022-12-28
 * Time :
 * Remark : Kokonut-user Service -> DynamicUserService에서 호출하는 서비스
 */
@Slf4j
@Service
public class KokonutUserService {

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
	private final DynamicUserRepositoryCustom dynamicUserRepositoryCustom;

//	@Autowired
//	ExcelService excelService;

	@Autowired
	public KokonutUserService(CompanyService companyService, CommonRepositoryCustom commonRepositoryCustom,
							  DynamicUserRepositoryCustom dynamicUserRepositoryCustom){
		this.companyService = companyService;
		this.commonRepositoryCustom = commonRepositoryCustom;
		this.dynamicUserRepositoryCustom = dynamicUserRepositoryCustom;
	}

	/**
	 * 시스템 관리자가 지정한 기본 테이블 정보 조회
	 */
//	public List<HashMap<String, Object>> SelectCommonUserTable() {
//		return dynamicUserDao.SelectCommonUserTable();
//	}

	/**
	 * 유저DB 테이블 중복검사
	 * 기존 코코넛 : SelectExistTable
	 */
	public int selectExistTable(String businessNumber) {
		log.info("selectExistTable 호출");
		return dynamicUserRepositoryCustom.selectExistTable(businessNumber);
	}

	// 기존 코코넛 : ExistTable
	public boolean existTable(String businessNumber) {
		log.info("existTable 호출");
		int result = selectExistTable(businessNumber);

		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
	// 유저테이블 : 생성, 삭제 + 테이블컬럼 : 추가, 삭제, 수정 @ //
	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //

	/**
	 * 동적테이블 생성
	 * @param businessNumber : 사업자번호(고유값)
	 * @return boolean
	 * 기존 코코넛 : CreateDynamicTable
	 */
	@Transactional
	public boolean createTableKokonutUser(String businessNumber) {
		log.info("createTableKokonutUser 호출");

		boolean isSuccess = false;

		try {
//			List<HashMap<String, Object>> commonTable = SelectCommonUserTable();

			List<CommonFieldDto> commonTable = commonRepositoryCustom.selectCommonUserTable();
			log.info("가져온 commonTable 필드리스트 : "+commonTable);
			
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE " + "`").append(businessNumber).append("` ( ");

			int num = 0;
			for (CommonFieldDto commonFieldDto : commonTable) {
				String query = "";
				String Field = commonFieldDto.getTableField();
				String Type = commonFieldDto.getTableType();
				String Null = commonFieldDto.getTableNull();
				String Extra = commonFieldDto.getTableExtra();
				String Key = commonFieldDto.getTableKey();
				String Default = "";

				if(commonFieldDto.getTableDefault() != null) {
					Default = commonFieldDto.getTableDefault();
				}

				if(Extra.equals("auto_increment") && Key.equals("PRI")) {
					query += "`"+ Field +"` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT";
				} else {
					query += "`"+ Field +"` "+ Type;
					if(Null.equals("NO")) {
						query += " NOT NULL";
					} else {
						query += " NULL";
					}
					if(!Default.equals(""))
						if(Type.contains("varchar")) {
							query += " DEFAULT " + "'" + Default + "'";
						} else {
							query += " DEFAULT " + Default;
						}

				}
				String Comment;
				if(commonFieldDto.getTableComment() != null) {
					Comment = commonFieldDto.getTableComment();
					query += " COMMENT "  + "'" + Comment + "'";
				}

				if (num < commonTable.size() - 1) {
					query +=",";
				}

				sb.append(query);
				num++;
			}

			sb.append(")");
			String createQuery = sb.toString();

			log.info("createQuery : "+createQuery);
			dynamicUserRepositoryCustom.userCommonTable(createQuery);

			log.info("동적 테이블 생성 완료 - 테이블 명 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.error("동적 테이블 생성 실패 - 테이블 명 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * 동적테이블 삭제
	 * @param businessNumber : 사업자번호(고유값)
	 * @return String
	 * 기존 코코넛 : DeleteDynamicTable
	 */
	@Transactional
	public String deleteTableKokonutUser(String businessNumber) {
		log.info("deleteTableKokonutUser 호출");

		String result;

		try {
			boolean check = existTable(businessNumber);

			if(check) {
				String deleteQuery = "DROP TABLE `" + businessNumber + "`";
				dynamicUserRepositoryCustom.userCommonTable(deleteQuery);

				log.info("저장된 유저테이블 삭제 성공 : "+businessNumber);
				result = "success";

			} else {

				log.error("저장된 유저테이블이 없음 : "+businessNumber);
				result = "fail";
			}

		} catch (Exception e) {
			result = "error";
			log.error("유저테이블 삭제 에러 : "+businessNumber);
		}

		return result;
	}

	/**
	 * 컬럼(필드) 추가
	 *
	 * @param businessNumber		- 변경할 테이블 명
	 * @param field 		- 변경 할 컬럼
	 * @param type 			- 데이터 타입
	 * @param length		- 데이터 길이
	 * @param isNull		- null 여부(true: null, false: not null)
	 * @param defaultValue  - 디폴트 값
	 * @return boolean
	 * 기존 코코넛 : AlterAddColumnTableQuery
	 */
	@Transactional
	public boolean alterAddColumnTableQuery(String businessNumber, String field, String type, int length, Boolean isNull, String defaultValue) {
		log.info("alterAddColumnTableQuery 호출");

		boolean isSuccess = false;

		try {

			String nullStr = "NULL";

			if(!isNull) {
				nullStr = "NOT NULL";
			}

			StringBuilder sb = new StringBuilder();

			sb.append("ALTER TABLE `").append(businessNumber).append("`");
			sb.append(" ADD COLUMN " + "`").append(field).append("`");

			if(length == 0) {
				sb.append(" ").append(type).append(" ").append(nullStr);
			} else {
				sb.append(" ").append(type).append("(").append(length).append(")").append(" ").append(nullStr);
			}

			if(!defaultValue.equals("")) {
				if(defaultValue.equals("CURRENT_TIMESTAMP")) {
					sb.append(" DEFAULT CURRENT_TIMESTAMP");
				} else {
					sb.append(" DEFAULT " + "'").append(defaultValue).append("'");
				}
			}

			String updateQuery = sb.toString();

			dynamicUserRepositoryCustom.userCommonTable(updateQuery);

			isSuccess = true;
			log.info("유저테이블 필드추가 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("유저테이블 필드추가 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * 컬럼(필드)정보 변경
	 * @param businessNumber		- 변경할 테이블 명
	 * @param beforField 	- 변경 전 컬럼
	 * @param afterField 	- 변경 할 컬럼
	 * @param type 			- 데이터 타입
	 * @param length		- 데이터 길이
	 * @param isNull		- null 여부(true: null, false: not null)
	 * @return boolean
	 * 기존 코코넛 : AlterChangeColumnTableQuery
	 */
	@Transactional
	public boolean alterChangeColumnTableQuery(String businessNumber, String beforField, String afterField, String type, int length, Boolean isNull, String comment, String defaultValue)  {
		log.info("alterChangeColumnTableQuery 호출");

		boolean isSuccess = false;

		try {

			String nullStr = "NULL";
			if(!isNull)
				nullStr = "NOT NULL";

			StringBuilder sb = new StringBuilder();

			sb.append("ALTER TABLE `").append(businessNumber).append("`");
			sb.append(" CHANGE COLUMN " + "`").append(beforField).append("`").append(" ").append("`").append(afterField).append("`");

			if(length == 0) {
				sb.append(" ").append(type).append(" ").append(nullStr);
			} else {
				sb.append(" ").append(type).append("(").append(length).append(")").append(" ").append(nullStr);
			}

			if(!defaultValue.equals("")) {
				if(defaultValue.equals("CURRENT_TIMESTAMP")) {
					sb.append(" DEFAULT CURRENT_TIMESTAMP");
				} else {
					sb.append(" DEFAULT " + "'").append(defaultValue).append("'");
				}
			}

			sb.append(" COMMENT " + "'").append(comment).append("'");

			String updateQuery = sb.toString();

			dynamicUserRepositoryCustom.userCommonTable(updateQuery);

			isSuccess = true;
			log.info("유저테이블 필드정보 수정 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("유저테이블 필드정보 수정 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * 컬럼(필드) 코멘트 변경
	 * @param businessNumber		- 변경할 테이블 명
	 * @param field 		- 변경 할 컬럼
	 * @param type 			- 데이터 타입
	 * @param length		- 데이터 길이
	 * @param isNull		- null 여부(true: null, false: not null)
	 * @param comment		- 변경 할 코멘트
	 * @return boolean
	 * 기존 코코넛 : AlterModifyColumnCommentQuery
	 */
	@Transactional
	public boolean alterModifyColumnCommentQuery(String businessNumber, String field, String type, int length, Boolean isNull, String comment, String defaultValue) {
		log.info("alterModifyColumnCommentQuery 호출");

		boolean isSuccess = false;

		try {

			String nullStr = "NULL";
			if(!isNull)
				nullStr = "NOT NULL";

			StringBuilder sb = new StringBuilder();

			sb.append("ALTER TABLE `").append(businessNumber).append("`");
			sb.append(" MODIFY " + "`").append(field).append("`");

			if(length == 0) {
				sb.append(" ").append(type).append(" ").append(nullStr);
			} else {
				sb.append(" ").append(type).append("(").append(length).append(")").append(" ").append(nullStr);
			}

			if(!defaultValue.equals("")) {
				if(defaultValue.equals("CURRENT_TIMESTAMP")) {
					sb.append(" DEFAULT CURRENT_TIMESTAMP");
				} else {
					sb.append(" DEFAULT " + "'").append(defaultValue).append("'");
				}
			}

			sb.append(" COMMENT " + "'").append(comment).append("'");

			String updateQuery = sb.toString();

			dynamicUserRepositoryCustom.userCommonTable(updateQuery);

			isSuccess = true;
			log.info("유저테이블 필드 코멘트 수정 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("유저테이블 필드 코멘트 수정 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * 컬럼(필드) 삭제
	 * @param businessNumber	- 변경할 테이블 명
	 * @param field		- 삭제할 컬럼명
	 * @return boolean
	 * 기존 코코넛 : AlterDropColumnTableQuery
	 */
	@Transactional
	public boolean alterDropColumnTableQuery(String businessNumber, String field) {
		log.info("alterModifyColumnCommentQuery 호출");

		boolean isSuccess = false;

		try {
			String updateQuery = "ALTER TABLE `" + businessNumber + "` DROP COLUMN " + "`" + field + "`";

			dynamicUserRepositoryCustom.userCommonTable(updateQuery);

			isSuccess = true;
			log.info("유저테이블 필드삭제 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("유저테이블 필드삭제 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //

	/**
	 * 유저테이블의 회원 조회
	 * 기존 코코넛 : List<HashMap<String, Object>> SelectUserList
	 */
	public List<Map<String, Object>> selectUserList(String businessNumber) {
		log.info("selectUserList 호출");

		String searchQuery = "SELECT * FROM `"+businessNumber+"` WHERE 1=1";
		log.info("searchQuery : "+searchQuery);

		List<Map<String, Object>> result = dynamicUserRepositoryCustom.selectUserList(searchQuery);
		log.info("result : "+result);

		if(result == null || result.size() == 0) {
			log.error("조회된 회원정보가 없습니다.");
			return null;
		}

		for(Map<String, Object> user : result) {
			String email = user.get("EMAIL").toString();
			String name = user.get("NAME").toString();
			log.info("email : "+email);
			log.info("name : "+name);
		}

		return result;
	}

	/**
	 * 유저테이블의 회원 수 조회
	 * 기존 코코넛 : int SelectUserListCount
	 */
	public int selectUserListCount(String businessNumber) {
		log.info("selectUserListCount 호출");
		String searchQuery = "SELECT COUNT(*) FROM `" + businessNumber + "` WHERE 1=1";
		return dynamicUserRepositoryCustom.selectUserListCount(searchQuery);
	}

	/**
	 * 유저테이블 회원정보 저장
	 * @param businessNumber : 테이블 명
	 * @param nameString 컬럼 리스트
	 * @param valueString 값 리스트
	 * @return boolean
	 * 기존 코코넛 : HashMap<String, Object> InsertUserTable
	 */
	@Transactional
	public boolean insertUserTable(String businessNumber, String nameString, String valueString) {
		log.info("insertUserTable 호출");

//		log.info("businessNumber : "+businessNumber);
//		log.info("nameString : "+nameString);
//		log.info("valueString : "+valueString);

		boolean isSuccess = false;

		try {
//			if(nameList.size() == valueList.size()) {
//				StringBuilder sb = new StringBuilder();
//
//				sb.append("INSERT INTO `").append(businessNumber).append("` (");
//
//				for (int i=0; i<nameList.size(); i++) {
//					if(i == nameList.size()-1){
//						sb.append("`").append(nameList.get(i)).append("`) VALUES (");
//					} else {
//						sb.append("`").append(nameList.get(i)).append("`, ");
//					}
//				}
//
//				for (int i=0; i<valueList.size(); i++) {
//					Object value = valueList.get(i);
//					if(value instanceof Integer || value instanceof Double){
//						// 값이 정수 또는 소수 일 경우
//						if(i == valueList.size()-1){
//							sb.append(value).append(")");
//						} else {
//							sb.append(value).append(", ");
//						}
//					} else {
//						// 값이 문자형 일경우
//						if(i == valueList.size()-1){
//							sb.append("'").append(valueList.get(i)).append("')");
//						} else {
//							sb.append("'").append(valueList.get(i)).append("', ");
//						}
//					}
//				}

			String insertQuery = "INSERT INTO `" + businessNumber + "` " +
					nameString +
					" VALUES " +
					valueString;
			log.info("insertQuery : "+insertQuery);
			dynamicUserRepositoryCustom.userCommonTable(insertQuery);

			isSuccess = true;
			log.info("유저저장 성공 : "+businessNumber);

//			} else {
//				log.error("컬럼리스트(nameList)와 값리스트(valueList) 사이즈가 맞지 않습니다.");
//			}
		} catch (Exception e) {
			log.error("유저저장 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * tableName 테이블에 회원정보 수정
	 * @param businessNumber : 테이블 명
	 * @param queryString : [{name-컬럼, value-값}]
	 * @param idx 회원 IDX
	 * @return boolean
	 * 기존 코코넛 : UpdateUserTable
	 */
	@Transactional
	public boolean updateUserTable(String businessNumber, int idx, String queryString) {
		log.info("updateUserTable 호출");

		log.info("businessNumber : "+businessNumber);
		log.info("idx : "+idx);
		log.info("queryString : "+queryString);

		boolean isSuccess = false;

		try {
			String userUpdateQuery = "UPDATE `"+businessNumber+"` set "+queryString+" WHERE `IDX` = "+idx;
			log.info("userUpdateQuery : "+userUpdateQuery);
			dynamicUserRepositoryCustom.userCommonTable(userUpdateQuery);

			log.info("유저수정 성공 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.error("유저수정 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * tableName 테이블에 회원정보 삭제
	 * @param businessNumber : 테이블 명
	 * @param idx 회원 IDX
	 * @return
	 * 기존 코코넛 : DeleteUserTable
	 */
	@Transactional
	public boolean deleteUserTable(String businessNumber, int idx) {
		log.info("deleteUserTable 호출");

		log.info("businessNumber : "+businessNumber);
		log.info("idx : "+idx);

		boolean isSuccess = false;

		try {
			String userDeleteQuery = "DELETE FROM `"+businessNumber+"` WHERE `IDX` = "+idx;
			log.info("userDeleteQuery : "+userDeleteQuery);
			dynamicUserRepositoryCustom.userCommonTable(userDeleteQuery);

			log.info("유저삭제 성공 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.error("유저삭제 에러 : "+businessNumber);
		}

		return isSuccess;
	}


















}