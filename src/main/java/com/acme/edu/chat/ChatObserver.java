package com.acme.edu.chat;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.SendMessageException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Class that represents pattern Observer for chat activities.
 */
public class ChatObserver {
    static final Collection<User> chatMembers = new ConcurrentLinkedQueue<>(new ArrayList<>(1000));
    static final ChatCache cache = new ChatCache();

    /**
     * Adding new user to group chat and update listeners
     * @param client  new client
     */
    public void subscribeToChat(User client) {
        chatMembers.add(client);
    }

    /**
     * Deleting customer from chat.
     */
    public void unsubscribeFromChat(User client) {
        chatMembers.remove(client);
        client.setIsAuthenticated();
    }

    /**
     * Notify all users in the chat about updates.
     */
    public void notifyChatMembers(String message) {
        synchronized (chatMembers) {
            cache.add(message);
            chatMembers.forEach(user -> {
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
        user.notifyUser(cache.getHistoryChatCache());
    }
}
