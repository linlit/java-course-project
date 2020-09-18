package com.acme.edu.client;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.client.Client;
import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.parser.MessageProcessor;
import com.acme.edu.server.Server;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientTest extends Client implements SysoutCaptureAndAssertionAbility {

    @Test(expected = ClientException.class)
    public void shouldThrowClientExceptionWhenIllegalCommandCalled() throws ClientException, IOException {
        DataOutputStream mock = mock(DataOutputStream.class);

        Client.sendMessage("/fhf", mock);
    }

    @Test
    public void shouldFlushMessageWhenMessageIsCorrect() throws ClientException, IOException {
        DataOutputStream outMock = mock(DataOutputStream.class);

        Client.sendMessage("/snd Hello!", outMock);

        verify(outMock, times(1)).flush();
    }

    @Test
    public void shouldAskSendMessageAgainWhen() {

    }

    @Test
    public void should
}

