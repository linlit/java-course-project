package com.acme.edu.chat;

import com.acme.edu.exception.SendMessageException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Interface that emulates pattern Observer for chat activities.
 */
public class ChatObserver {
    static final Collection<User> chatMembers = new ConcurrentLinkedQueue<>(new ArrayList<>(1000));

    /*
     * Adding new user to group chat and update listeners
     * @param client  new client
     */
    public void subscribeToChat(User client) {
        chatMembers.add(client);
    };

    /*
     * Deleting customer from chat.
     */
    public void unsubscribeFromChat(User client) {
        client.unsubscribeFromChat();
        chatMembers.remove(client);
    };

    /*
     * Notify all users in the chat about updates.
     */
    public void notifyChatMembers(String message) {
        synchronized (chatMembers) {
            chatMembers.forEach(user -> {
                try {
                    user.notifyUser(message);
                } catch (SendMessageException e) {
                    e.printStackTrace();
                }
            });
        }
    };
}
