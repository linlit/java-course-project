package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

/**
 * Reacts when user wants to change chat room
 */
public class ChangeRoomReactor implements CommandReactor {
    private final String roomId;
    private final User user;
    private final ChatObserver observer;

    /**
     * Creates reactor for switching chat rooms.
     * @param roomId String room id to be switched to
     * @param observer ChatObserver for all rooms
     * @param user User that wants to switch chats
     */
    public ChangeRoomReactor(String roomId, ChatObserver observer, User user) {
        this.roomId = roomId;
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void react() {
        this.observer.unsubscribeFromRoom(this.user);
        this.observer.subscribeToChat(this.roomId, this.user);
    }
}
