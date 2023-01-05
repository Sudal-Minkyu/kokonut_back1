package com.app.kokonutremove;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Woody
 * Date : 2023-01-03
 * Time :
 * Remark :
 */
@Slf4j
@Service
public class KokonutRemoveService {

	private final DynamicRemoveRepositoryCustom dynamicRemoveRepositoryCustom;

	@Autowired
	public KokonutRemoveService(DynamicRemoveRepositoryCustom dynamicRemoveRepositoryCustom){
		this.dynamicRemoveRepositoryCustom = dynamicRemoveRepositoryCustom;
	}

	/**
	 * REMOVE DB > tableName 테이블에 회원정보 저장
	 * @param businessNumber : 테이블 명
	 * @param nameString 컬럼 리스트
	 * @param valueString 값 리스트
	 * @return boolean
	 * 기존 코코넛 : InsertRemoveDbUser
	 */
	@Transactional
	public boolean insertRemoveTable(String businessNumber, String nameString, String valueString) {
		log.info("insertRemoveTable 호출");

		log.info("businessNumber : "+businessNumber);
		log.info("nameString : "+nameString);
		log.info("valueString : "+valueString);

		boolean isSuccess = false;

		try {
			String insertQuery = "INSERT INTO `" + businessNumber + "` " +
					nameString +
					" VALUES " +
					valueString;
			log.info("insertQuery : "+insertQuery);
			dynamicRemoveRepositoryCustom.userCommonTable(insertQuery);

			isSuccess = true;
			log.info("삭제할 유저저장 성공 : "+businessNumber);
		} catch (Exception e) {
			log.error("삭제할 유저저장 에러 : "+businessNumber);
		}

		return isSuccess;
	}

	/**
	 * REMOVE DB > tableName 테이블에 회원정보 삭제
	 * @param businessNumber : 테이블 명
	 * @param idx 회원 IDX
	 * @return boolean
	 * 기존 코코넛 : DeleteRemoveDbUser
	 */
	@Transactional
	public boolean deleteRemoveTable(String businessNumber, int idx) {
		log.info("deleteRemoveTable 호출");

		log.info("businessNumber : "+businessNumber);
		log.info("idx : "+idx);

		boolean isSuccess = false;

		try {
			String removeDeleteQuery = "DELETE FROM `"+businessNumber+"` WHERE `IDX` = "+idx;
			log.info("removeDeleteQuery : "+removeDeleteQuery);
			dynamicRemoveRepositoryCustom.userCommonTable(removeDeleteQuery);

			log.info("삭제한 유저삭제 성공 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.error("삭제한 유저삭제 에러 : "+businessNumber);
		}

		return isSuccess;
	}




}