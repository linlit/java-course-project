package com.acme.edu.chat;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.SendMessageException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class that represents pattern Observer for chat activities.
 */
public class ChatObserver {
    private final ConcurrentMap<String, Set<User>> chatMembers = new ConcurrentHashMap<>();
    private final ChatCache cache = new ChatCache();

    /**
     * Adding new user to group chat and update listeners
     * @param client User new client
     */
    public void subscribeToChat(String roomId, User client) {
        synchronized (this.chatMembers) {
            this.chatMembers.computeIfAbsent(roomId, k -> new HashSet<>()).add(client);
            client.setRoomId(roomId);
            client.setIsAuthenticated(true);
        }
    }

    /**
     * Deleting user from chat for all.
     * @param client user that needs an exit from the whole chat
     */
    public void unsubscribeFromChat(User client) {
        unsubscribeFromRoom(client);
        client.setIsAuthenticated(false);
    }

    /**
     * Deleting user from his current room for further change room or exiting app.
     * @param client  user that needs to be deleted from chat room
     */
    public void unsubscribeFromRoom(User client) {
        synchronized (this.chatMembers) {
            this.chatMembers.get(client.getRoomId()).remove(client);
        }
    }

    /**
     * Notify all users in the chat about updates.
     * @param message  String that needs to be send to others
     * @param roomId  identifier of room which users need to be notified
     */
    public void notifyChatMembers(String message, String roomId) {
        synchronized (this.chatMembers) {
            this.cache.add(message, roomId);
            this.chatMembers.get(roomId).forEach(user -> {
                try {
                    user.notifyUser(message);
                } catch (SendMessageException e) {
                    ExceptionLogger.logExceptionQuiet("Cannot notify user: " + user, e);
                }
            });
        }
    }

    public void notifyOneMember(String message, User from, User to) {}

    /**
     * Load all chat history and notifies user who ordered it.
     * @param user client that wants to see current room history
     */
    public void loadHistory(User user) throws SendMessageException {
        user.notifyUser(this.cache.getHistoryChatCache(user.getRoomId()));
    }
}
