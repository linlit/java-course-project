package com.acme.edu.parser.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

/**
 * Reacts when user wants to send a message.
 */
public class SendReactor implements CommandReactor {
    private final String sendMessage;
    private final ChatObserver observer;
    private final User user;

    /**
     * Creates reactor for send action.
     * @param message String that should be sent
     * @param observer ChatObserver for all rooms
     */
    public SendReactor(String message, ChatObserver observer, User user) {
        this.sendMessage = message;
        this.observer = observer;
        this.user = user;
    }

    @Override
    public void react() {
        observer.notifyChatMembers(this.sendMessage, this.user.getRoomId());
    }
}
