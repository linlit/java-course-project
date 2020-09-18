package com.acme.edu.server;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.chat.reactors.*;
import com.acme.edu.exception.InvalidMessageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Assert.assertEquals(messageProcessor.parse("/snd parse", user, observer).getClass(), SendToAllReactor.class);
        Assert.assertEquals(messageProcessor.parse("/exit", user, observer).getClass(), ExitReactor.class);
        Assert.assertEquals(messageProcessor.parse("/hist", user, observer).getClass(), HistoryReactor.class);
        Assert.assertEquals(messageProcessor.parse("/chid lol", user, observer).getClass(), AuthenticationReactor.class);
        Assert.assertEquals(messageProcessor.parse("/chroom lol", user, observer).getClass(), ChangeRoomReactor.class);
    }
    @Test (expected = InvalidMessageException.class)
    public void shouldReturnException() throws InvalidMessageException {
        ChatObserver observer = mock(ChatObserver.class);
        User user = mock(User.class);
        messageProcessor.parse("bad string", user, observer);
    }
}