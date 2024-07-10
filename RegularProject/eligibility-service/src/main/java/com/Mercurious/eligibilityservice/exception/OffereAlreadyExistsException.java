package com.Mercurious.eligibilityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OffereAlreadyExistsException extends RuntimeException {

    public OffereAlreadyExistsException(String message) {
        super(message);
    }

}