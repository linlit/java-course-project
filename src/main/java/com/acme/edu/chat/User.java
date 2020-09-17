package com.acme.edu.chat;

import java.net.Socket;

/*
 * Listener pattern for a chat member.
 */
public class User {
    private boolean isAuthenticated;
    private final Socket userConnection;

    public User(Socket socket) {
        this.userConnection = socket;
        this.isAuthenticated = true;
    }

    public String notifyUser(String message) {
        return message;
    }

    void unsubscribeFromChat() {
        this.isAuthenticated = false;
    }
}
