package com.app.kokonut.woody.config;

import com.app.kokonut.apiKey.dto.ApiKeyKeyDto;
import com.app.kokonut.apiKey.service.ApiKeyService;
import com.app.kokonut.woody.been.ApiKeyInfo;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Woody
 * Date : 2022-10-24
 * Remark :
 */
public class KokonutApiInterceptor implements AsyncHandlerInterceptor {
//public class KokonutApiInterceptor extends HandlerInterceptorAdapter {  // 스프링 버전 픽스
	private final Logger logger = LogManager.getLogger(KokonutApiInterceptor.class);
	
	private final String API_KEY_HEADER = "key";
	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final ApiKeyService apiKeyService;

	@Autowired
    public KokonutApiInterceptor(ApiKeyService apiKeyService){
        this.apiKeyService = apiKeyService;
    }

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String key = request.getHeader(API_KEY_HEADER);
		ApiKeyInfo apiKeyInfo = null;
		boolean check = false;
		
		do {
			ApiKeyKeyDto apiKeyMap = apiKeyService.findByKey(key);
			if(key == null || key.isEmpty()) {
				logger.error("not found api key info. api key: {}", key);
				break;
			}

//			apiKeyInfo = generateApiKeyInfo(apiKeyMap);
			if(apiKeyInfo == null) {
				logger.error("filed to generate ApiKeyInfo object. api key: {}", key);
				break;
			}

			// 사용유무 확인
			if(apiKeyInfo.getUseYn().equals("N")) {
				logger.error("blocked key: {}", key);
				break;
			}

			// 타입 확인
			Integer type = apiKeyInfo.getType();
			if(type != 1 && type != 2) {
				logger.error("unknown type: {}. key: {}", type.toString(), key);
				break;
			}

			// 테스트 타입의 키는 유효날짜 확인
			if(type == 2) {
				Date today = new Date();
				Date start = apiKeyInfo.getValidityStart();
				Date end = apiKeyInfo.getValidityEnd();

				if(start == null || end == null) {
					logger.error("not found validity date of test key.");
					break;
				}

				if(!(today.getTime() >= start.getTime() && today.getTime() <= end.getTime())) {
					logger.error("nonvalidated test key: {}, {}~{}", key, start.toString(), end.toString());
					break;
				}
			}

			check = true;
		}while(false);
		
		if(!check) {
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			return false;
		}
		
		request.setAttribute("apiKeyInfo", apiKeyInfo);
		
//		return super.preHandle(request, response, handler);  // 스프링 버전 픽스
		return preHandle(request, response, handler);
	}
	
//	private ApiKeyInfo generateApiKeyInfo(ApiKeyKeyDto apiKeyKeyDto) throws ParseException {
//		ApiKeyInfo apiKeyInfo = new ApiKeyInfo();
//
//		apiKeyInfo.setIdx(Integer.parseInt(apiKeyKeyDto.getIdx().toString()));

//		if(map.containsKey("COMPANY_IDX")){
//			apiKeyInfo.setCompanyIdx(Integer.parseInt(map.get("COMPANY_IDX").toString()));
//		}
//		if(map.containsKey("ADMIN_IDX")) {
//			apiKeyInfo.setAdminIdx(Integer.parseInt(map.get("ADMIN_IDX").toString()));
//		}
//		if(map.containsKey("REGISTER_NAME")) {
//			apiKeyInfo.setRegisterName(map.get("REGISTER_NAME").toString());
//		}
//		if(map.containsKey("KEY")) {
//			apiKeyInfo.setKey(map.get("KEY").toString());
//		}
//		if(map.containsKey("REGDATE")) {
//			apiKeyInfo.setRegdate(SDF.parse(map.get("REGDATE").toString()));
//		}
//		if(map.containsKey("TYPE")) {
//			apiKeyInfo.setType(Integer.parseInt(map.get("TYPE").toString()));
//		}
//		if(map.containsKey("NOTE")) {
//			apiKeyInfo.setNote(map.get("NOTE").toString());
//		}
//		if(map.containsKey("VALIDITY_START")) {
//			apiKeyInfo.setValidityStart(SDF.parse(map.get("VALIDITY_START").toString()));
//		}
//		if(map.containsKey("VALIDITY_END")) {
//			apiKeyInfo.setValidityEnd(SDF.parse(map.get("VALIDITY_END").toString()));
//		}
//		if(map.containsKey("USE_ACCUMULATE")) {
//			apiKeyInfo.setUseAccumulate(Integer.parseInt(map.get("USE_ACCUMULATE").toString()));
//		}
//		if(map.containsKey("STATE")) {
//			apiKeyInfo.setState(Integer.parseInt(map.get("STATE").toString()));
//		}
//		if(map.containsKey("USE_YN")) {
//			apiKeyInfo.setUseYn(map.get("USE_YN").toString().equals("Y"));
//		}
//		if(map.containsKey("MODIFIER_IDX")) {
//			apiKeyInfo.setModifierIdx(Integer.parseInt(map.get("MODIFIER_IDX").toString()));
//		}
//		if(map.containsKey("MODIFIER_NAME")) {
//			apiKeyInfo.setModifierName(map.get("MODIFIER_NAME").toString());
//		}
//		if(map.containsKey("MODIFY_DATE")) {
//			apiKeyInfo.setModifyDate(SDF.parse(map.get("MODIFY_DATE").toString()));
//		}
		
//		return apiKeyInfo;
//	}
}
