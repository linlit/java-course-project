package com.acme.edu.chat;

import com.acme.edu.exception.ExceptionLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static java.lang.System.lineSeparator;

/**
 * Class for logging chat messages to specific file
 */
public class ChatCache {
    private String getLogPath(String roomId) {
        String chatLogPath = "logs/";
        return chatLogPath + "room_" + roomId + ".txt";
    }

    /**
     * Adding new message to chat log file.
     * @param message String to add to log
     */
    public void add(String message, String roomId) {
        try (BufferedWriter bufferedWriter =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     new BufferedOutputStream(
                                             new FileOutputStream(getLogPath(roomId), true))))) {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            ExceptionLogger.logException("Cannot save this message to chat log file", e);
        }
    }

    /**
     * Loading chat history from file.
     */
    public String getHistoryChatCache(String roomId) {
        try (BufferedReader br =
                     new BufferedReader(
                             new InputStreamReader(
                                     new BufferedInputStream(
                                             new FileInputStream(getLogPath(roomId))), StandardCharsets.UTF_8))) {
            String readLine;
            StringBuilder historyChatCache = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                historyChatCache.append("> ").append(readLine).append(lineSeparator());
            }
            return historyChatCache.toString();
        } catch (IOException e) {
            ExceptionLogger.logException("Cannot read from chat log file", e);
            return "";
        }
    }
}
