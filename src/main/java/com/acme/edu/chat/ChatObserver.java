package com.acme.edu.chat;

import java.util.ArrayList;
import java.util.List;

/*
 * Interface that emulates pattern Observer for chat activities.
 */
public class ChatObserver {
    static List<User> chatMembers = new ArrayList<>(1000);

    /*
     * Adding new user to group chat and update listeners
     * @param client  new client
     */
    void subscribeToChat(User client) {
        chatMembers.add(client);
    };

    /*
     * Deleting customer from chat.
     */
    void unsubscribeFromChat(User client) {
        client.unsubscribeFromChat();
        chatMembers.remove(client);
    };

    /*
     * Notify all users in the chat about updates.
     */
    void notifyChatMembers(String message) {
        chatMembers.forEach(user -> user.notifyUser(message));
    };
}
