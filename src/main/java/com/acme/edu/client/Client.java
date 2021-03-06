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
    public static final String SERVER_IS_NOT_AVAILABLE = "Server is not available.";
    private static boolean serverAlive = true;

    public static void main(String[] args) {
        try (
                final Socket connection = new Socket(args[0], Integer.parseInt(args[1]));
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
            ExceptionLogger.logExceptionWithError(SERVER_IS_NOT_AVAILABLE, e);
        }
    }

    public static void startClientListener(Scanner in, DataOutputStream output) {
        while (serverAlive) {
            try {
                String currentLine = in.nextLine();
                if (!serverAlive || manager.isExitCommand(currentLine)) {
                    return;
                }
                sendMessage(currentLine, output);
            } catch (ClientException e) {
                ExceptionLogger.logExceptionWithInfo("Incorrect message. Please, try again!", e);
            }
        }
    }

    private static void startServerListener(DataInputStream input) {
        Thread thread = new Thread(() -> {
            while (serverAlive) {
                try {
                    String readLine = input.readUTF();
                    ExceptionLogger.logExceptionWithInfo(readLine, new Exception("New Line was read"));
                } catch (IOException e) {
                    ExceptionLogger.logExceptionWithError(SERVER_IS_NOT_AVAILABLE, e);
                    serverAlive = false;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    protected static void sendMessage(String message, DataOutputStream out) throws ClientException {
        try {
            String decoratedMessage = manager.getFilteredMessage(message);

            if (!"".equals(decoratedMessage)) {
                out.writeUTF(decoratedMessage);
                out.flush();
            }
        } catch (IOException | InvalidMessageException e) {
            throw new ClientException();
        }
    }
}