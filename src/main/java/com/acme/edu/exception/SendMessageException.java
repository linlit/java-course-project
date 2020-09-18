package com.acme.edu.exception;

import java.io.IOException;

/**
 * Exception thrown when message cannot be send for any reason.
 */
public class SendMessageException extends ChatException {
    public SendMessageException(String s, IOException e) {
        super(s, e);
    }
}
