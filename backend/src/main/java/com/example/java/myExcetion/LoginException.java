package com.example.java.myExcetion;

import jakarta.validation.constraints.NotNull;

public class LoginException extends RuntimeException{
    public LoginException(@NotNull String message){
        super(message);
    }
}
