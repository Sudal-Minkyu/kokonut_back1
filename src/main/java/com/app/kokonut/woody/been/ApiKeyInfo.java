package com.app.kokonut.woody.been;

import lombok.Data;

import java.util.Date;

/**
 * @author Woody
 * Date : 2022-10-24
 * Remark :
 */
@Data
public class ApiKeyInfo {
    public static final int TYPE_NORMAL = 1; 				// 일반
    public static final int TYPE_TEST = 2; 					// 테스트
    
    public static final int USE_ACCUMULATE_DELETE = 0; 		// 일괄삭제
    public static final int USE_ACCUMULATE_ACCUMULATE = 1;	// 지속사용
	
    public static final int STATE_NEW = 1;					// 신규
    public static final int STATE_REFRESHED = 2; 			// 재발급
    
	private Integer idx;
	private Integer companyIdx;
	private Integer adminIdx;
	private String registerName;
	private String key;
	private Date regdate;
	private Integer type;
	private String note;
	private Date validityStart;
	private Date validityEnd;
	private Integer useAccumulate;
	private Integer state;
	private Boolean useYn;
	private Integer modifierIdx;
	private String modifierName;
	private Date modifyDate;

}