package com.duoc.gestionpedidos.exception;

public class S3FileException extends RuntimeException{
    
    public S3FileException(String message){
        super(message);
    }

    public S3FileException(String message, Throwable cause){
        super(message, cause);
    }

}
