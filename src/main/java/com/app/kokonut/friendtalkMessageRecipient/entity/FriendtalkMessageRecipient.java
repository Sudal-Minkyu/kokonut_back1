package com.app.kokonut.friendtalkMessageRecipient.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "friendtalk_message_recipient")
public class FriendtalkMessageRecipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "IDX", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(name = "FRIENDTALK_MESSAGE_IDX")
    private Integer friendtalkMessageIdx;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

}
