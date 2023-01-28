package de.grb.droneproject.excercises;

import java.util.Random;

import de.grb.droneproject.vectormath.Vector3D;

public class SubtractionExerciseGenerator extends AddSubExercise {

    @Override
    public void Generate() {
        firstVector = new Vector3D(10, -9);
        secondVector = new Vector3D(10, -9);
        Random random = new Random();

        // result vectors z HAS TO BE between 0 and 2 (prevent flying into ground or too high)
        int z3 = random.nextInt(3);

        // first vector has z between -9 and 9
        int z1 = random.nextInt(10 - (-9)) - 9;

        // choose second vectors z to accommodate for result vector with z between 0 and 2
        int z2 = z1 - z3;

        firstVector.setZ(z1);
        secondVector.setZ(z2);

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
