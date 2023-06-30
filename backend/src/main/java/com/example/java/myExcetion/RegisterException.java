package com.example.java.myExcetion;

import org.springframework.validation.BindingResult;

public class RegisterException extends RuntimeException {
    private BindingResult check;

    public BindingResult getCheck() {
        return check;
    }

    public RegisterException(String message){
        super(message);
    }
    public RegisterException(String message,BindingResult check){
        super(message);
        this.check=check;
    }
}
