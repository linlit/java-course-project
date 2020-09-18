package com.acme.edu.client;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.ExceptionLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class ClientTest implements SysoutCaptureAndAssertionAbility {

    @Before
    public void setUpSystemOut() {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }

    @Test(expected = ClientException.class)
    public void shouldThrowClientExceptionWhenIllegalCommandCalled() throws ClientException {
        DataOutputStream mock = mock(DataOutputStream.class);
        Client.sendMessage("/fhf", mock);
        assertSysoutEquals("Incorrect message. Please, try again!" + System.lineSeparator());
    }

    @Test
    public void shouldFlushMessageWhenMessageIsCorrect() throws ClientException, IOException {
        DataOutputStream outMock = mock(DataOutputStream.class);

        Client.sendMessage("/snd Hello!", outMock);

        verify(outMock, times(1)).flush();
    }

    @Test
    public void shouldWriteServerIsNotAvailableWhenCanNotConnect() {
        ExceptionLogger mock = mock(ExceptionLogger.class);

        Client.main(new String[]{"", "1"});

        verify(mock, times(1)).logExceptionWithError("Server is not available.", any(IOException.class));
    }


    @Test
    public void shouldInterruptWhenCallExitCommand() throws ClientException {
        Client dummy = mock(Client.class);
        DataOutputStream out = new DataOutputStream(System.out);
        Scanner in = new Scanner("/exit");

        Client.startClientListener(in, out);

        verify(dummy, never()).sendMessage("/exit", out);
    }


    @Test
    public void shouldFlushMessageWhenStartServerListener() throws ClientException {
        DataOutputStream out = new DataOutputStream(System.out);
        Client dummy = mock(Client.class);
        Scanner in = new Scanner("/snd message" + System.lineSeparator() + "/exit");

        Client.startClientListener(in, out);

        verify(dummy, times(1)).sendMessage("/snd message", out);
    }
}