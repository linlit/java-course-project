package com.acme.edu.Client;

import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.CommandParser;

public class MessageManager {
    public MessageManager() {
    }

    public String decorate(String message) throws InvalidMessageException{
        message = CommandParser.parse(message)
        filterLen(message);
        return message;
    }

    private String parse(String message) { // /snd /hist /quit
        message.contentEquals()
    }

    public void filterLen(String message) throws InvalidMessageException {
        if (message.length() > 150) throw new InvalidMessageException("Message length is over 150 characters");
    }


}



// TODO spec if over -> ? cut or rewrite