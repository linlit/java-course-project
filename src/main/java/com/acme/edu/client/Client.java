package com.acme.edu.client;

import com.acme.edu.exception.ClientException;
import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.InvalidMessageException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client-side logic of application
 */
public class Client {
    private static final MessagePreprocessor manager = new MessagePreprocessor();
    private static boolean serverAlive = true;
    public static void main(String[] args) {
        try (
                final Socket connection = new Socket("127.0.0.1", 10_000);
                final DataInputStream input =
                        new DataInputStream(
                                new BufferedInputStream(connection.getInputStream()));
                final DataOutputStream output =
                        new DataOutputStream(
                                new BufferedOutputStream(connection.getOutputStream()));
                final Scanner in = new Scanner(System.in)
        ) {
            startServerListener(input);
            startClientListener(in, output);
        } catch (IOException e) {
            ExceptionLogger.logException("Server is not available.", e);
            System.err.println("Server is not available.");
        }
    }

    private static void startClientListener(Scanner in, DataOutputStream output) {
        while (serverAlive) {
            try {
                String currentLine = in.nextLine();
                if (!serverAlive || manager.isExitCommand(currentLine)) {
                    return;
                }
                sendMessage(currentLine, output);
            } catch (ClientException e) {
                ExceptionLogger.logException("Incorrect message", e);
                System.out.println("Please, try again!");
            }
        }
    }

    private static void startServerListener(DataInputStream input) {
        Thread thread = new Thread(() -> {
            while (serverAlive) {
                try {
                    String readLine = input.readUTF();
                    System.out.println(readLine);
                } catch (IOException e) {
                    ExceptionLogger.logException("Server is not available.", e);
                    System.err.println("Server is not available.");
                    serverAlive = false;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
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