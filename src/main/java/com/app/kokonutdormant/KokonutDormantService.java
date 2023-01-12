package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantFieldCheckDto;
import com.app.kokonutdormant.dtos.KokonutDormantFieldInfoDto;
import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutuser.dtos.KokonutRemoveInfoDto;
import com.app.kokonutuser.dtos.KokonutUserFieldCheckDto;
import com.app.kokonutuser.dtos.KokonutUserFieldDto;
import com.app.kokonutuser.dtos.KokonutUserSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Woody
 * Date : 2023-01-03
 * Time :
 * Remark :
 */
@Slf4j
@Service
public class KokonutDormantService {

	private final DynamicDormantRepositoryCustom dynamicDormantRepositoryCustom;

	@Autowired
	public KokonutDormantService(DynamicDormantRepositoryCustom dynamicDormantRepositoryCustom){
		this.dynamicDormantRepositoryCustom = dynamicDormantRepositoryCustom;
	}

	// 휴면사용자 리스트 조회
	public List<KokonutDormantListDto> listDormant(KokonutUserSearchDto kokonutUserSearchDto, String businessNumber) {
		log.info("listDormant 호출");

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT * FROM `").append(businessNumber).append("` WHERE ");
		if(!kokonutUserSearchDto.getBaseDate().equals("") && kokonutUserSearchDto.getBaseDate() != null) {
			sb.append("`").append(kokonutUserSearchDto.getBaseDate()).append("` BETWEEN '")
					.append(kokonutUserSearchDto.getStimeStart()).append("' AND '").append(kokonutUserSearchDto.getStimeEnd()).append("'");
		} else {
			sb.append("`REGDATE` BETWEEN '").append(kokonutUserSearchDto.getStimeStart()).append("' AND '").append(kokonutUserSearchDto.getStimeEnd()).append("'");
		}

		if(kokonutUserSearchDto.getSearchText() != null) {
			sb.append(" AND `ID` LIKE CONCAT('%','").append(kokonutUserSearchDto.getSearchText()).append("','%')");
		}

		sb.append(" ORDER BY `REGDATE` DESC");

		String searchQuery = sb.toString();
//		log.info("searchQuery : "+searchQuery);

//		List<KokonutDormantListDto> kokonutDormantListDtos = dynamicDormantRepositoryCustom.findByDormantPage(searchQuery);
//		log.info("kokonutDormantListDtos : "+kokonutDormantListDtos);

		return dynamicDormantRepositoryCustom.findByDormantPage(searchQuery);
	}

	// 휴면회원 등록여부 조회
    public Integer selectDormantCount(String businessNumber, Integer idx) {
		log.info("selectDormantCount 호출");
		String searchQuery = "SELECT COUNT(1) FROM "+"`"+businessNumber+"`"+" WHERE `IDX`="+idx;
		return dynamicDormantRepositoryCustom.selectDormantCount(searchQuery);
    }

	// 휴면테이블의 아이디 존재 유무 확인
	public boolean isDormantExistId(String businessNumber, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("businessNumber", businessNumber);
		map.put("id", id);

		String searchQuery = "SELECT COUNT(*) FROM `" + businessNumber + "` WHERE 1=1 AND `ID`= '"+id+"'";
//		log.info("searchQuery : "+searchQuery);

		Integer count = dynamicDormantRepositoryCustom.selectDormantIdCheck(searchQuery);

		if(count > 0) {
			return true;
		} else {
			return false;
		}

	}

	// 휴면테이블의 회원 한명 조회
	public List<KokonutRemoveInfoDto> selectDormantDataByIdx(String businessNumber, Integer idx) {
		log.info("selectUserListCount 호출");
		String searchQuery = "SELECT IDX, ID FROM `" + businessNumber + "` WHERE `IDX`="+idx;
//		log.info("searchQuery : "+searchQuery);
		return dynamicDormantRepositoryCustom.selectDormantDataByIdx(searchQuery);
	}

