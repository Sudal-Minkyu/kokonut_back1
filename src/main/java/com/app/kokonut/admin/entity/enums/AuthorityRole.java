package com.app.kokonut.admin.entity.enums;

/**
 * @author Woody
 * Date : 2022-12-01
 * Time :
 * Remark : 사용자 권한 구분
 */
public enum AuthorityRole {

    ROLE_SYSTEM("ROLE_SYSTEM", "SYSTEM"),
    ROLE_ADMIN("ROLE_ADMIN", "ADMIN"),
    ROLE_MASTER("ROLE_MASTER", "MASTER"),
    ROLE_USER("ROLE_USER", "USER");

    private final String code;
    private final String desc;

    AuthorityRole(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
