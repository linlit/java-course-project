package com.acme.edu.Client;

import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.CommandParser;

import java.time.LocalDateTime;

public class MessageManager {
    private String decorate(String message) {
        return "/snd " + LocalDateTime.now() + " " + message.substring(5);
    }

    public String getFilteredMessage(String message) throws InvalidMessageException {
        if (message.startsWith("/snd ")) {
            filterLen(message.substring(5));
            return decorate(message);
        }
        if (("/hist".equals(message)) || (("/exit".equals(message)))) return message;
        throw new InvalidMessageException("Unknown command");
    }

    private void filterLen(String message) throws InvalidMessageException {
        if (message.length() > 150) throw new InvalidMessageException("Message length is over 150 characters");
    }


}



// TODO spec if over -> ? cut or rewrite