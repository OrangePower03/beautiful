package com.example.java.myExcetion;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;

public class RegisterException extends RuntimeException {
    private BindingResult check;

    public BindingResult getCheck() {
        return check;
    }

    public RegisterException(@NotNull String message){
        super(message);
    }
    public RegisterException(@NotNull String message,BindingResult check){
        super(message);
        this.check=check;
    }
}
