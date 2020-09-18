package com.acme.edu.client;

import com.acme.edu.exception.InvalidMessageException;

import java.time.LocalDateTime;

/**
 * Client-side pre-processing message
 */
public class MessagePreprocessor {
    private String decorate(String message) {
        return "/snd " + LocalDateTime.now() + " " + message.substring(5);
    }

    private void filterLen(String message) throws InvalidMessageException {
        if (message.length() > 150) {
            System.out.println("Message length is over 150 characters");
            throw new InvalidMessageException();
        }
    }

    private boolean checkIfCorrectCommand(String message) {
        return message.startsWith("/chid ") || "/hist".equals(message) ||
                "/exit".equals(message) || message.startsWith("/chroom ");
    }

    public String getFilteredMessage(String message) throws InvalidMessageException {
        if (message.startsWith("/snd ")) {
            filterLen(message.substring(5));
            return decorate(message);
        }

        if (checkIfCorrectCommand(message)) {
            return message;
        } else {
            System.out.println("Unknown command");
            throw new InvalidMessageException();
        }
    }

    public boolean isExitCommand(String command) {
        return "/exit".trim().equals(command);
    }
}