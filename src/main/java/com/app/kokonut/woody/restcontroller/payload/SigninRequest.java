package com.app.kokonut.woody.restcontroller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value="로그인 요청을 위한 Request Body")
public class SigninRequest {
	@ApiModelProperty(value="아이디", required=true, example="id")
	private @NotBlank String id;
	
	@ApiModelProperty(value="패스워드", required=true, example="password")
	private @NotBlank String pw;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
}
