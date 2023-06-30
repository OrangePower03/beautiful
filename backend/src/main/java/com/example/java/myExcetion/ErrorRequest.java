package com.example.java.myExcetion;

public class ErrorRequest extends RuntimeException{
    public ErrorRequest(String message){
        super(message);
    }
}
