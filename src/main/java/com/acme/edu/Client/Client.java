package com.acme.edu.Client;

import com.acme.edu.exception.ClientException;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (
                final Socket connection = new Socket("127.0.0.1", 10_000);
                final DataInputStream input =
                        new DataInputStream(
                                new BufferedInputStream(connection.getInputStream()));
                final DataOutputStream out =
                        new DataOutputStream(
                                new BufferedOutputStream(connection.getOutputStream()));
        ) {

        } catch (IOException e) {
            e.printStackTrace();
            throw new ClientException("Connection error", e);
        }
    }

    private static void sendMessage(String message, DataOutputStream out) {
        String decoratedMessage = new MessageManager().decorate(message);
        try {
            out.writeUTF(decoratedMessage);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClientException("Couldn't send the message", e);
        }
    }
}
/snd hfhjvbhbv
sin
/snd
// TODO flush
//TODO valid?
// /send cat -> ??? -> /send <time> cat
// /exit ->  (return main)
// /hist ->
//