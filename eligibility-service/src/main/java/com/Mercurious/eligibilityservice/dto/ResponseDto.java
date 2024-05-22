package com.Mercurious.eligibilityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
//@Data
public class ResponseDto {
	private String statusCode;

    private String statusMsg;

	public ResponseDto(String statusCode, String statusMsg) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public ResponseDto() {
		super();
	
	}
    

}
