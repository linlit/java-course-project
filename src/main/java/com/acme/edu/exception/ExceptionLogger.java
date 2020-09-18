package com.acme.edu.exception;

/**
 * Custom exceptions logger, supports saving to exception history and getting all the history.
 */
public class ExceptionLogger {
    static final StringBuilder exceptionsHistory = new StringBuilder();

    private ExceptionLogger(){

    }
    public static void logExceptionQuiet(String message, Exception e) {
        synchronized (exceptionsHistory) {
            exceptionsHistory.append(message).append(e);
        }
    }

    public static void logExceptionWithError(String message, Exception e) {
        logExceptionQuiet(message, e);
        System.err.println(message);
    }

    public static void logExceptionWithInfo(String message, Exception e) {
        logExceptionQuiet(message, e);
        System.out.println(message);
    }

    public static String getExceptionLog() {
        return exceptionsHistory.toString();
    }
}
