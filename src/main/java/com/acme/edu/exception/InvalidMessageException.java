package com.acme.edu.exception;

public class InvalidMessageException extends ChatException {
    public InvalidMessageException(String message) {
        super(message);
    }
}
