package com.acme.edu.exception;

/**
 * Custom exceptions logger, supports saving to exception history and getting all the history.
 */
public class ExceptionLogger {
    static final StringBuilder exceptionsHistory = new StringBuilder();

    static public void logExceptionQuiet(String message, Exception e) {
        synchronized (exceptionsHistory) {
            exceptionsHistory.append(message).append(e);
        }
    }

    static public void logExceptionWithError(String message, Exception e) {
        logExceptionQuiet(message, e);
        ExceptionLogger.logExceptionWithInfo(message, new Exception(message));
    }

    static public void logExceptionWithInfo(String message, Exception e) {
        logExceptionQuiet(message, e);
        ExceptionLogger.logExceptionWithInfo(message, new Exception(message));
    }

    static public String getExceptionLog() {
        return exceptionsHistory.toString();
    }
}
