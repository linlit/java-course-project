package com.acme.edu.client;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.exception.ClientException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

import static org.mockito.Mockito.*;

public class ClientTest implements SysoutCaptureAndAssertionAbility {

    @Test(expected = ClientException.class)
    @Ignore
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
}

