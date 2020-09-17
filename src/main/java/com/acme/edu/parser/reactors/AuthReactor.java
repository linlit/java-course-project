package com.acme.edu.parser.reactors;

import com.acme.edu.chat.User;

/*
 * Reacts when user wants to change username
 */
public class AuthReactor implements CommandReactor  {
    private final String userName;
    private final User user;

    public AuthReactor(String message, User user) {
        this.userName = message;
        this.user = user;
    }

    @Override
    public void react() {
        this.user.setUserName(this.userName);
    }
}
