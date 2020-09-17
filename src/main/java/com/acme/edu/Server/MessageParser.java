package com.acme.edu.Server;

public class MessageParser {
    private MessageType messageType;
    private String message;

    public void parse(String clientMessage) throws MessageException {
        if(clientMessage.contains("/hist")){
            messageType = MessageType.GET_ALL;
        }
        else if(clientMessage.contains("/snd")){
            messageType = MessageType.SEND;
            message = clientMessage.split("/snd")[1];
        }
        else if(clientMessage.trim().equals("/exit")){
            messageType = MessageType.EXIT;
        }
        else{
            throw new MessageException("wrong format of message: " + clientMessage);
        }
    }
}
