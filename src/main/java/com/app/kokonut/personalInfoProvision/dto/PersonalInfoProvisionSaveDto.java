package com.app.kokonut.personalInfoProvision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoProvisionSaveDto {
	private Integer idx;
	private Integer companyIdx;
	private Integer adminIdx;
	private String number;
	private Integer reason;
	private Integer type;
	private Integer recipientType;
	private Character agreeYn;
	private Integer agreeType;
	private Date regdate;
	private String purpose;
	private String tag;
	private Date startDate;
	private Date expDate;
	private Integer period;
	private String retentionPeriod;
	private String columns;
	private String recipientEmail;
	private String targets;
	private String targetStatus;
}
