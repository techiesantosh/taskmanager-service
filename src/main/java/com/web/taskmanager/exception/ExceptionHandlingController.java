package com.web.taskmanager.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> invalidInput(MethodArgumentNotValidException ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "Validation Error", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDetails> invalidTask(TaskNotFoundException ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "Invalid taskId", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> invalidCredentials(AuthenticationException ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "Invalid Credentials", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.BAD_REQUEST);
    }
}