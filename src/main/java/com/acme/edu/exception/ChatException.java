package com.acme.edu.exception;

public class ChatException extends Exception {
    public ChatException(String s, Exception e) {
        super(s, e);
    }
}
