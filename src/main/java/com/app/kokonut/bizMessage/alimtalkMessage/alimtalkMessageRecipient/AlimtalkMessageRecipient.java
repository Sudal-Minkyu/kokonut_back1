package com.app.kokonut.bizMessage.alimtalkMessage.alimtalkMessageRecipient;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "alimtalk_message_recipient")
public class AlimtalkMessageRecipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "ALIMTALK_MESSAGE_IDX")
    private Integer alimtalkMessageIdx;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
