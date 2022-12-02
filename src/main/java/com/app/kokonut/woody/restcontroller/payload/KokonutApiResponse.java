package com.app.kokonut.woody.restcontroller.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="코코넛 API Response Body")
public class KokonutApiResponse {
	@ApiModelProperty(value="성공여부", example="true")
	private Boolean success;
	
	@ApiModelProperty(value="에러 메시지", example="error message")
	private String error;
	
	public KokonutApiResponse() {
		this.success = true;
		this.error = "";
	}
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
