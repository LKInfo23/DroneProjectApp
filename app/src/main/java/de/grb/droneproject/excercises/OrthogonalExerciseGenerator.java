//CURRENTLY NOT IN USE
package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.LinearFunction;
import de.grb.droneproject.vectormath.Vector3D;

public class OrthogonalExerciseGenerator extends OrthogonalExercise {

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
