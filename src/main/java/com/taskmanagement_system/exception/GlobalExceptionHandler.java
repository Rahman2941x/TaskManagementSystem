package com.taskmanagement_system.exception;

import com.taskmanagement_system.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handlerUserNotFound(UserNotFoundException ex, HttpServletRequest request){
        ResponseDTO<String> responseDTO=new ResponseDTO<>(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ResponseDTO<String>> handleUserExist(UserAlreadyExistException ex,HttpServletRequest request){
        ResponseDTO<String> responseDTO= new ResponseDTO<>(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public  ResponseEntity<ResponseDTO<String>> handleIntegrityViolation(DataIntegrityViolationException ex,HttpServletRequest request){

        String message=ex.getMostSpecificCause().getMessage();
        if(message.contains("email")){
            message ="Email name should be unique";
        }else if (message.contains("number")){
            message="Number should be unique";
        }else message="Duplicate value or invalid one";

       ResponseDTO<String> responseDTO = new ResponseDTO<>(HttpStatus.CONFLICT,message,request.getRequestURI());
       return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseDTO<String>> handleInvalidCredential(InvalidCredentialsException ex,HttpServletRequest request){
        ResponseDTO<String> responseDTO= new ResponseDTO<>(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }



}
