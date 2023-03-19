package de.grb.droneproject.excercises;

import java.util.Random;

import de.grb.droneproject.vectormath.Vector3D;

/**
 * This class generates addition exercises.
 */
public class AdditionExerciseGenerator extends AddSubExercise {

    /**
     * Generates the vectors for the addition exercise.
     *
     * @apiNote
     * Vectors are chosen to accommodate for physical limitations of space.
     * We only have around 5*5*2 meters that work well for our purposes, which is
     * why the resulting vectors are guaranteed to have:
     * X- and Y-components of between -5 and 5 inclusive.
     * Z-components of between -5 and 5 inclusive.
     *
     * @author L. Janke
     * @author M. Hagen
     */
    @Override
    public void Generate() {
        firstVector = new Vector3D();
        secondVector = new Vector3D();

        Random random = new Random();

        // result vectors z HAS TO BE between 0 and 2 (prevent flying into ground or too high)
        int z3 = random.nextInt(3);

        // first vector has z between 0 and z3
        int z1 = z3 > 0 ? random.nextInt(z3 + 1) : 0;

        // choose second vectors z to accommodate for result vector with z between 0 and 2
        int z2 = z3 - z1;


        // result vectors x and y HAS TO BE between -5 and 5
        int x3 = random.nextInt(11) - 5;
        int y3 = random.nextInt(11) - 5;

        // first vector has x and y between -9 and 9 respectively
        int x1 = random.nextInt(19) - 9;
        int y1 = random.nextInt(19) - 9;

        // choose second vectors x and y to accommodate for result vector with x3 and y3
        int x2 = x3 - x1;
        int y2 = y3 - y1;


        // now the vectors are populated with the components that meet our constraints
        firstVector.setX(x1);
        firstVector.setY(y1);
        firstVector.setZ(z1);

        secondVector.setX(x2);
        secondVector.setY(y2);
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
