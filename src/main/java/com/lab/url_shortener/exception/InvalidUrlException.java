package com.lab.url_shortener.exception;

public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String message){
        super(message);
    }
}
