package com.acme.edu.chat;

import com.acme.edu.exception.SendMessageException;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Listener pattern for a chat member.
 */
public class User {
    private boolean isAuthenticated;
    private final DataOutputStream userOutputStream;

    public User(Socket socket) throws SendMessageException {
        try {
            this.userOutputStream = new DataOutputStream(
                    new BufferedOutputStream(
                            socket.getOutputStream()));
        } catch (IOException e) {
            throw new SendMessageException("Cannot create output stream", e);
        }

        this.isAuthenticated = true;
    }

    void notifyUser(String message) throws SendMessageException {
        try {
            if (this.isAuthenticated) {
                this.userOutputStream.writeUTF(message);
            }
        } catch (IOException e) {
            throw new SendMessageException("Cannot write a message to this stream", e);
        }
    }

    void unsubscribeFromChat() {
        this.isAuthenticated = false;
    }
}
