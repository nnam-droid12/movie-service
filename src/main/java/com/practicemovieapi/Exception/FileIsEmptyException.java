package com.practicemovieapi.Exception;

public class FileIsEmptyException extends RuntimeException{

    public FileIsEmptyException(String message){
        super(message);
    }
}
