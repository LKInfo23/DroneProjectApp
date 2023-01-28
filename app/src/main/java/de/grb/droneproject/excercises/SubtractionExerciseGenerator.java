package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

public class SubtractionExerciseGenerator extends AddSubExercise {

    @Override
    public void Generate() {
        firstVector = new Vector3D(10, 0);
        secondVector = new Vector3D(10, 0);
        solution = firstVector.sub(secondVector);
    }

    @Override
    public String AsStringExercise() {
        return "Die Drone befindet sich am Punkt: " + firstVector + "\nUnd soll zum Punkt: " + solution +
                "\nGeben sie den Vektor an den die Drone abfliegen muss.\n";
    }

    @Override
    public String AsStringExerciseWSolution() {
        return "Die Drone befindet sich am Punkt: " + firstVector + "\nUnd soll zum Punkt: " + solution +
                "\nGeben sie den Vektor an den die Drone abfliegen muss.\n" + secondVector + "\n";
    }

    public String toString() {
        return firstVector.toString() + " - " + secondVector.toString() + " = " + solution.toString() ;
    }
}
