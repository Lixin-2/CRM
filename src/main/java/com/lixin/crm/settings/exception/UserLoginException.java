package com.lixin.crm.settings.exception;

public class UserLoginException extends RuntimeException{
    public UserLoginException() {
    }

    public UserLoginException(String message) {
        super(message);
    }
}
