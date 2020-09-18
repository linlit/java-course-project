package com.acme.edu.client;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.exception.ClientException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

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

    @Test
    public void shouldThrowClientExceptionWhenIllegalCommandCalled() throws ClientException, IOException {
        DataOutputStream mock = mock(DataOutputStream.class);
        Client.sendMessage("/fhf", mock);
        assertSysoutEquals("Unknown command" + System.lineSeparator());
    }

    @Test
    public void shouldFlushMessageWhenMessageIsCorrect() throws ClientException, IOException {
        DataOutputStream outMock = mock(DataOutputStream.class);

        Client.sendMessage("/snd Hello!", outMock);

        verify(outMock, times(1)).flush();
    }
}

