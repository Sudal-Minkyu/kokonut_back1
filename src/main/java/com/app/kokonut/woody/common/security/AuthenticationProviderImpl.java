//package com.app.kokonut.woody.common.security;
//
//import com.app.kokonut.activityHistory.ActivityHistoryService;
//import com.app.kokonut.admin.AdminService;
//import com.app.kokonut.adminLevel.AdminLevelService;
//import com.app.kokonut.apiKey.ApiKeyService;
//import com.app.kokonut.bean.KokonutUser;
//import com.app.kokonut.common.component.AriaUtil;
//import com.app.kokonut.common.component.CommonUtil;
//import com.app.kokonut.common.component.MailSender;
//import com.app.kokonut.common.security.bean.AuthUser;
//import com.app.kokonut.common.service.IpInfoService;
//import com.app.kokonut.login.LoginService;
//import com.app.kokonut.mail.MailController;
//import com.app.kokonut.setting.SettingService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//
//
//@Component("AuthenticationProviderImpl")
//public class AuthenticationProviderImpl implements AuthenticationProvider {
//	private Logger logger = LoggerFactory.getLogger(AuthenticationProviderImpl.class);
//
//	@Autowired
//	LoginService loginService;
//
//	@Autowired
//	AdminLevelService adminLevelService;
//
//	@Autowired
//	ApiKeyService apiKeyService;
//
//	@Autowired
//	AriaUtil ariaUtil;
//
//	@Autowired
//	SettingService settingService;
//
//	@Autowired
//	IpInfoService ipInfoService;
//
//	@Autowired
//	AdminService adminService;
//
//	@Autowired
//	MailSender mailSender;
//
//    @Autowired
//    ActivityHistoryService activityHistoryService;
//
//	@SuppressWarnings("deprecation")
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String email = authentication.getName();
//		String password = authentication.getCredentials().toString();
//
//		WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
//		String ip = wad.getRemoteAddress();
//
//		boolean bSuccess = false;
//		String errorMsg = "";
//		String encryptIdx = "";
//		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
//
//		KokonutUser user = null;
//		AuthUser authUser = null;
//
//		String loginType = "";
//		do {
//
//			user = loginService.SelectAdminByIdAndPassword(email, password);
//			if (user != null) {
//				String isLoginAuth = user.getIsLoginAuth();
//				if("N".equals(isLoginAuth)) {
//					errorMsg = "ERROR_11";
//					loginType= "GOOGLE OTP로 로그인을 하지 않음";
//					break;
//				}
//			}
//
//			if (user == null) {
//				KokonutUser emailUser = loginService.SelectAdminByEmailCount(email);
//				if(emailUser != null) {
//					encryptIdx = ariaUtil.Encrypt(String.valueOf(emailUser.getIdx()));
//					loginService.UpdatePwdErrorCountByIdx(emailUser.getIdx());
//					int pwdErrorCount = emailUser.getPwdErrorCount() + 1;
//					if(pwdErrorCount == 5) {
//						errorMsg = "ERROR_2";
//						break;
//					}
//
//					if(pwdErrorCount > 5) {
//						if (pwdErrorCount == 6) {
//							loginService.UpdateStateStopByIdx(emailUser.getIdx());
//
//							/* 사업자 또는 최고 관리자의 로그인 시도인 경우 모든 시스템 관리자에게 알림 */
//							String level = emailUser.getLevel();
//							String role = emailUser.getRoleName();
//
//							if("ROLE_MASTER".equals(role) && ("최고 관리자".equals(level) || "사업자".equals(level))) {
//
//								List<HashMap<String, Object>> systemAdminList = adminService.SelectSystemAdminList();
//								if(systemAdminList != null && systemAdminList.size() > 0) {
//									try {
//										String mailData = URLEncoder.encode(emailUser.getEmail(), "UTF-8");
//										String title = "사업자 회원가입 승인요청";
//										String contents = mailSender.getHTML2("/mail/emailForm/" + Integer.toString(MailController.EmailFormType.ABNORMAL_MASTER_OVER_LOGIN.ordinal()) + "?data=" + mailData);
//
//										for(HashMap<String, Object> systemAdmin : systemAdminList) {
//											if(systemAdmin.containsKey("EMAIL") && systemAdmin.containsKey("NAME")) {
//												String toEmail = systemAdmin.get("EMAIL").toString();
//												String toName = systemAdmin.get("NAME").toString();
//
//												mailSender.sendMail(toEmail, toName, title, contents);
//											} else {
//												logger.error("not found system admin info.");
//											}
//										}
//									} catch (IOException e) {
//										logger.error(e.getMessage());
//									}
//								} else {
//									logger.error("not found system admin list.");
//								}
//							}
//						}
//
//						errorMsg = "ERROR_3";
//						break;
//					}
//				}
//
//				errorMsg = "ERROR_1";
//				loginType= "존재하지 않는 계정";
//				break;
//			}
//
//			encryptIdx = ariaUtil.Encrypt(String.valueOf(user.getIdx()));
//			String state = user.getState();
//			if("0".equals(state)) {
//				errorMsg = "ERROR_4";
//				loginType= "정지된 계정";
//				break;
//			}
//
//			if("3".equals(state)) {
//				errorMsg = "ERROR_5";
//				loginType= "탈퇴한 계정";
//				break;
//			}
//
//			if("4".equals(state)) {
//				errorMsg = "ERROR_6";
//				loginType= "휴면 계정";
//				break;
//			}
//
//			String isEmailAuth = user.getIsEmailAuth();
//			if("N".equals(isEmailAuth)) {
//				errorMsg = "ERROR_8";
//				loginType= "인증하지 않은 계정";
//				break;
//			}
//
//			String approvalState = user.getApprovalState();
//			if (KokonutUser.ROLE_MASTER.equals(user.getRoleName()) && !"2".equals(approvalState) ) {
//				errorMsg = "ERROR_7";
//				loginType= "승인되지 않은 계정";
//				break;
//			}
//
//			int companyIdx = user.getCompanyIdx();
//			if (KokonutUser.ROLE_ADMIN.equals(user.getRoleName()) && companyIdx == 0) {
//				errorMsg = "ERROR_9";
//				loginType= "회사 정보가 없는 계정";
//				break;
//			}
//
//			/* 해외 아이피 차단 여부 */
//			HashMap<String, Object> setting = settingService.SelectSettingByCompanyIdx(companyIdx);
//			if(setting != null && !setting.isEmpty()) {
//				String overseasBlock = setting.get("OVERSEAS_BLOCK").toString();
//				if("1".equals(overseasBlock)) {
//					HashMap<String, Object> ipInfo = ipInfoService.GetIpInfo(ip);
//					if(ipInfo.get("country")!= null && !"".equals("country") && !ipInfo.get("country").toString().equals("KR")){
//						errorMsg = "ERROR_10";
//						loginType= "해외 아이피 차단";
//
//						/* 시스템 관리자 계정으로 해외 아이피 접속 시 모든 시스템 관리자에게 이메일 알림 */
//						if("ROLE_SYSTEM".equals(user.getRoleName())) {
//							List<HashMap<String, Object>> systemAdminList = adminService.SelectSystemAdminList();
//							if(systemAdminList != null && systemAdminList.size() > 0) {
//								try {
//									String mailData = URLEncoder.encode(user.getEmail(), "UTF-8");
//									String title = "해외 접속 이상행위 감지";
//									String contents = mailSender.getHTML2("/mail/emailForm/" + Integer.toString(MailController.EmailFormType.ABNORMAL_SYSTEM_OVERSEA_LOGIN.ordinal()) + "?data=" + mailData);
//
//									for(HashMap<String, Object> systemAdmin : systemAdminList) {
//										if(systemAdmin.containsKey("EMAIL") && systemAdmin.containsKey("NAME")) {
//											String toEmail = systemAdmin.get("EMAIL").toString();
//											String toName = systemAdmin.get("NAME").toString();
//
//											mailSender.sendMail(toEmail, toName, title, contents);
//										} else {
//											logger.error("not found system admin info.");
//										}
//									}
//								} catch (IOException e) {
//									logger.error(e.getMessage());
//								}
//							} else {
//								logger.error("not found system admin list.");
//							}
//						}
//
//                        /* 해외 아이피 차단 설정시, 해외 아이피로 접속하면 활동이력 추가(비정상, 사유) */
//                        if("ROLE_MASTER".equals(user.getRoleName()) || "ROLE_ADMIN".equals(user.getRoleName())){
//                            int adminIdx = user.getIdx();
//                            String reason = "해외에서 로그인 시도";
//                            activityHistoryService.InsertActivityHistory(2, companyIdx, adminIdx, 1, "", reason, CommonUtil.clientIp(), 2);
//                        }
//
//						break;
//					}
//				}
//			}
//
//			if (KokonutUser.ROLE_SYSTEM.equals(user.getRoleName())) {
//				grantedAuths.add(new SimpleGrantedAuthority(KokonutUser.ROLE_SYSTEM));
//			}
//			else if (KokonutUser.ROLE_MASTER.equals(user.getRoleName())) {
//				grantedAuths.add(new SimpleGrantedAuthority(KokonutUser.ROLE_MASTER));
//			}
//			else {
//				grantedAuths.add(new SimpleGrantedAuthority(KokonutUser.ROLE_ADMIN));
//			}
//
//			// 메뉴별 권한 추가
//			if(KokonutUser.ROLE_MASTER.equals(user.getRoleName()) || KokonutUser.ROLE_ADMIN.equals(user.getRoleName())){
//				Integer adminLevelIdx = user.getAdminLevelIdx();
//				if(adminLevelIdx != null) {
//					HashMap<String, Object> adminLevel = adminLevelService.SelectAdminLevelByIdx(adminLevelIdx);
//					for (int i = 1; i < 13; i++) {
//						String menu = adminLevel.get("MENU_"+i).toString();
//						if("1".equals(menu)) {
//							grantedAuths.add(new SimpleGrantedAuthority("ROLE_MENU_"+i));
//						}
//					}
//				}
//			}
//
//			// API TEST KEY 만료 체크
//			HashMap<String, Object> apiKey = apiKeyService.SelectTestApiKeyByCompanyIdx(companyIdx);
//			if(apiKey != null && !apiKey.isEmpty()) {
//				String validityStart = apiKey.get("VALIDITY_START").toString();
//				String validityEnd = apiKey.get("VALIDITY_END").toString();
//
//				user.setValidityStart(validityStart);
//				user.setValidityEnd(validityEnd);
//			}
//
//			adminService.UpdateIsLoginAuth(user.getIdx(), "N");
//			authUser = new AuthUser(user);
//			bSuccess = true;
//
//
//		} while (false);
//
//
//		if (bSuccess) {
//			return new UsernamePasswordAuthenticationToken(authUser, password, grantedAuths);
//		}
//		else {
///*			userMap.put("userIdx", -1);
//			userMap.put("userId", email);
//			userMap.put("loginIp", userIPAddress);
//			userMap.put("loginType", loginType);
//			loginService.InsertLoginHistory(userMap);
//*/
//			HashMap<String, Object> failMap = new HashMap<String, Object>();
//			failMap.put("loginId", email);
//			failMap.put("encryptIdx", encryptIdx);
//			throw new BadCredentialsException(errorMsg, failMap);
//		}
//
//	}
//
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}
//}
