package com.practicemovieapi.Exception;

public class FileExistException extends RuntimeException{
    public FileExistException(String message){
        super(message);
    }
}
