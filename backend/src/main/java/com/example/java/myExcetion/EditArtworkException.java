package com.example.java.myExcetion;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.BindingResult;

public class EditArtworkException extends RuntimeException{
    private BindingResult check;
    public EditArtworkException(@NotNull String message){
        super(message);
    }

    public EditArtworkException(@NotNull String message,BindingResult check){
        super(message);
        this.check=check;
    }

    public BindingResult getCheck() {
        return check;
    }
}
