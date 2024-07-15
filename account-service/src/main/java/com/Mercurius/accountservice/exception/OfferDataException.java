package com.Mercurius.accountservice.exception;



public class OfferDataException extends RuntimeException {
    private String message;
    public OfferDataException(String s) {
        super(s);
        this.message=s;
    }
}
