package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

/**
 * Reacts when user wants to exit the chat.
 */
public class ExitReactor implements CommandReactor {
    private final User user;
    private final ChatObserver observer;

    /**
     * Creates reactor for exiting the chat.
     * @param user User that wants to leave the chat
     * @param observer ChatObserver for all rooms
     */
    public ExitReactor(User user, ChatObserver observer) {
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void react() {
        System.out.println("User "+ user.getUserName() +" has quiet");
        this.observer.unsubscribeFromChat(this.user);
    }
}
