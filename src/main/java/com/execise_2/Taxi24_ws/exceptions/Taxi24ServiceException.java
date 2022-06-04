package com.execise_2.Taxi24_ws.exceptions;

public class Taxi24ServiceException extends RuntimeException{
    private static final long serialVersionUID = 9162518725984564932L;

    public Taxi24ServiceException(String message){
        super(message);
    }
}
