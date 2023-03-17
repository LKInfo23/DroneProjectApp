package de.grb.droneproject.util;

public class Logger {

    private static StringBuilder log;

    public Logger() {
        log = new StringBuilder();
    }

    public void append(String text) {
        if (text.length() > 0 && text.charAt(text.length() - 1) == '\n') {
            log.append(text);
        } else {
            log.append(text).append("\n");
        }
    }

    public String getLog(){
        return log.toString();
    }

    private void clearLog(){
        log = new StringBuilder();
    }


}
