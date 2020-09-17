package com.acme.edu.client;

import com.acme.edu.exception.InvalidMessageException;

import java.time.LocalDateTime;

/*
 * Client-side pre-processing message
 */
public class MessageManager {
    private String decorate(String message) {
        return "/snd " + LocalDateTime.now() + " " + message.substring(5);
    }

    public String getFilteredMessage(String message) throws InvalidMessageException {
        if (message.startsWith("/snd ")) {
            filterLen(message.substring(5));
            return decorate(message);
        } return message;
    }

    private void filterLen(String message) throws InvalidMessageException {
        if (message.length() > 150) throw new InvalidMessageException("Message length is over 150 characters");
    }
}