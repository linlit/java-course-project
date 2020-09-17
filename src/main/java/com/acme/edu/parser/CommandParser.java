package com.acme.edu.parser;

public class CommandParser {

    public static String parse(String message) {
        String parsedMessage;
        if (message.startsWith("/snd ")) parsedMessage = message.substring(5);
        if (message.startsWith("/hist")) parsedMessage = message;
        // TODO: /quite - > exseption? or state?¡
        return "";
    }
}
