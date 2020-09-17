package com.acme.edu.parser.reactors;

import com.acme.edu.chat.ChatObserver;

/*
 * Reacts when user wants to send a message.
 */
public class SendReactor implements CommandReactor {
    private final String sendMessage;
    private final ChatObserver observer;

    public SendReactor(String message, ChatObserver observer) {
        this.sendMessage = message;
        this.observer = observer;
    }

    @Override
    public void react() {
        observer.notifyChatMembers(this.sendMessage);
    }
}