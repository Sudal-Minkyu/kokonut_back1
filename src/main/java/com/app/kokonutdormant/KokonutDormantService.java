package com.app.kokonutdormant;

import com.app.kokonutdormant.dtos.KokonutDormantListDto;
import com.app.kokonutuser.dtos.KokonutUserSearchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
		log.info("searchQuery : "+searchQuery);

//		List<KokonutDormantListDto> kokonutDormantListDtos = dynamicDormantRepositoryCustom.findByDormantPage(searchQuery);
//		log.info("kokonutDormantListDtos : "+kokonutDormantListDtos);

		return dynamicDormantRepositoryCustom.findByDormantPage(searchQuery);
	}

}
