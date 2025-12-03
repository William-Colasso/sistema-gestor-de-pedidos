package com.tecdes.lanchonete.exception;

public class InvalidDataException extends ValidationException {
    public InvalidDataException(String message){
        super(message);
    }
}
