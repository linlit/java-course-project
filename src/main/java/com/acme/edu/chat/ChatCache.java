package com.acme.edu.chat;

import com.acme.edu.exception.ExceptionLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static java.lang.System.lineSeparator;

public class ChatCache {
    private final String chatLogPath = "log.txt";

    public void add(String message) {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new BufferedOutputStream(
                                             new FileOutputStream(chatLogPath, true))))) {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            ExceptionLogger.logException("Cannot save this message to chat log file", e);
        }
    }

    public String getHistoryChatCache() {
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     new BufferedInputStream(
                                             new FileInputStream(chatLogPath)), StandardCharsets.UTF_8))) {
            String readLine;
            StringBuilder historyChatCache = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                historyChatCache.append(">> ").append(readLine).append(lineSeparator());
            }
            return historyChatCache.toString();
        } catch (IOException e) {
            ExceptionLogger.logException("Cannot read from chat log file", e);
            return "";
        }
    }
}
