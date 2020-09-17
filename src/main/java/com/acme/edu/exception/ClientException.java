package com.acme.edu.exception;

import java.io.IOException;

public class ClientException extends Exception {
    public ClientException(String s, IOException e) {
        super(s, e);
    }
}
