package com.acme.edu.client;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.InvalidMessageException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Client-side pre-processing message
 */


public class MessagePreprocessor {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now;
    private String decorate(String message, String delta) {
        now = LocalDateTime.now();
        return delta + dtf.format(now) + " " + message.substring(delta.length());
    }

    private void filterLen(String message) {
        if (message.length() > 150) {
            ExceptionLogger.logExceptionWithInfo(
                    "Message length is over 150 characters", new InvalidMessageException());
        }
    }

    private boolean checkIfCorrectCommand(String message) {
        return message.startsWith("/chid ") || "/hist".equals(message) ||
                "/exit".equals(message) || message.startsWith("/chroom ") || message.startsWith("/sdnp");
    }

    public String getFilteredMessage(String message) throws InvalidMessageException {
        if (message.startsWith("/snd ")) {
            filterLen(message.substring(5));
            return decorate(message, "/snd ");
        }
        if (message.startsWith("/sdnp ")) {
            filterLen(message.substring(6));
            return decorate(message, "/sdnp ");
        }

        if (checkIfCorrectCommand(message)) {
            return message;
        } else {
            throw new InvalidMessageException("Invalid command!");
        }
    }

    public boolean isExitCommand(String command) {
        return "/exit".trim().equals(command);
    }
}