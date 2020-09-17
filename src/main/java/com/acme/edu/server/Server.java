package com.acme.edu.server;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.exception.SendMessageException;
import com.acme.edu.parser.MessageProcessor;
import com.acme.edu.parser.reactors.CommandReactor;
import com.acme.edu.parser.reactors.ExitReactor;

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
    private static final MessageProcessor commandor = new MessageProcessor();
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
            ExceptionLogger.logException("Server stopped", e);
        }
    }

    private static void run(DataInputStream inputStream, DataOutputStream outputStream) {
        User user = new User(outputStream);
        observer.subscribeToChat(user);

        while (user.isUserAlive()) {
            String clientMessage = "";
            try {
                clientMessage = inputStream.readUTF();
                CommandReactor reactor = commandor.parse(clientMessage, user, observer);
                reactor.react();
            }
            catch (IOException e) {
                new ExitReactor(user, observer).react();
            }
            catch (InvalidMessageException| SendMessageException e) {
                ExceptionLogger.logException("Cannot perform client action", e);
            }
        }
    }
}


