package com.example.demo.exceptions;

import com.example.demo.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request){

        String errorMassageDescription = ex.getLocalizedMessage();
        if (errorMassageDescription == null) errorMassageDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMassageDescription);

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );

    }
    @ExceptionHandler(value = {NullPointerException.class, UserServiceException.class})
    public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request){

        String errorMassageDescription = ex.getLocalizedMessage();
        if (errorMassageDescription == null) errorMassageDescription = ex.toString();
        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMassageDescription);

        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
