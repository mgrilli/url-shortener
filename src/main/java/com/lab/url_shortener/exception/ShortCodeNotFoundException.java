package com.lab.url_shortener.exception;

public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String message) {
        super(message);
    }
}
