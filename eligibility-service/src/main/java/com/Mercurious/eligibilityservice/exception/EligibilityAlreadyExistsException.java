package com.Mercurious.eligibilityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EligibilityAlreadyExistsException extends RuntimeException {

    public EligibilityAlreadyExistsException(String message) {
        super(message);
    }

}