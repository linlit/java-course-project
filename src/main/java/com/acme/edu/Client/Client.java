package com.acme.edu.Client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (final Socket connection = new Socket("127.0.0.1", 10_000);
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             connection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             connection.getOutputStream()));
        ) {
            while (true) {
                out.writeUTF(input.readUTF());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}