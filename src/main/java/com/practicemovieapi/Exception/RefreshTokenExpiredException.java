package com.practicemovieapi.Exception;

public class RefreshTokenExpiredException extends RuntimeException{

    public RefreshTokenExpiredException(String message){
        super(message);
    }
}
