package com.example.java.myExcetion;


import jakarta.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;

public class AddArtworkException extends RuntimeException{
    private BindingResult check;

    public BindingResult getCheck() {
        return check;
    }

    public AddArtworkException(@NotNull String message){
        super(message);
    }

    public AddArtworkException(@NotNull String message, BindingResult check){
        super(message);
        this.check=check;
    }
}
