//CURRENTLY NOT IN USE
package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.LinearFunction;
import de.grb.droneproject.vectormath.Vector3D;

/**
 * class to generate orthogonality exercises.
 * @apiNote this is very bare bones and needs extra work. one problem that was encountered is the checking of answers.
 * The problem arose because there are technically an infinite amount of valid solutions and floating point math also does not
 * work very well on computers
 * @author L. Janke
 */
public class OrthogonalExerciseGenerator extends OrthogonalExercise {

    /**
     * generates the two vectors.
     * First it generates a function with the starting point of (0;0;2) (this is the point the drone is at
     * after takeoff) and a random direction vector.
     * Then it takes the point at 1 of the previous function and an orthogonal (on the XY-plane) direction vector
     */
    @Override
    public void Generate() {
        g1 = new LinearFunction(new Vector3D(0,0,2), new Vector3D(10,1));
        g2 = new LinearFunction(g1.pointAt(1), g1.getDirection().OrthogonalXY());
    }

    @Override
    public String AsStringExercise() {
        return "Die Drone fliegt entlang der Funktion: \n" + g1 + "\nJetzt ist die Drone bei dem Punkt: \n" +
                g1.pointAt(1) + "\nGeben sie ein Funktion an, sodass diese Orthogonal zu der Funktion auf der XY-Ebene ist.\n" +
                g2.withoutDirection();
    }

    @Override
    public String AsStringExerciseWSolution() {
        return "Die Drone fliegt entlang der Funktion: \n" + g1 + "\nJetzt ist die Drone bei dem Punkt: \n" +
                g1.pointAt(1) + "\nGeben sie ein Funktion an, sodass diese Orthogonal zu der Funktion auf der XY-Ebene ist.\n" +
                g2;
    }

    public String toString() {
        return g1.toString() + "\northogonal Line:\n" + g2.toString();
    }
}
