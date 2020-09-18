package com.acme.edu.parser;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.reactors.AuthenticationReactor;
import com.acme.edu.parser.reactors.ExitReactor;
import com.acme.edu.parser.reactors.HistoryReactor;
import com.acme.edu.parser.reactors.SendReactor;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.*;

import static org.mockito.Mockito.mock;

public class MessageProcessorTest implements SysoutCaptureAndAssertionAbility {
    MessageProcessor messageProcessor;
    @Before
    public void setUp() {
        messageProcessor = new MessageProcessor();
    }

    @Test
    public void shouldReturnTrueTypeOfData() throws InvalidMessageException {
        ChatObserver observer = mock(ChatObserver.class);
        User user = mock(User.class);
        Assert.assertEquals(messageProcessor.parse("/snd parse", user, observer).getClass(), SendReactor.class);
        Assert.assertEquals(messageProcessor.parse("/exit", user, observer).getClass(), ExitReactor.class);
        Assert.assertEquals(messageProcessor.parse("/hist", user, observer).getClass(), HistoryReactor.class);
        Assert.assertEquals(messageProcessor.parse("/chid lol", user, observer).getClass(), AuthenticationReactor.class);
    }
    @Test (expected = InvalidMessageException.class)
    public void shouldReturnException() throws InvalidMessageException {
        ChatObserver observer = mock(ChatObserver.class);
        User user = mock(User.class);
        messageProcessor.parse("bad string", user, observer);
    }
}