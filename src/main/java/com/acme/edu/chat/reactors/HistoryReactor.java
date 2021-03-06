package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.SendMessageException;

/**
 * Reacts when user wants to see the history
 */
public class HistoryReactor implements CommandReactor {
    private final User user;
    private final ChatObserver observer;

    /**
     * Creates reactor for show history action.
     * @param user User that wants to see history
     * @param observer ChatObserver for all rooms
     */
    public HistoryReactor(User user, ChatObserver observer) {
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void react() throws SendMessageException {
        observer.loadHistory(user);
    }
}
