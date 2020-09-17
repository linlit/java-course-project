package com.acme.edu.exception;

public class ExceptionLogger {
    static private final StringBuffer exceptionsHistory = new StringBuffer();

    static public void logException(String message, Exception e) {
        synchronized (exceptionsHistory) {
            exceptionsHistory.append(message).append(e);
        }
    }

    static public String getExceptionLog() {
        return exceptionsHistory .toString();
    }
}
