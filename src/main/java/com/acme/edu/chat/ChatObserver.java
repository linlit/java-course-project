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
     * Deleting customer from chat.
     */
    public void unsubscribeFromChat(User client) {
        unsubscribeFromRoom(client);
        client.setIsAuthenticated(false);
    }

    public void unsubscribeFromRoom(User client) {
        synchronized (this.chatMembers) {
            this.chatMembers.get(client.getRoomId()).remove(client);
        }
    }

    /**
     * Notify all users in the chat about updates.
     */
    public void notifyChatMembers(String message, String roomId) {
        synchronized (this.chatMembers) {
            this.cache.add(message, roomId);
            this.chatMembers.get(roomId).forEach(user -> {
                try {
                    user.notifyUser(message);
                } catch (SendMessageException e) {
                    ExceptionLogger.logException("Cannot notify user: " + user, e);
                }
            });
        }
    }

    /**
     * Load all chat history and notifies user who ordered it.
     */
    public void loadHistory(User user) throws SendMessageException {
        user.notifyUser(this.cache.getHistoryChatCache(user.getRoomId()));
    }
}
