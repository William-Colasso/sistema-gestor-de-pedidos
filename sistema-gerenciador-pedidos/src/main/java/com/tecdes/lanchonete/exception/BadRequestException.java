package com.tecdes.lanchonete.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String message){
        super(message);
    }
}
