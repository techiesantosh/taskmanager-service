package com.web.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> userAlreadyExists(UserAlreadyExistsException ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "User Already exists", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> invalidCredentials(AuthenticationException ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "Invalid Credentials", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> invalidCredentials(Exception ex) {
        ErrorDetails response = new ErrorDetails(new Date(), "Service id down. Please try after some time", ex.getMessage());

        return new ResponseEntity<ErrorDetails>(response, HttpStatus.SERVICE_UNAVAILABLE);

    }


}