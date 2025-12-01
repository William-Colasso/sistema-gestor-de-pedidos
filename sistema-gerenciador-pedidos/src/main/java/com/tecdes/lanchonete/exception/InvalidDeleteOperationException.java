package com.tecdes.lanchonete.exception;

public class InvalidDeleteOperationException extends ValidationException {
    public InvalidDeleteOperationException(String message){
        super(message);
    }
}
