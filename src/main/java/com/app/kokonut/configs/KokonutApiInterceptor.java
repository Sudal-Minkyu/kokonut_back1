package com.app.kokonut.configs;

import com.app.kokonut.apiKey.ApiKeyService;
import com.app.kokonut.auth.jwt.been.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Woody
 * Date : 2022-10-24
 * Remark :
 */
@Slf4j
@Component
public class KokonutApiInterceptor implements AsyncHandlerInterceptor {

	@Autowired
	private ApiKeyService apiKeyService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("API Key 읽음");

		String JWT_KEY_HEADER = "Bearer";
		String API_KEY_HEADER = "ApiKey";
		String apikey = request.getHeader(API_KEY_HEADER);
		if(apikey == null) {
			return false;
		}

		String jwt = request.getHeader(JWT_KEY_HEADER);
		Claims claims;
		if(jwt != null) {
			claims = jwtTokenProvider.parseClaims(jwt);
		} else {
			return false;
		}
		log.info("apikey : "+apikey);
		log.info("jwt : "+jwt);

		String email = claims.getSubject(); // 이메일(로그인ID)

		Long check = apiKeyService.validateApiKey(apikey, email);
		if(check == 0) {
			log.error("유효하지 않은 APIKey 입니다.");
			return false;
		} else {
			return true;
		}


	}

}
