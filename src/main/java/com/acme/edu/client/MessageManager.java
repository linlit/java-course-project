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

    private void filterLen(String message) throws InvalidMessageException {
        if (message.length() > 150) {
            System.out.println("Message length is over 150 characters");
            throw new InvalidMessageException("");
        }
    }

    public String getFilteredMessage(String message) throws InvalidMessageException {
        if (message.startsWith("/snd ")) {
            filterLen(message.substring(5));
            return decorate(message);
        }
        if (("/hist".equals(message))) {
            return message;
        }
        if ("/exit".equals(message)) {
            return message;
        }

        System.out.println("Unknown command");
        throw new InvalidMessageException();
    }

    public static boolean isExitCommand(String command) {
        return "/exit".trim().equals(command);
    }
}