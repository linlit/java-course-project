package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.SendMessageException;

/**
 * Reacts when user wants to change username
 */
public class AuthenticationReactor implements CommandReactor  {
    private final String userName;
    private final User user;
    private final ChatObserver observer;

    /**
     * Creates reactor for setting nickname action.
     * @param userName String that user wants to set as nickname
     * @param observer ChatObserver for all rooms
     * @param user User that wants to change nickname
     */
    public AuthenticationReactor(String userName, ChatObserver observer, User user) {
        this.userName = userName;
        this.observer = observer;
        this.user = user;
    }

    @Override
    public void react() {
        try {
            if (this.observer.findMeAllUsersWithName(this.userName).isEmpty()) {
                this.user.setUserName(this.userName);
                this.user.notifyUser("New nickname was set successfully!");
            } else {
                this.user.notifyUser("There is another user with such nickname!");
            }
        } catch (SendMessageException e) {
            ExceptionLogger.logExceptionWithError("Error while set nickname command", e);
        }
    }
}
