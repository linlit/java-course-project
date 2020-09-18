package com.acme.edu.parser.reactors;

import com.acme.edu.exception.SendMessageException;

/**
 * Pattern command for user commands.
 */
public interface CommandReactor {
    void react() throws SendMessageException;
}
