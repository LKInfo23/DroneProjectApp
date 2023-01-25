package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

public class AdditionExerciseGenerator extends AddSubExercise {

    @Override
    public void Generate() {
        firstVector = new Vector3D(10, 1);
        secondVector = new Vector3D(10, 1);
        solution = firstVector.add(secondVector);
    }

    @Override
    public String AsStringExercise() {
        return "Die Drone befindet sich am Punkt: " + firstVector + "\nUnd fliegt den Vektor: " + secondVector +
                "\nGeben sie den Punkt an an dem die Drone sich jetzt befindet.\n";
    }

    @Override
    public String AsStringExerciseWSolution() {
        return "Die Drone befindet sich am Punkt: " + firstVector + "\nUnd fliegt den Vektor: " + secondVector +
                "\nGeben sie den Punkt an an dem die Drone sich jetzt befindet.\n" + solution + "\n";
    }

    public String toString() {
        return firstVector.toString() + " + " + secondVector.toString() + " = " + solution.toString() ;
    }
}
