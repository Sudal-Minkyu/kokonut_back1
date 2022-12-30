package com.app.kokonutuser;

import com.app.kokonut.company.CompanyService;
import com.app.kokonutuser.common.CommonRepositoryCustom;
import com.app.kokonutuser.common.dto.CommonFieldDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public KokonutUserService(CompanyService companyService,
                              CommonRepositoryCustom commonRepositoryCustom, DynamicUserRepositoryCustom dynamicUserRepositoryCustom){
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


	//====================================================================//
	// TABLE INSERT, UPDATE, DELETE
	//====================================================================//
	/**
	 * 동적테이블 생성
	 * @param businessNumber : 사업자번호(고유값)
	 * @return
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
			dynamicUserRepositoryCustom.userCreateTable(createQuery);

			log.info("동적 테이블 생성 완료 - 테이블 명 : "+businessNumber);
			isSuccess = true;
		} catch (Exception e) {
			log.info("동적 테이블 생성 실패 - 테이블 명 : "+businessNumber);
		}

		return isSuccess;
	}

















}
