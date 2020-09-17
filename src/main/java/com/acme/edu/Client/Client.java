package com.acme.edu.Client;

import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.InvalidMessageException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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
                Scanner in = new Scanner(System.in);
        ) {
            while (true) {
                try {
                    sendMessage(in.nextLine(), out);
                } catch (ClientException e) {
                    e.printStackTrace();
                    System.out.println("Try again");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String message, DataOutputStream out) throws ClientException {
        try {
            String decoratedMessage = new MessageManager().getFilteredMessage(message);
            out.writeUTF(decoratedMessage);
            out.flush();
        } catch (IOException|InvalidMessageException e) {
            e.printStackTrace();
            throw new ClientException("Couldn't send the message", e);
        }
    }
}
