package com.acme.edu.parser.reactors;

import com.acme.edu.chat.User;

/*
 * Reacts when user wants to exit the chat.
 */
public class ExitReactor implements CommandReactor {
    private final User user;

    public ExitReactor(User user) {
        this.user = user;
    }

    @Override
    public void react() {
        this.user.unsubscribeFromChat();
    }
}
