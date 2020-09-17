package com.acme.edu.chat;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ChatCache {
    private StringBuffer historyChatCache = new StringBuffer();
    public void add(String message){
        synchronized (historyChatCache) {
            historyChatCache.append(message).append(System.lineSeparator());
        }
    }

    public String getHistoryChatCache() {
        return historyChatCache.toString();
    }
}
