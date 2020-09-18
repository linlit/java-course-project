package com.acme.edu.chat.reactors;

import com.acme.edu.chat.User;

/**
 * Reacts when user wants to change username
 */
public class AuthenticationReactor implements CommandReactor  {
    private final String userName;
    private final User user;

    /**
     * Creates reactor for setting nickname action.
     * @param userName String that user wants to set as nickname
     * @param user User that wants to change nickname
     */
    public AuthenticationReactor(String userName, User user) {
        this.userName = userName;
        this.user = user;
    }

    @Override
    public void react() {
        this.user.setUserName(this.userName);
    }
}
