//package com.app.kokonut.woody.config;
//
//import com.app.kokonut.apiKey.dtos.ApiKeyKeyDto;
//import com.app.kokonut.apiKey.ApiKeyService;
//import com.app.kokonut.woody.been.ApiKeyInfo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.AsyncHandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Date;
//
///**
// * @author Woody
// * Date : 2022-10-24
// * Remark :
// */
//@Slf4j
//public class KokonutApiInterceptor implements AsyncHandlerInterceptor {
////public class KokonutApiInterceptor extends HandlerInterceptorAdapter {  // 스프링 버전 픽스
//
////	private final Logger logger = LogManager.getLogger(KokonutApiInterceptor.class);
//
////	private final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//	private final ApiKeyService apiKeyService;
//
//	@Autowired
//	public KokonutApiInterceptor(ApiKeyService apiKeyService) {
//		this.apiKeyService = apiKeyService;
//	}
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String API_KEY_HEADER = "key";
//		String key = request.getHeader(API_KEY_HEADER);
//		ApiKeyInfo apiKeyInfo = null;
//		boolean check = false;
//
//		do {
//			ApiKeyKeyDto apiKeyMap = apiKeyService.findByKey(key);
//			if(key == null || key.isEmpty()) {
//				log.error("not found api key info. api key: {}", key);
////				logger.error("not found api key info. api key: {}", key);
//				break;
//			}
//
//			apiKeyInfo = generateApiKeyInfo(apiKeyMap);
//
//			// 사용유무 확인
//			if(apiKeyInfo.getUseYn().equals("N")) {
//				log.error("blocked key: {}", key);
////				logger.error("blocked key: {}", key);
//				break;
//			}
//
//			// 타입 확인
//			Integer type = apiKeyInfo.getType();
//			if(type != 1 && type != 2) {
//				log.error("unknown type: {}. key: {}", type.toString(), key);
////				logger.error("unknown type: {}. key: {}", type.toString(), key);
//				break;
//			}
//
//			// 테스트 타입의 키는 유효날짜 확인
//			if(type == 2) {
//				Date today = new Date();
//				Date start = apiKeyInfo.getValidityStart();
//				Date end = apiKeyInfo.getValidityEnd();
//
//				if(start == null || end == null) {
//					log.error("not found validity date of test key.");
////					logger.error("not found validity date of test key.");
//					break;
//				}
//
//				if(!(today.getTime() >= start.getTime() && today.getTime() <= end.getTime())) {
//					log.error("nonvalidated test key: {}, {}~{}", key, start.toString(), end.toString());
////					logger.error("nonvalidated test key: {}, {}~{}", key, start.toString(), end.toString());
//					break;
//				}
//			}
//
//			check = true;
//		}while(false);
//
//		if(!check) {
//			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
//			return false;
//		}
//
//		request.setAttribute("apiKeyInfo", apiKeyInfo);
//
////		return super.preHandle(request, response, handler);  // 스프링 버전 픽스
//		return AsyncHandlerInterceptor.super.preHandle(request, response, handler);
//	}
//
//	private ApiKeyInfo generateApiKeyInfo(ApiKeyKeyDto apiKeyKeyDto) {
//		ApiKeyInfo apiKeyInfo = new ApiKeyInfo();
//
//		apiKeyInfo.setIdx(apiKeyKeyDto.getIdx());
//		apiKeyInfo.setCompanyIdx(apiKeyInfo.getCompanyIdx());
//		apiKeyInfo.setAdminIdx(apiKeyInfo.getCompanyIdx());
//		apiKeyInfo.setRegisterName(apiKeyInfo.getRegisterName());
//		apiKeyInfo.setKey(apiKeyInfo.getKey());
//		apiKeyInfo.setRegdate(apiKeyInfo.getRegdate());
//		apiKeyInfo.setType(apiKeyInfo.getType());
//		apiKeyInfo.setNote(apiKeyInfo.getNote());
//		apiKeyInfo.setValidityStart(apiKeyInfo.getValidityStart());
//		apiKeyInfo.setValidityEnd(apiKeyInfo.getValidityEnd());
//		apiKeyInfo.setUseAccumulate(apiKeyInfo.getUseAccumulate());
//		apiKeyInfo.setState(apiKeyInfo.getState());
//		apiKeyInfo.setUseYn(apiKeyInfo.getUseYn());
//		apiKeyInfo.setModifierIdx(apiKeyInfo.getModifierIdx());
//		apiKeyInfo.setModifierName(apiKeyInfo.getModifierName());
//		apiKeyInfo.setModifyDate(apiKeyInfo.getModifyDate());
//
//		return apiKeyInfo;
//	}
//}
