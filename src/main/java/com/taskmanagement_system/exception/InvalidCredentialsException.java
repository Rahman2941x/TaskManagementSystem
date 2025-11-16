package com.taskmanagement_system.exception;

public class InvalidCredentialsException extends RuntimeException{
    public  InvalidCredentialsException(String message){
        super(message);
    }
}
