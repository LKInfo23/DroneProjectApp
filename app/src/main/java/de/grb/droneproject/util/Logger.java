package de.grb.droneproject.util;


/**
 * This is a class representing a logger, which is used to log the output of the Drone's movement.
 * This is only used for debugging purposes.
 * Please more or less ignore this class. A conventional logger would be better but this was faster and easier.
 * @author F. Ottenburg
 */
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
