package com.app.kokonut.refactor.personalInfoDownloadHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "personal_info_download_history")
public class PersonalInfoDownloadHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 키
     */
    @Id
    @ApiModelProperty("키")
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    /**
     * personal_info_provision 고유번호
     */
    @Column(name = "NUMBER")
    @ApiModelProperty("personal_info_provision 고유번호")
    private String number;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

    /**
     * 보유기간 만료일
     */
    @ApiModelProperty("보유기간 만료일")
    @Column(name = "RETENTION_DATE", nullable = false)
    private Date retentionDate;

    /**
     * 이메일
     */
    @ApiModelProperty("이메일")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    /**
     * 파일명
     */
    @ApiModelProperty("파일명")
    @Column(name = "FILE_NAME", nullable = false)
    private String fileName;

    /**
     * 주의사항 동의여부 (Y/N)
     */
    @ApiModelProperty("주의사항 동의여부 (Y/N)")
    @Column(name = "AGREE_YN", nullable = false)
    private String agreeYn;

    /**
     * 정보제공 파기 파일그룹 아이디
     */
    @ApiModelProperty("정보제공 파기 파일그룹 아이디")
    @Column(name = "DESTRUCTION_FILE_GROUP_ID")
    private String destructionFileGroupId;

    /**
     * 정보제공 파기 주의사항 동의여부 (Y/N)
     */
    @ApiModelProperty("정보제공 파기 주의사항 동의여부 (Y/N)")
    @Column(name = "DESTRUCTION_AGREE_YN", nullable = false)
    private String destructionAgreeYn;

    /**
     * 정보제공 파기 최근 등록일
     */
    @Column(name = "DESTRUCTION_DATE")
    @ApiModelProperty("정보제공 파기 최근 등록일")
    private Date destructionDate;

    /**
     * 정보제공 파기 등록자
     */
    @ApiModelProperty("정보제공 파기 등록자")
    @Column(name = "DESTRUNCTION_REGISTER_NAME")
    private String destrunctionRegisterName;

}
