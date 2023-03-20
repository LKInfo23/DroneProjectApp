package de.grb.droneproject;

import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.util.Logger;

/**
 * This class is used to keep the instances of the DroneCommunicator and the Logger.
 * It could be expanded to include various other classes and objects. For example having a single instance of every
 * ExerciseGenerator might be a good idea. So they don't have to be created every time they are needed.
 *
 */
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
