package com.Mercurius.accountservice.exception;

public class AccountDataException extends RuntimeException {
    private String message;
    public AccountDataException(String s) {
        super(s);
        this.message=s;
    }
}
