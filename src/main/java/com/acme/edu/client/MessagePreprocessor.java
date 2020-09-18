package com.acme.edu.client;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.InvalidMessageException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Client-side pre-processing message
 */


public class MessagePreprocessor {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    private String decorate(String message, String delta) {
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

    public String getFilteredMessage(String message) {
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
            ExceptionLogger.logExceptionWithInfo("Unknown command", new InvalidMessageException());
            return "";
        }
    }

    public boolean isExitCommand(String command) {
        return "/exit".trim().equals(command);
    }
}