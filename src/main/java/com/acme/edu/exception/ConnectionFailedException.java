package com.acme.edu.exception;

public class ConnectionFailedException extends ChatException {
    public ConnectionFailedException(String s, Exception e) {
        super(s, e);
    }
}
