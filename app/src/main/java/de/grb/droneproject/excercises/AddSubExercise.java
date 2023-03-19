package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

/**
 * Class that provides the concrete structure of addition and subtraction exercises.
 *
 * The Exercises both use three vectors, two that will be added/subtracted and the resulting vector.
 * @author L. Janke
 */
public abstract class AddSubExercise extends Exercise {
    /**
     * first vector of this exercise
     */
    Vector3D firstVector;
    /**
     * second vector of this exercise
     */
    Vector3D secondVector;
    /**
     * solution vector to this exercise
     */
    Vector3D solution;

    /**
     * Function to get firstVector
     * @return firstVector
     */
    public Vector3D getFirstVector() {
        return firstVector;
    }

    /**
     * Function to set the value of firstVector
     * @param firstVector the first vector
     */
    public void setFirstVector(Vector3D firstVector) {
        this.firstVector = firstVector;
    }

    /**
     * Function to get secondVector
     * @return secondVector
     */
    public Vector3D getSecondVector() {
        return secondVector;
    }

    /**
     * Function to set the value of secondVector
     * @param secondVector the second vector
     */
    public void setSecondVector(Vector3D secondVector) {
        this.secondVector = secondVector;
    }

    /**
     * Function to get the solution vector to the exercise
     * @return solution
     */
    public Vector3D getSolution() {
        return solution;
    }

    /**
     * Function to check if a vector is a solution to this exercise by checking if the x, y, and z value match
     * @param s s is the vector to be checked
     * @return a boolean that is TRUE if the provided vector is a solution and FALSE if not
     */
    public boolean isSolution(Vector3D s) {
        return this.solution.equals(s) || (this.solution.getX() == s.getX() && this.solution.getY() == s.getY() && this.solution.getZ() == s.getZ());
    }
}
