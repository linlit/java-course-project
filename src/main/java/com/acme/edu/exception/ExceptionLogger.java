package com.acme.edu.exception;

/**
 * Custom exceptions logger, supports saving to exception history and getting all the history.
 */
public class ExceptionLogger {
    static final StringBuilder exceptionsHistory = new StringBuilder();

    static public void logException(String message, Exception e) {
        synchronized (exceptionsHistory) {
            exceptionsHistory.append(message).append(e);
        }
    }

    static public String getExceptionLog() {
        return exceptionsHistory.toString();
    }
}