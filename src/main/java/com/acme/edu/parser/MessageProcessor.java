package com.acme.edu.parser;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.reactors.*;

/*
 * Creates command pattern for reacting on messages.
 */
public class MessageProcessor {
    public CommandReactor parse(String clientMessage, User user, ChatObserver observer) throws InvalidMessageException {
        String userName = user.getUserName();

        if (clientMessage.startsWith("/hist")) {
            return new HistoryReactor(user, observer);
        } else if (clientMessage.startsWith("/snd")) {
            String preparedMessage = (userName != null ? userName + ": " : "")
                    + clientMessage.split("/snd")[1];
            return new SendReactor(preparedMessage, observer);
        } else if (clientMessage.trim().equals("/exit")) {
            return new ExitReactor(user, observer);
        } else if (clientMessage.startsWith("/chid")) {
            return new AuthReactor(clientMessage.split("/chid")[1], user);
        } else {
            throw new InvalidMessageException("Wrong format of message: " + clientMessage);
        }
    }
}
