package com.Mercurius.accountservice.exception;



public class OfferAlreadyExistsException extends RuntimeException {

    public OfferAlreadyExistsException(String message) {
        super(message);
    }

}