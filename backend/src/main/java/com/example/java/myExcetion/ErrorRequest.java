package com.example.java.myExcetion;

import jakarta.validation.constraints.NotNull;

public class ErrorRequest extends RuntimeException{
    public ErrorRequest(@NotNull String message){
        super(message);
    }
}
