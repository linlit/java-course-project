package com.acme.edu.Server;

import com.acme.edu.chat.ChatObserver;
import com.acme.edu.chat.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*final Socket clientConnection = connectionPortListener.accept();
           final DataInputStream input = new DataInputStream(
                   new BufferedInputStream(
                           clientConnection.getInputStream()));
           final DataOutputStream out = new DataOutputStream(
                   new BufferedOutputStream(
                           clientConnection.getOutputStream()))*/

// /exit
//
public class Server {
    private static ChatObserver observer = new ChatObserver();
    public static void main(String[] args) throws IOException {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);){
            ExecutorService executor =  Executors.newFixedThreadPool(1000);
            while(true){
                final Socket clientConnection = connectionPortListener.accept();
                executor.submit(() -> run(clientConnection));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void run(Socket clientConnection) throws IOException {
        MessageParser messageParser = new MessageParser();
        User user = new User(clientConnection);
        observer.subscribeToChat(user);
        while(true){
            String clientMessage = user.waitMessage();
            try {
                messageParser.parse(clientMessage);
                observer.notifyChatMembers(clientMessage);

            } catch (MessageException e) {
                user.notifyUser(e.getMessage());
            }
        }
    }
}


