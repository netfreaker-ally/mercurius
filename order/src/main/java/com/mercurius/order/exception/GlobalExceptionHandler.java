package com.mercurius.order.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.mercurius.order.dto.ErrorResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false), HttpStatus.NOT_FOUND,
				exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
		ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	 @ExceptionHandler(RuntimeException.class) 
	    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException exception,
	            WebRequest webRequest) {
	        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(webRequest.getDescription(false),
	                HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	 @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	    public ResponseEntity<ErrorResponseDto> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, WebRequest request) {
	        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(request.getDescription(false),
	                HttpStatus.NOT_ACCEPTABLE, "Requested media type not supported", LocalDateTime.now());
	        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_ACCEPTABLE);
	    }
}
