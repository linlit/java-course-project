package com.acme.edu.exception;

public class InvalidMessageException extends ChatException {
    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Exception cause) {
        super(message, cause);
    }
}
