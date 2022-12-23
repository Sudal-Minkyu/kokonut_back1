package com.app.kokonut.joy.email;
public class RecipientForRequest {
    /* 이메일 전송을 위한 Dto 생성*/
    private String address;		//수신자 Email주소
    private String name;		//수신자 이름
    private String type;		//수신자 유형 (R: 수신자, C: 참조인, B: 숨은참조)

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RecipientForRequest [address=" + address + ", name=" + name + ", type=" + type + ", parameters=" + "]";
    }
}
