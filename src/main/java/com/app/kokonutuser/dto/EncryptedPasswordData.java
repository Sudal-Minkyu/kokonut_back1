package com.app.kokonutuser.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptedPasswordData {

    private String encryptedPassword;
    private String salt;

    public EncryptedPasswordData(String encryptedPassword, String salt) {
        this.encryptedPassword = encryptedPassword;
        this.salt = salt;
    }

}
