package com.Mercurious.productservice.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Data @AllArgsConstructor

public class ErrorResponseDto {

   
    private  String apiPath;

   
    private HttpStatus errorCode;

   
    private  String errorMessage;

   
    private LocalDateTime errorTime;


	public ErrorResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ErrorResponseDto(String apiPath, HttpStatus errorCode, String errorMessage, LocalDateTime errorTime) {
		super();
		this.apiPath = apiPath;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorTime = errorTime;
	}
    

}
