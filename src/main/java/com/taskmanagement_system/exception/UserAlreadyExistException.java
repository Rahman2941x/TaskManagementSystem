package com.taskmanagement_system.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String string) {
        super(string);
    }
}
