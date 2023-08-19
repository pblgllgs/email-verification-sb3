package com.pblgllgs.emailverificationsb3.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleException(
            UserAlreadyExistsException e,
            HttpServletRequest request
    ){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenNotSendException.class)
    public ResponseEntity<ApiError> handleException(
            TokenNotSendException e,
            HttpServletRequest request
    ){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
