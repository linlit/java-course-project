package com.acme.edu.chat.reactors;

import com.acme.edu.exception.SendMessageException;

/**
 * Pattern command for user commands.
 */
public interface CommandReactor {
    void react() throws SendMessageException;
}
