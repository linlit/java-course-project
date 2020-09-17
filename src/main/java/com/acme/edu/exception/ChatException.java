package com.acme.edu.exception;

public class ChatException extends Exception {
    public ChatException(String s, Exception e) {
        super(s, e);
    }

    public ChatException() {
        super();
    }

    public ChatException(String s) {
        super(s);
    }


}
