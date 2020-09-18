package com.acme.edu.server;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.chat.reactors.*;
import com.acme.edu.exception.InvalidMessageException;

/**
 * Creates command pattern for reacting on messages.
 *
 * @see User
 * @see ChatObserver
 */
public class MessageProcessor {

    /**
     * Parses user message and decides which Command Reactor should be called.
     *
     * @param message  String that was sent from a client
     * @param user     User that invoked command by sending message
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
            return new SendToAllReactor(preparedMessage, observer, user);
        } else if (message.trim().equals("/exit")) {
            return new ExitReactor(user, observer);
        } else if (message.startsWith("/chid ")) {
            return new AuthenticationReactor(message.split("/chid ")[1], observer, user);
        } else if (message.startsWith("/chroom ")) {
            return new ChangeRoomReactor(message.split("/chroom ")[1], observer, user);
        } else if (message.startsWith("/sdnp ")) {
            String userNameTo = message.split("[ ]+")[2];
            String mess = message.split("[ ]+")[1] + " " + message.split("[ ]+", 4)[3];
            return new SendToOneUser(user, userNameTo, mess, observer);
        } else {
            throw new InvalidMessageException("Wrong format of message: " + message);
        }
    }
}
