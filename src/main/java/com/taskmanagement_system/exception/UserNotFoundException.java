package com.taskmanagement_system.exception;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String string) {
        super(string);
    }
    public UserNotFoundException(){

    }
}
