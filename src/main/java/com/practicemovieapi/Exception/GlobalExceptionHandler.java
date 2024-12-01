package com.practicemovieapi.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler(FileExistException.class)
    public ProblemDetail FileExistException(FileExistException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(FileIsEmptyException.class)
    public ProblemDetail FileIsEmptyException(FileIsEmptyException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail MovieNotFoundException(MovieNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ProblemDetail RefreshTokenExpiredException(RefreshTokenExpiredException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ProblemDetail RefreshTokenNotFoundException(RefreshTokenNotFoundException ex){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
