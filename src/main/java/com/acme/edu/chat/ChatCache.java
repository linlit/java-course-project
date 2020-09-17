package com.acme.edu.chat;

public class ChatCache {
    private final StringBuffer historyChatCache = new StringBuffer();

    public void add(String message) {
        synchronized (historyChatCache) {
            historyChatCache.append(message).append(System.lineSeparator());
        }
    }

    public String getHistoryChatCache() {
        return historyChatCache.toString();
    }
}
