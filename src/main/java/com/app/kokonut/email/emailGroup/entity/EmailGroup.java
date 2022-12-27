package com.app.kokonut.email.emailGroup.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email_group")
public class EmailGroup implements Serializable {

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
     * 관리자 키(문자열, 구분자: ',')
     */
    @ApiModelProperty("관리자 키(문자열, 구분자: ',')")
    @Column(name = "ADMIN_IDX_LIST", nullable = false)
    private String adminIdxList;

    /**
     * 그룹명
     */
    @ApiModelProperty("그룹명")
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * 그룹설명
     */
    @ApiModelProperty("그룹설명")
    @Column(name = "DESC", nullable = false)
    private String desc;

    /**
     * 등록일
     */
    @ApiModelProperty("등록일")
    @Column(name = "REGDATE", nullable = false)
    private LocalDateTime regdate;

    /**
     * 사용여부
     */
    @ApiModelProperty("사용여부")
    @Column(name = "USE_YN", nullable = false)
    private String useYn;

    /**
     * 관리자 이메일 목록 (문자열, 구분자: ',')
     * 영속성 제외, db 컬럼 아님.
     * 엔티티 매핑 무시
     */
    @Transient
    private String adminEmailList;

}
