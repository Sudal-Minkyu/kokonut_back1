//package com.app.kokonut.woody.common.security;
//
//import com.app.kokonut.activityHistory.ActivityHistoryService;
//import com.app.kokonut.admin.AdminService;
//import com.app.kokonut.bean.KokonutUser;
//import com.app.kokonut.common.component.CommonUtil;
//import com.app.kokonut.common.security.bean.AuthUser;
//import com.app.kokonut.login.LoginService;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//
//
//
//
//public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler{
//
//	private Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
//
//	@Autowired
//	LoginService loginService;
//
//	@Autowired
//	AdminService adminService;
//
//	@Autowired
//	ActivityHistoryService activityHistoryService;
//
//	private String defaultUrl;
//
//	public String getDefaultUrl() {
//		return defaultUrl;
//	}
//
//	public void setDefaultUrl(String defaultUrl) {
//		this.defaultUrl = defaultUrl;
//	}
//
//	public AuthenticationSuccessHandlerImpl() {
//		defaultUrl= "/";
//	}
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException
//	{
//
//		String adminUrl = "/system/dashboard/dashboardView";
//		String userUrl = "/member/dashboard/dashboard";
//
//		AuthUser authUser = (AuthUser) auth.getPrincipal();
//
//		logger.info("---------------------------------------------------");
//		logger.info("---------------------------------------------------");
//		logger.info("---------------------------------------------------");
//		logger.info("###LoginUser Id(Email): " + authUser.getUser().getEmail());
//		logger.info("###LoginUser Name: " + authUser.getUser().getName());
//		logger.info("---------------------------------------------------");
//		logger.info("---------------------------------------------------");
//		logger.info("---------------------------------------------------");
//
//
//		res.setContentType("application/json");
//		res.setCharacterEncoding("utf-8");
//
//
//		String url ="";
//		int pwdChangeDiff = authUser.getUser().getPwdChangeDiff();
//		if(pwdChangeDiff > 5) {
//			url = "/member/admin/loginPlzChangePw";
//		} else {
//			if(KokonutUser.ROLE_SYSTEM.equals(authUser.getUser().getRoleName())) {
//				url = adminUrl;
//			}
//			else {
//				url = userUrl;
//
//				HttpSession session = req.getSession();
//		        if (session != null) {
//		            String redirectUrl = (String) session.getAttribute("prevPage");
////		            url = redirectUrl;
//		            if (redirectUrl != null) {
//		            	if(redirectUrl.equals("/myPage/myHome")) {
//		        			url = redirectUrl;
//		        		}
//
//		                session.removeAttribute("prevPage");
//		            }
//		        }
//			}
//		}
//
//		/* 로그인 정보 업데이트 */
//		HashMap<String, Object> loginMap = new HashMap<String, Object>();
//		loginMap.put("idx", authUser.getUser().getIdx());
//		loginMap.put("ip", CommonUtil.getServerIp());
//		loginService.UpdateLoginInfo(loginMap);
//
//		/* 로그인 성공 후 다시 업데이트 처리 */
//		adminService.UpdateIsLoginAuth(authUser.getUser().getIdx(), "N");
//
//		/* 마스터 관리자, 일반관리자일 경우 활동이력(로그인) 추가 */
//		if(KokonutUser.ROLE_MASTER.equals(authUser.getUser().getRoleName()) || KokonutUser.ROLE_ADMIN.equals(authUser.getUser().getRoleName())){
//			int companyIdx = authUser.getUser().getCompanyIdx();
//			int adminIdx = authUser.getUser().getIdx();
//
//			activityHistoryService.InsertActivityHistory(2, companyIdx, adminIdx, 1, "", "", CommonUtil.clientIp(), 1);
//		}
//
//		JSONObject responseJson = new JSONObject();
//		JSONObject responseDataJson = new JSONObject();
//		responseDataJson.put("error", false);
//		responseDataJson.put("url", url);
//		responseJson.put("response", responseDataJson);
//
//		PrintWriter out = res.getWriter();
//		out.print(responseJson.toString());
//		out.flush();
//		out.close();
//
//	}
//
//}
