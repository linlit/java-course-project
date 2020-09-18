package com.acme.edu.parser;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.reactors.*;

/**
 * Creates command pattern for reacting on messages.
 */
public class MessageProcessor {
    /**
     * Parses user message and decides which Command Reactor should be called.
     *
     * @param message String that was sent from a client
     * @param user User that invoked command by sending message
     * @param observer ChatObserver that supports subscribing, unsubscribing from current chat
     * @return CommandReactor for performing specific reaction after command parsing
     * @throws InvalidMessageException when message has wrong
     */
    public CommandReactor parse(String message, User user, ChatObserver observer) throws InvalidMessageException {
        if (message.startsWith("/hist")) {
            return new HistoryReactor(user, observer);
        } else if (message.startsWith("/snd ")) {
            String userName = user.getUserName();
            String preparedMessage = (userName != null ? userName + ": " : "") + message.split("/snd ")[1];
            return new SendReactor(preparedMessage, observer, user);
        } else if (message.trim().equals("/exit")) {
            return new ExitReactor(user, observer);
        } else if (message.startsWith("/chid ")) {
            return new AuthenticationReactor(message.split("/chid ")[1], user);
        } else if (message.startsWith("/chroom ")) {
            return new ChangeRoomReactor(message.split("/chroom ")[1], observer, user);
        } else {
            throw new InvalidMessageException("Wrong format of message: " + message);
        }
    }
}
