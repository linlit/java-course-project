package com.acme.edu;

import com.acme.edu.client.Client;
import com.acme.edu.exception.ClientException;
import com.acme.edu.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest extends Client implements SysoutCaptureAndAssertionAbility {
    //private Client client;

   /* @Before
    public void setUp() {
        client = new Client();
    }*/

    @Test(expected = ClientException.class)
    public void shouldThrowClientExceptionWhenIllegalCommandCalled() throws ClientException, IOException {
        DataOutputStream mock = mock(DataOutputStream.class);

        Client.sendMessage("/fhf", mock);
    }

    @Test
    public void shouldFlushMessageWhenMessageIsCorrect() throws ClientException, IOException {
        DataOutputStream out = new DataOutputStream(System.out);
        DataInputStream in = new DataInputStream(System.in);

        Client.sendMessage("/snd Hello!", out);
        in.readUTF()
        //String message = in.readUTF();
        assertSysoutContains("/snd");
        assertSysoutContains("2020");
        assertSysoutContains("Hello!");
    }
}
