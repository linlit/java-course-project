package com.acme.edu.chat;

import com.acme.edu.exception.SendMessageException;

import java.io.DataOutputStream;
import java.io.IOException;

/*
 * Listener pattern for a chat member.
 * Supports current auth status and connection.
 */
public class User {
    private String userName;
    private final DataOutputStream outputStream;

    public User(DataOutputStream out) {
        this.outputStream = out;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void notifyUser(String message) throws SendMessageException {
        try {
            this.outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new SendMessageException("Cannot write a message to this stream", e);
        }
    }
}
