package com.acme.edu.server;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.parser.CommandParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Emulates server-side work
 */
public class Server {
    private static final ChatObserver observer = new ChatObserver();
    private static final CommandParser commandParser = new CommandParser();

    public static void main(String[] args) {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000)) {
            ExecutorService executor =  Executors.newFixedThreadPool(1000);

            while (true) {
                final Socket clientConnection = connectionPortListener.accept();
                final DataInputStream inputStream = new DataInputStream(
                        new BufferedInputStream(
                                clientConnection.getInputStream()));
                final DataOutputStream outputStream = new DataOutputStream(
                        new BufferedOutputStream(
                                clientConnection.getOutputStream()));
                executor.submit(() -> run(inputStream, outputStream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void run(DataInputStream inputStream, DataOutputStream outputStream) {
        User user = new User(outputStream);
        observer.subscribeToChat(user);

        while (true) {
            try {
                final String clientMessage = inputStream.readUTF();
                commandParser.parse(clientMessage);
                observer.notifyChatMembers(commandParser.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


