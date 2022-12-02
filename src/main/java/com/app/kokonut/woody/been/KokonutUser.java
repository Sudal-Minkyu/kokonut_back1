package com.app.kokonut.woody.been;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Slf4j
public class KokonutUser {

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

	/* 결제한 서비스가 유효한지 */
	public boolean serviceValid() {
		boolean serviceValid = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(validStart == null || "".equals(validStart) || validEnd == null) {
				return false;
			}
			
			Date startDate = sdf.parse(validStart);
			Date endDate = sdf.parse(validEnd);
			Date date = new Date();
			
			if(date.after(startDate) && date.before(endDate)) {
				serviceValid = true;
			}
			
		} catch (ParseException e) {
			log.info("예외발생 e : "+e);
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
			if(validStart != null && !"".equals(validStart) && validEnd != null) {
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
			log.info("예외발생 e : "+e);
		}
		
		return resultValid;
	}
}
	