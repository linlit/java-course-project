package com.acme.edu.exception;

import java.io.IOException;

public class SendMessageException extends ChatException {
    public SendMessageException(String s, IOException e) {
        super(s, e);
    }
}
