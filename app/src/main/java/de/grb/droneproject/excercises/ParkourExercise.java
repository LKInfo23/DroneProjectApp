//CURRENTLY NOT IN USE
package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

import java.util.ArrayList;

/**
 * class to generate parkour exercises. The exercise only consists of an <i>Arraylist</i> of points.
 * @author L. Janke
 */
public abstract class ParkourExercise extends Exercise{
    /**
     * the list of points the parkour exercise consists of
     */
    ArrayList<Vector3D> PointList;

    /**
     * Function to get the list of points
     * @return the list of points
     */
    public ArrayList<Vector3D> getPointList() {
        return PointList;
    }
}
