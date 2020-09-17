package com.acme.edu.exception;

import java.io.IOException;

public class ClientException extends Exception {

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
