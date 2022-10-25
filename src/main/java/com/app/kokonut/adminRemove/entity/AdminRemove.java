package com.app.kokonut.adminRemove.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "admin_remove")
public class AdminRemove implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ADMIN IDX
     */
    @Id
    @ApiModelProperty("ADMIN IDX")
    @Column(name = "ADMIN_IDX", nullable = false)
    private Integer adminIdx;

    /**
     * 이메일
     */
    @Column(name = "EMAIL")
    @ApiModelProperty("이메일")
    private String email;

    /**
     * 탈퇴 사유(1:계정변경, 2:서비스 이용 불만, 3:사용하지 않음, 4:기타)
     */
    @Column(name = "REASON")
    @ApiModelProperty("탈퇴 사유(1:계정변경, 2:서비스 이용 불만, 3:사용하지 않음, 4:기타)")
    private Integer reason;

    /**
     * 탈퇴 사유
     */
    @ApiModelProperty("탈퇴 사유")
    @Column(name = "REASON_DETAIL")
    private String reasonDetail;

    /**
     * 탈퇴일시
     */
    @ApiModelProperty("탈퇴일시")
    @Column(name = "REGDATE", nullable = false)
    private Date regdate;

}
