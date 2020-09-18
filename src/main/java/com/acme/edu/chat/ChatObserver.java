package com.acme.edu.chat;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.SendMessageException;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Class that represents pattern Observer for chat activities.
 */
public class ChatObserver {
    final ConcurrentMap<String, Set<User>> chatMembers;
    final ChatCache cache;

    public ChatObserver() {
        this.chatMembers = new ConcurrentHashMap<>();
        this.cache = new ChatCache();
    }

    ChatObserver(ConcurrentMap<String, Set<User>> chatMembers) {
        this.chatMembers = chatMembers;
        this.cache = new ChatCache();
    }

    /**
     * Adding new user to group chat and update listeners
     *
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
     *
     * @param client user that needs an exit from the whole chat
     */
    public void unsubscribeFromChat(User client) {
        unsubscribeFromRoom(client);
        client.setIsAuthenticated(false);
    }

    /**
     * Deleting user from his current room for further change room or exiting app.
     *
     * @param client user that needs to be deleted from chat room
     */
    public void unsubscribeFromRoom(User client) {
        synchronized (this.chatMembers) {
            this.chatMembers.get(client.getRoomId()).remove(client);
        }
    }

    /**
     * Notify all users in the chat about updates.
     *
     * @param message String that needs to be send to others
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

    /**
     * @param message    private user-to-user message
     * @param fromUser   user that sent the message
     * @param toUserName username of user he wants to send the message
     */
    public void notifyOneMember(String message, User fromUser, String toUserName) {
        tryNotify(fromUser, prepareFromToMessage(message, toUserName));
        String messageToUs = prepareToFromMessage(message, fromUser.getUserName());
        List<User> listUsers = findMeAllUsersWithName(toUserName);

        if (listUsers.isEmpty()) return;
        listUsers.forEach(
                user -> tryNotify(user, messageToUs)
        );
    }

    /**
     * @param toUserName username to find among all
     * @return found user
     */
    public List<User> findMeAllUsersWithName(String toUserName) {
        List<User> user = new LinkedList<>();

        synchronized (this.chatMembers) {
            chatMembers.forEach((e, set) ->
                    set.forEach(usr -> {
                        if (toUserName.equals(usr.getUserName())) user.add(usr);
                    })
            );
        }
        return user;
    }


    private void tryNotify(User user, String message) {
        try {
            user.notifyUser(message);
        } catch (SendMessageException e) {
            ExceptionLogger.logExceptionWithError("Cannot notify user: " + user, e);
        }
    }

    /**
     * Load all chat history and notifies user who ordered it.
     *
     * @param user client that wants to see current room history
     */
    public void loadHistory(User user) throws SendMessageException {
        user.notifyUser(this.cache.getHistoryChatCache(user.getRoomId()));
    }


    private String prepareFromToMessage(String message, String toUserName) {
        return "from me to " + toUserName + " " + message;
    }

    private String prepareToFromMessage(String message, String userName) {
        userName = (userName == null) ? "Undefined(Wo)Man" : userName;
        return "from " + userName + " to me " + message + " (private) ";
    }
}
