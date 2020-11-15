package com.lixin.crm.workbench.exception;

public class CustomerException extends RuntimeException{
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
