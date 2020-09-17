package com.acme.edu.chat;

import com.acme.edu.exception.ConnectionFailedException;
import com.acme.edu.exception.SendMessageException;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * Listener pattern for a chat member.
 * Supports current auth status and connection.
 */
public class User {
    volatile private boolean isAuthenticated;
//    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public User(DataOutputStream out) {
//        this.inputStream = in;
        this.outputStream = out;
        this.isAuthenticated = true;
    }
    void notifyUser(String message) throws SendMessageException {
        try {
            if (this.isAuthenticated) {
                this.outputStream.writeUTF(message);
            }
        } catch (IOException e) {
            throw new SendMessageException("Cannot write a message to this stream", e);
        }
    }

    void unsubscribeFromChat() {
        this.isAuthenticated = false;
    }
}
