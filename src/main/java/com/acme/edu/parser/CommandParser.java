package com.acme.edu.parser;

import com.acme.edu.exception.InvalidMessageException;

public class CommandParser {
     private MessageType messageType;
     private String message;

    public void parse(String clientMessage) throws InvalidMessageException {
        if (clientMessage.trim().equals("/start")) {
            messageType = MessageType.SUBSCRIBE;
            message = "Welcome to the chat!";
        } else if (clientMessage.startsWith("/hist")) {
            messageType = MessageType.HISTORY;
            message = "Here is the chat history!";
        } else if (clientMessage.startsWith("/snd")) {
            messageType = MessageType.SEND;
            message = clientMessage.split("/snd")[1];
        } else if (clientMessage.trim().equals("/exit")) {
            messageType = MessageType.UNSUBSCRIBE;
            message = "We will be glad to see you again!";
        } else {
            throw new InvalidMessageException("wrong format of message: " + clientMessage);
        }
    }

    public  MessageType getMessageType() {
        return messageType;
    }

    public  String getMessage() {
        return message;
    }
}
