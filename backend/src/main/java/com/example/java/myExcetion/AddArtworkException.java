package com.example.java.myExcetion;


import org.springframework.validation.BindingResult;

public class AddArtworkException extends RuntimeException{
    private BindingResult check;

    public BindingResult getCheck() {
        return check;
    }

    public AddArtworkException(String message){
        super(message);
    }

    public AddArtworkException(String message, BindingResult check){
        super(message);
        this.check=check;
    }
}
