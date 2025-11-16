package com.taskmanagement_system.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseDTO<T> {
   private LocalDateTime localDateTime;
   private int status;
    private  T message;
    private String path;


    public ResponseDTO(HttpStatus status, T message, String path) {
        this.localDateTime = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
        this.path = path;
    }
    public ResponseDTO(HttpStatus status, T message) {
        this.localDateTime = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
    }



    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "localDateTime=" + localDateTime +
                ", status=" + status +
                ", message=" + message +
                ", path='" + path + '\'' +
                '}';
    }
}
