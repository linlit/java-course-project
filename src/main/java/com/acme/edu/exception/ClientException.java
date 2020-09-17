package com.acme.edu.exception;

public class ClientException extends Exception {
    public ClientException() {
        super();
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