	/**
	 * 휴면테이블 회원정보 저장
	 * @param businessNumber : 테이블 명
	 * @param nameString 컬럼 리스트
	 * @param valueString 값 리스트
	 * @return boolean
	 * 기존 코코넛 : boolean DeleteRemoveDbUser
	 */
	@Transactional
	public boolean insertDormantTable(String businessNumber, String nameString, String valueString) {
		log.info("insertDormantTable 호출");

//		log.info("businessNumber : "+businessNumber);
//		log.info("nameString : "+nameString);
//		log.info("valueString : "+valueString);

		boolean isSuccess = false;

		try {
			String insertQuery = "INSERT INTO `" + businessNumber + "` " +
					nameString +
					" VALUES " +
					valueString;
//			log.info("insertQuery : "+insertQuery);
			dynamicDormantRepositoryCustom.dormantCommonTable(insertQuery);

			isSuccess = true;
			log.info("삭제할 유저저장 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("삭제할 유저저장 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	// 휴면테이블의 개인정보 수정
	@Transactional
	public boolean updateDormantTable(String businessNumber, Long idx, String queryString) {
		log.info("updateDormantTable 호출");

		log.info("businessNumber : "+businessNumber);
		log.info("idx : "+idx);
		log.info("queryString : "+queryString);

		boolean isSuccess = false;

		try {
			String dormantUpdateQuery = "UPDATE `"+businessNumber+"` set "+queryString+" WHERE `IDX` = "+idx;
//			log.info("dormantUpdateQuery : "+dormantUpdateQuery);
			dynamicDormantRepositoryCustom.dormantCommonTable(dormantUpdateQuery);

			log.info("휴면 개인정보 수정 성공 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.error("휴면 개인정보 수정 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	// 휴면테이블의 컬럼 목록 조회
	public List<KokonutUserFieldDto> getDormantColumns(String businessNumber) {
		log.info("getDormantColumns 호출");
		String searchQuery = "SHOW FULL COLUMNS FROM `"+businessNumber+"`";
//		log.info("searchQuery : "+searchQuery);
		return dynamicDormantRepositoryCustom.selectDormantColumns(searchQuery);
	}

	/**
	 * 암호화 속성을 갖는 테이블 컬럼 목록 조회
	 * @param businessNumber 테이블 이름
	 * @return Column 객체 리스트 -> KokonutUserFieldDto
	 * 기존 코코넛 : SelectEncryptColumns
	 */
	public List<KokonutUserFieldDto> selectDormantEncryptColumns(String businessNumber) {
		log.info("selectDormantEncryptColumns 호출");
		String searchQuery = "SHOW FULL COLUMNS FROM `"+businessNumber+"` WHERE `COMMENT` REGEXP '(.+)(\\()(.*암호화.*)(\\))'";
//		log.info("searchQuery : "+searchQuery);
		return dynamicDormantRepositoryCustom.selectDormantColumns(searchQuery);
	}

	// 컬럼(필드) 추가
	@Transactional
	public void alterAddColumnTableQuery(String businessNumber, String field, String type, int length, Boolean isNull, String defaultValue, String comment) {
		log.info("alterAddColumnTableQuery 휴면 호출");

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
			sb.append(" COMMENT " + "'").append(comment).append("'");

			String updateQuery = sb.toString();

			dynamicDormantRepositoryCustom.dormantCommonTable(updateQuery);

			log.info("휴면테이블 필드추가 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("휴면테이블 필드추가 에러 : "+businessNumber);
		}

	}

	// 컬럼(필드) 코멘트 변경
	@Transactional
	public void alterModifyColumnCommentQuery(String businessNumber, String field, String type, int length, Boolean isNull, String defaultValue, String comment) {
		log.info("alterModifyColumnCommentQuery 휴면 호출");

		try {

			String nullStr = "NULL";
			if(!isNull) {
				nullStr = "NOT NULL";
			}

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

			dynamicDormantRepositoryCustom.dormantCommonTable(updateQuery);

			log.info("휴면테이블 필드 코멘트 수정 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("휴면테이블 필드 코멘트 수정 에러 : "+businessNumber);
		}

	}

	// 컬럼(필드)정보 수정
	@Transactional
	public void alterChangeColumnTableQuery(String businessNumber, String beforField, String afterField, String type, int length, Boolean isNull, String defaultValue, String comment)  {
		log.info("alterChangeColumnTableQuery 휴면 호출");

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

			dynamicDormantRepositoryCustom.dormantCommonTable(updateQuery);

			log.info("휴면테이블 필드정보 수정 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("휴면테이블 필드정보 수정 에러 : "+businessNumber);
		}

	}

	// 휴면테이블 - 필드-값 쌍으로 사용자 컬럼값 조회
    public List<KokonutDormantFieldInfoDto> selectDormantFieldList(String businessNumber, String field) {
		log.info("selectDormantFieldList 호출");

		String searchQuery = "SELECT `IDX`,`"+field+"` FROM `" + businessNumber+"`";
//		log.info("searchQuery : " + searchQuery);

		try {
			return dynamicDormantRepositoryCustom.selectDormantFieldList(field, searchQuery);
		} catch (Exception e) {
			log.error("존재하지 않은 field : "+field+" 입니다.");
			return null;
		}
    }

	// 휴면테이블 컬럼(필드) 삭제
	@Transactional
	public void alterDropColumnDormantTableQuery(String businessNumber, String field) {
		log.info("alterModifyColumnDormantCommentQuery 호출");

		try {
			String dropQuery = "ALTER TABLE `" + businessNumber + "` DROP COLUMN " + "`" + field + "`";

			dynamicDormantRepositoryCustom.dormantCommonTable(dropQuery);

			log.info("휴면테이블 필드삭제 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("휴면테이블 필드삭제 에러 : "+businessNumber);
		}
	}

	// 휴면테이블의 필드명을 통해 테이블명, 필드명 조회 -> 삭제하기위해 조회하는 메서드
	public List<KokonutDormantFieldCheckDto> selectDormantTableNameAndFieldName(String businessNumber, String fieldName) {
		log.info("selectDormantTableNameAndFieldName 호출");

		try {
			String searchQuery = "SELECT TABLE_NAME, COLUMN_NAME FROM information_schema.columns where '" + businessNumber + "' AND column_name = '"+fieldName+"' limit 1";
//			log.info("searchQuery : "+searchQuery);
			return dynamicDormantRepositoryCustom.selectDormantTableNameAndFieldName(searchQuery);
		} catch (Exception e ){
			log.error("존재하지 않은 필드입니다. ");
			return null;
		}
	}

	// 휴면 테이블 중복검사
	public int selectExistDormantTable(String businessNumber) {
		log.info("selectExistDormantTable 호출");
		return dynamicDormantRepositoryCustom.selectExistDormantTable(businessNumber);
	}

}
