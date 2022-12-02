//package com.app.kokonut.woody.common.security;
//
//import com.app.kokonut.common.component.AriaUtil;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//
//public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
//
//	private Logger logger = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);
//
//	@Autowired
//	AriaUtil ariaUtil;
//
//	@SuppressWarnings({ "deprecation", "unchecked" })
//	@Override
//    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException
//	{
//		logger.info("onAuthenticationFailure");
//
//		String msg = ex.getMessage();
//		HashMap<String, Object> extraInfo = (HashMap<String, Object>) ex.getExtraInformation();
//
//
//		//HashMap<String, Object> failMap = (HashMap<String, Object>) ex.getExtraInformation();
//		//loginService.Update(failMap.get("accountId").toString(), 2);
//
//		res.setContentType("application/json");
//		res.setCharacterEncoding("utf-8");
//
//		JSONObject responseObject = new JSONObject();
//		JSONObject jsonObject = new JSONObject();
//
//		jsonObject.put("error", true);
//		jsonObject.put("msg", msg);
//
//		if(extraInfo.get("encryptIdx") != null && !"".equals(extraInfo.get("encryptIdx"))) {
//			jsonObject.put("encryptIdx", extraInfo.get("encryptIdx").toString());
//		}
//
//		if(msg.equals("ERROR_1")) {
//			// encryptIdx 가데이터 입력
//			int provisionalIdx = (int) (Math.random() * 10000);
//			jsonObject.put("encryptIdx", ariaUtil.Encrypt(String.valueOf(provisionalIdx)));
//		}
//
//		responseObject.put("response", jsonObject);
//
//		PrintWriter out = res.getWriter();
//		out.print(responseObject.toString());
//		out.flush();
//		out.close();
//	}
//
//}
