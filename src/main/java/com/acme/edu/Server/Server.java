package com.acme.edu.Server;

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


public class Server {
    public static void main(String[] args) throws IOException {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);
           ){
            ExecutorService executor =  Executors.newFixedThreadPool(1000);
            while(true){
                final Socket clientConnection = connectionPortListener.accept();
                executor.submit(() -> run(clientConnection));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void run(Socket clientConnection){

    }
}

