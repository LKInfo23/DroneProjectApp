//CURRENTLY NOT IN USE
package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

import java.util.ArrayList;

/**
 * class that generates the parkour exercises.
 * @author L. Janke
 */
public class ParkourExerciseGenerator extends ParkourExercise {

    /**
     *generates the parkour exercise.
     * The First point is always (0;0;2) because that is the position of the drone after takeoff and then generates 4
     * more random points to append to the list.
     */
    @Override
    public void Generate() {
        PointList = new ArrayList<>(5);
        PointList.add(new Vector3D(0,0,2));
        for (int i = 1; i < 5; i++) {
            PointList.add(new Vector3D(10,0));
        }
    }

    //very crude and useless implementation
    /**
     * this should do what it says for the other exercises. But since the parkour exercise is so different from the rest
     * a different logic is needed and that has not been implemented yet so the return is pretty much useless.
     * @return {@link ParkourExerciseGenerator#getString()}
     */
    @Override
    public String AsStringExercise() {
        return getString();
    }
    //very crude and useless implementation
    /**
     * this should do what it says for the other exercises. But since the parkour exercise is so different from the rest
     * a different logic is needed and that has not been implemented yet so the return is pretty much useless.
     * @return {@link ParkourExerciseGenerator#getString()}
     */
    @Override
    public String AsStringExerciseWSolution() {
        return getString();
    }

    /**
     * creates a string of all the points in the format:
     * "Die Drone ist am Punkt: (...) und muss zum Punkt (...)"
     * needs to be implemented better
     * @return the String created
     */
    private String getString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++ ) {
            sb.append("Die Drone ist am Punkt: ").append(PointList.get(i)).append("\n");
            sb.append("Und muss zum Punkt: ").append(PointList.get(i+1)).append("\n");
        }
        return sb.toString();
    }

    /**
     * a toString() for the parkour exercise
     * @return a string of the vectors
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Vector3D vec : PointList) {
            sb.append(vec.toString()).append(" ");
        }
        return sb.toString();
    }
}
