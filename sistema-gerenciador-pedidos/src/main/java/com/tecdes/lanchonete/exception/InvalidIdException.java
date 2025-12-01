package com.tecdes.lanchonete.exception;

public class InvalidIdException extends ValidationException{
    public InvalidIdException(String message){
        super(message);
    }
}
