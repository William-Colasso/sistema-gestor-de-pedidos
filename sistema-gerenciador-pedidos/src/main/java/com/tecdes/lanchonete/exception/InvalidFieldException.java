package com.tecdes.lanchonete.exception;

public class InvalidFieldException extends ValidationException {
    public InvalidFieldException(String message){
        super(message);
    }
}
