package com.app.kokonut.woody.been;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KokonutUser {
	
	private Logger logger = LoggerFactory.getLogger(KokonutUser.class);
	
	/** 기본 키 */
	private int idx;
	
	/** 기업정보 IDX */
	private int companyIdx;
	
	/** 마스터IDX(마스터는 0):관리자로 등록한 마스터의 키 */
	private int masterIdx;
	
	/** 회원타입(1:사업자, 2:개인) */
	private int userType;
	
	/** 이메일 */
	private String email;

	/** 비밀번호 */
	private String password;

	/** 비밀번호 변경 일자*/
	private String pwdChangeDate;
	
	/** 비밀번호 변경 현자일자 차이(개월)*/
	private int pwdChangeDiff;

	/** 이름(대표자명) */
	private String name;

	/** 휴대폰번호 */
	private String phoneNumber;

	/** 부서 */
	private String department;

	/** SYSTEM:0,나머지는 레벨 IDX */
	private Integer adminLevelIdx;

	/** 0:정지(권한해제), 1:사용, 2:로그인제한(비번5회오류), 3:탈퇴 */
	private String state;

	/** 휴면계정 전환 일자 */
	private String dormantDate;

	/** 계정 삭제 예정 일자 */
	private String expectedDeleteDate;
	
	/** 권한해제 사유 */
	private String reason;
	
	/** 비밀번호 오류 횟수 */
	private int pwdErrorCount;
	
	/** 등록일시 */
	private String regDate;

	/** 수정일시 */
	private String modifyDate;
	
	/** 권한 이름 (SYSTEM, ADMIN) */
	private String roleName;
	
	/** 탈퇴일시*/
	private String withdrawalDate;
	
	/** 최종 로그인 일시 */
	private String lastLoginDate;

	/**  최근 접속정보 (접속 IP) */
	private String ip;

	/** 승인상태(1:승인대기, 2:승인완료, 3:승인보류) */
	private String approvalState;
	
	/** 승인 일자 */
	private String approvalDate;

	/** 승인 반려 사유 */
	private String approvalReturnReason;

	/** 권한 이름 */
	private String level;
	
	/** 구글 OTP에 사용될 KEY */
	private String otpKey;
	
    /** API 메뉴얼 관리(0:사용안함,1:사용) */
    private String menu1;
    
    /** 관리자 관리(0:사용안함,1:사용) */
    private String menu2;
    
    /** 결제관리(0:사용안함,1:사용) */
    private String menu3;
    
    /** 회원관리(0:사용안함,1:사용) */
    private String menu4;
    
    /** 회원정보제공(0:사용안함,1:사용) */
    private String menu5;
    
    /** 회원정보 DB 관리(0:사용안함,1:사용) */
    private String menu6;
    
    /** 이력 관리(0:사용안함,1:사용) */
    private String menu7;
    
    /** 메시지 발송 관리(0:사용안함,1:사용) */
    private String menu8;
    
    /** 리포트 관리(0:사용안함,1:사용) */
    private String menu9;
    
    /** 개인정보처리방침 관리(0:사용안함,1:사용) */
    private String menu10;
    
    /** 설정(0:사용안함,1:사용) */
    private String menu11;
    
    /** 비즈 메시지(0:사용안함,1:사용) */
    private String menu12;
    
	/** 이메일 인증 여부 */
	private String isEmailAuth;

	/** 서비스 IDX */
	private String serviceIdx;
	
	/** 유효기간 시작일자 */
	private String validStart;

	/** 유효기간 종료일자*/
	private String validEnd;

	/** 테스트키 시작일자 */
	private String validityStart;
	
	/** 테스트키 종료 일자 */
	private String validityEnd;
	
	/** 구글 OTP 인증 여부 */
	private String isLoginAuth;
	
	public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_MASTER = "ROLE_MASTER";
	public static final String ROLE_USER = "ROLE_USER";

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public int getCompanyIdx() {
		return companyIdx;
	}

	public void setCompanyIdx(int companyIdx) {
		this.companyIdx = companyIdx;
	}

	public int getMasterIdx() {
		return masterIdx;
	}

	public void setMasterIdx(int masterIdx) {
		this.masterIdx = masterIdx;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPwdChangeDate() {
		return pwdChangeDate;
	}

	public void setPwdChangeDate(String pwdChangeDate) {
		this.pwdChangeDate = pwdChangeDate;
	}

	public int getPwdChangeDiff() {
		return pwdChangeDiff;
	}

	public void setPwdChangeDiff(int pwdChangeDiff) {
		this.pwdChangeDiff = pwdChangeDiff;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getAdminLevelIdx() {
		return adminLevelIdx;
	}

	public void setAdminLevelIdx(Integer adminLevelIdx) {
		this.adminLevelIdx = adminLevelIdx;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDormantDate() {
		return dormantDate;
	}

	public void setDormantDate(String dormantDate) {
		this.dormantDate = dormantDate;
	}

	public String getExpectedDeleteDate() {
		return expectedDeleteDate;
	}

	public void setExpectedDeleteDate(String expectedDeleteDate) {
		this.expectedDeleteDate = expectedDeleteDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getPwdErrorCount() {
		return pwdErrorCount;
	}

	public void setPwdErrorCount(int pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(String withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getApprovalState() {
		return approvalState;
	}

	public void setApprovalState(String approvalState) {
		this.approvalState = approvalState;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovalReturnReason() {
		return approvalReturnReason;
	}

	public void setApprovalReturnReason(String approvalReturnReason) {
		this.approvalReturnReason = approvalReturnReason;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getOtpKey() {
		return otpKey;
	}

	public void setOtpKey(String otpKey) {
		this.otpKey = otpKey;
	}

	public String getIsEmailAuth() {
		return isEmailAuth;
	}

	public void setIsEmailAuth(String isEmailAuth) {
		this.isEmailAuth = isEmailAuth;
	}

	public String getMenu1() {
		return menu1;
	}

	public void setMenu1(String menu1) {
		this.menu1 = menu1;
	}

	public String getMenu2() {
		return menu2;
	}

	public void setMenu2(String menu2) {
		this.menu2 = menu2;
	}

	public String getMenu3() {
		return menu3;
	}

	public void setMenu3(String menu3) {
		this.menu3 = menu3;
	}

	public String getMenu4() {
		return menu4;
	}

	public void setMenu4(String menu4) {
		this.menu4 = menu4;
	}

	public String getMenu5() {
		return menu5;
	}

	public void setMenu5(String menu5) {
		this.menu5 = menu5;
	}

	public String getMenu6() {
		return menu6;
	}

	public void setMenu6(String menu6) {
		this.menu6 = menu6;
	}

	public String getMenu7() {
		return menu7;
	}

	public void setMenu7(String menu7) {
		this.menu7 = menu7;
	}

	public String getMenu8() {
		return menu8;
	}

	public void setMenu8(String menu8) {
		this.menu8 = menu8;
	}

	public String getMenu9() {
		return menu9;
	}

	public void setMenu9(String menu9) {
		this.menu9 = menu9;
	}

	public String getMenu10() {
		return menu10;
	}

	public void setMenu10(String menu10) {
		this.menu10 = menu10;
	}

	public String getMenu11() {
		return menu11;
	}

	public void setMenu11(String menu11) {
		this.menu11 = menu11;
	}

	public String getMenu12() {
		return menu12;
	}

	public void setMenu12(String menu12) {
		this.menu12 = menu12;
	}
	
	public String getServiceIdx() {
		return serviceIdx;
	}

	public void setServiceIdx(String serviceIdx) {
		this.serviceIdx = serviceIdx;
	}

	public String getValidStart() {
		return validStart;
	}

	public void setValidStart(String validStart) {
		this.validStart = validStart;
	}

	public String getValidEnd() {
		return validEnd;
	}

	public void setValidEnd(String validEnd) {
		this.validEnd = validEnd;
	}

	public String getValidityStart() {
		return validityStart;
	}

	public void setValidityStart(String validityStart) {
		this.validityStart = validityStart;
	}

	public String getValidityEnd() {
		return validityEnd;
	}

	public void setValidityEnd(String validityEnd) {
		this.validityEnd = validityEnd;
	}
	
	public String getIsLoginAuth() {
		return isLoginAuth;
	}

	public void setIsLoginAuth(String isLoginAuth) {
		this.isLoginAuth = isLoginAuth;
	}

	/* 결제한 서비스가 유효한지 */
	public boolean serviceValid() {
		boolean serviceValid = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(validStart == null || "".equals(validStart) || validEnd == null ||"".equals(validStart)) {
				return serviceValid;
			}
			
			Date startDate = sdf.parse(validStart);
			Date endDate = sdf.parse(validEnd);
			Date date = new Date();
			
			if(date.after(startDate) && date.before(endDate)) {
				serviceValid = true;
			}
			
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
		return serviceValid;
	}
	
	/* API TEST KEY 또는 결제한 서비스가 유효한지 */
	public boolean valid() {
		boolean resultValid = false;
		boolean serviceValid = false;
		boolean testValid = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = new Date();
			if(validStart != null && !"".equals(validStart) && validEnd != null && !"".equals(validStart) ) {
				Date startDate = sdf.parse(validStart);
				Date endDate = sdf.parse(validEnd);
				
				if(date.after(startDate) && date.before(endDate)) {
					serviceValid = true;
				}
				
			}
			
			if(validityStart != null && !"".equals(validityStart) && validityEnd != null &&  !"".equals(validityEnd) ) {
				Date validityStartDate = sdf.parse(validityStart);
				Date validityEndDate = sdf.parse(validityEnd);
				if(date.after(validityStartDate) && date.before(validityEndDate)) {
					testValid = true;
				}
			}
			if(serviceValid || testValid) {
				resultValid = true;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		
		return resultValid;
	}
}
	