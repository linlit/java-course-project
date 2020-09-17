package com.acme.edu.client;

import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.InvalidMessageException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*
 * Client-side logic of application
 */
public class Client {
    private static final MessageManager manager = new com.acme.edu.client.MessageManager();

    public static void main(String[] args) {
        try (
                final Socket connection = new Socket("127.0.0.1", 10_000);
                final DataInputStream input =
                        new DataInputStream(
                                new BufferedInputStream(connection.getInputStream()));
                final DataOutputStream out =
                        new DataOutputStream(
                                new BufferedOutputStream(connection.getOutputStream()));
                Scanner in = new Scanner(System.in)
        ) {
            Thread thread = new Thread(() -> {
                while(true) {
                    try {
                        String readLine = input.readUTF();
                        System.out.println(readLine);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

            while(true) {
                try {
                    String currentLine = in.nextLine();
                    sendMessage(currentLine, out);
                    if(MessageManager.isExitCommand(currentLine)) {
                        return;
                    }
                } catch (ClientException e) {
                    System.out.println("Please, try again!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Server is not available.");
        }
    }

    private static void sendMessage(String message, DataOutputStream out) throws ClientException {
        try {
            String decoratedMessage = manager.getFilteredMessage(message);
            out.writeUTF(decoratedMessage);
            out.flush();
        } catch (IOException | InvalidMessageException e) {
            throw new ClientException();
        }
    }
}