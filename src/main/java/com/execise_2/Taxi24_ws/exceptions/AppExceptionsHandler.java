package com.execise_2.Taxi24_ws.exceptions;

import com.execise_2.Taxi24_ws.exceptions.Taxi24ServiceException;
import com.execise_2.Taxi24_ws.ui.model.response.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler(value = {Taxi24ServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(Taxi24ServiceException ex, WebRequest req){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest req){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
