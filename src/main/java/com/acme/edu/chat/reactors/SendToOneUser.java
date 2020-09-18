package com.acme.edu.chat.reactors;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.SendMessageException;

public class SendToOneUser implements CommandReactor {
    private User userFrom;
    private String userNameTo;
    private String message;
    private ChatObserver observer;
    public SendToOneUser(User userFrom, String userNameTo, String message, ChatObserver observer) {
        this.userFrom = userFrom;
        this.userNameTo = userNameTo;
        this.message = message;
        this.observer = observer;
    }

    @Override
    public void react() {
        observer.notifyOneMember(message, userFrom, userNameTo);
    }
}
