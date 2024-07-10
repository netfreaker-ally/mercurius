package com.mercurius.keyAuth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ClientAlreadyExistsException extends RuntimeException {

	public ClientAlreadyExistsException(String message) {
		super(message);
	}

}
