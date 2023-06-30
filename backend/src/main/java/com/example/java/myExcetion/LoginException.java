package com.example.java.myExcetion;

public class LoginException extends RuntimeException{
    public LoginException(String message){
        super(message);
    }
}
