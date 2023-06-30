package com.example.java.myExcetion;

import org.springframework.validation.BindingResult;

public class EditArtworkException extends RuntimeException{
    private BindingResult check;
    public EditArtworkException(String message){
        super(message);
    }

    public EditArtworkException(String message,BindingResult check){
        super(message);
        this.check=check;
    }

    public BindingResult getCheck() {
        return check;
    }
}
