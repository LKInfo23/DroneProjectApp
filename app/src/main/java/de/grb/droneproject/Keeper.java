package de.grb.droneproject;

import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.util.Logger;

public class Keeper {

    private static DroneCommunicator droneCommunicator;
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public static DroneCommunicator getDroneCommunicator() {
        if (droneCommunicator == null) {
            droneCommunicator = new DroneCommunicator("192.168.10.1", 8889);
        }
        return droneCommunicator;
    }


}
