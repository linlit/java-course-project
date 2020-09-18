package com.acme.edu.server;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;
import com.acme.edu.chat.reactors.CommandReactor;
import com.acme.edu.chat.reactors.ExitReactor;
import com.acme.edu.exception.ExceptionLogger;
import com.acme.edu.exception.InvalidMessageException;
import com.acme.edu.exception.SendMessageException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Emulates server-side work
 */
public class Server {
    private static final ChatObserver observer = new ChatObserver();
    private static final MessageProcessor messageProcessor = new MessageProcessor();

    public static void main(String[] args){
        try (final ServerSocket connectionPortListener = new ServerSocket(Integer.parseInt(args[0]))) {
            final ExecutorService executor = Executors.newFixedThreadPool(1000);

            while (!connectionPortListener.isClosed()) {
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
            ExceptionLogger.logExceptionQuiet("Server stopped", e);
        }
    }

    /**
     * Submits a job for new user thread
     *
     * @param inputStream  input user stream
     * @param outputStream output user stream
     */
    public static void run(DataInputStream inputStream, DataOutputStream outputStream) {
        User user = new User(outputStream);
        observer.subscribeToChat("main", user);

        while (user.getIsAuthenticated()) {
            String clientMessage = "";
            try {
                clientMessage = inputStream.readUTF();
                CommandReactor reactor = messageProcessor.parse(clientMessage, user, observer);
                reactor.react();
            } catch (IOException e) {
                new ExitReactor(user, observer).react();
            } catch (InvalidMessageException | SendMessageException e) {
                ExceptionLogger.logExceptionQuiet("Cannot perform client action for client " + user, e);
            }
        }
    }
}


