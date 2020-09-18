package com.acme.edu.exception;

/**
 * Exception thrown when message type is invalid for any reason.
 */
public class InvalidMessageException extends ChatException {
    public InvalidMessageException() {
        super();
    }

    public InvalidMessageException(String message) {
        super(message);
    }
}
