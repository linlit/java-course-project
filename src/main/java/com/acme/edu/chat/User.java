package com.acme.edu.chat;

import com.acme.edu.exception.SendMessageException;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Listener pattern for a chat member.
 * Supports current auth status and connection.
 */
public class User {
    private String userName;
    private volatile String roomId;
    private final DataOutputStream outputStream;
    private volatile boolean isAuthenticated;

    public User(DataOutputStream out) {
        this.outputStream = out;
        this.roomId = "main";
        this.isAuthenticated = true;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setIsAuthenticated(boolean auth) {
        this.isAuthenticated = auth;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * Writes message to user's output stream
     * @param message  String message to be sent
     */
    public void notifyUser(String message) throws SendMessageException {
        try {
            outputStream.writeUTF(message);
            outputStream.flush();
        } catch (IOException e) {
            throw new SendMessageException("Cannot write a message to this stream", e);
        }
    }
}
