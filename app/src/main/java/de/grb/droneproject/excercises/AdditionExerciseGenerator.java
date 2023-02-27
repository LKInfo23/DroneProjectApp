package de.grb.droneproject.excercises;

import java.util.Random;

import de.grb.droneproject.vectormath.Vector3D;

/**
 * This class generates addition exercises.
 */
public class AdditionExerciseGenerator extends AddSubExercise {

    /**
     * Generates the vectors for the addition exercise. First it generates two random vectors and then clamps the z value
     * before using the resulting vectors to generate the solution vector
     */
    @Override
    public void Generate() {
        firstVector = new Vector3D(10, 0);
        secondVector = new Vector3D(10, 0);
        Random random = new Random();

        // result vectors z HAS TO BE between 0 and 2 (prevent flying into ground or too high)
        int z3 = random.nextInt(3);

        // first vector has z between 0 and z3
        int z1 = z3 > 0 ? random.nextInt(z3) : 0;

        // choose second vectors z to accommodate for result vector with z between 0 and 2
        int z2 = z3 - z1;

        firstVector.setZ(z1);
        secondVector.setZ(z2);

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
