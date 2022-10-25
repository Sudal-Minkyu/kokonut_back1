package com.app.kokonut.privacyEmailHistory.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "privacy_email_history")
public class PrivacyEmailHistory implements Serializable {

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
     * privacy_email 키
     */
    @ApiModelProperty("privacy_email 키")
    @Column(name = "PRIVACY_EMAIL_IDX", nullable = false)
    private Integer privacyEmailIdx;

    /**
     * 받는 사람 이메일
     */
    @ApiModelProperty("받는 사람 이메일")
    @Column(name = "RECEIVER_EMAIL", nullable = false)
    private String receiverEmail;

    /**
     * 발송일
     */
    @ApiModelProperty("발송일")
    @Column(name = "SEND_DATE", nullable = false)
    private Date sendDate;

}
