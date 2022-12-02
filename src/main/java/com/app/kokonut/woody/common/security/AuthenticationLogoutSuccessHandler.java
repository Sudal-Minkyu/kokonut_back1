//package com.app.kokonut.woody.common.security;
//
//import com.app.kokonut.common.security.bean.AuthUser;
//import com.app.kokonut.login.LoginService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//
//
//
//public class AuthenticationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
//
//	@Autowired
//	LoginService loginService;
//
//	private Logger logger = LoggerFactory.getLogger(AuthenticationLogoutSuccessHandler.class);
//
//	@Override
//	public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
//
//		logger.info("onLogoutSuccess");
//		String rUrl = "/";
////		String rUrl = "/Login/LoginUI";
//
//		if(auth != null)
//		{
//			AuthUser authUser = (AuthUser) auth.getPrincipal();
//
//			if( authUser.getConcurrency() == 1 ) rUrl = "/";
//		}
//
//		setDefaultTargetUrl(rUrl);
//		super.onLogoutSuccess(req, res, auth);
//	}
//}
