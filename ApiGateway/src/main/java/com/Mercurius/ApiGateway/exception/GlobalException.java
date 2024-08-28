package com.Mercurius.ApiGateway.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.ws.rs.NotFoundException;



@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(NotFoundException exception,
			WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,
				exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}
}
