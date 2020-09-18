package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

/**
 * Reacts when user wants to send a message privately.
 */
public class SendToOneUser implements CommandReactor {
    private final User userFrom;
    private final String userNameTo;
    private final String message;
    private final ChatObserver observer;

    /**
     * Creates reactor for private send action.
     * @param userFrom User who sent a message
     * @param userNameTo username of User that message is sent to
     * @param message String to be sent
     * @param observer ChatObserver for all rooms
     */
    public SendToOneUser(User userFrom, String userNameTo, String message, ChatObserver observer) {
        this.userFrom = userFrom;
        this.userNameTo = userNameTo;
        this.message = message;
        this.observer = observer;
    }

    @Override
    public void react() {
        observer.notifyOneMember(message, userFrom, userNameTo);
    }
}
