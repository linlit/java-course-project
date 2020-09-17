package com.acme.edu.parser.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

/*
 * Reacts when user wants to exit the chat.
 */
public class ExitReactor implements CommandReactor {
    private final User user;
    private final ChatObserver observer;

    public ExitReactor(User user, ChatObserver observer) {
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void react() {
        this.observer.unsubscribeFromChat(this.user);
    }
}
