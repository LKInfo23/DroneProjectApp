package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.Vector3D;

public abstract class AddSubExercise extends Exercise {
    Vector3D firstVector;
    Vector3D secondVector;
    Vector3D solution;

    public Vector3D getFirstVector() {
        return firstVector;
    }

    public void setFirstVector(Vector3D firstVector) {
        this.firstVector = firstVector;
    }

    public Vector3D getSecondVector() {
        return secondVector;
    }

    public void setSecondVector(Vector3D secondVector) {
        this.secondVector = secondVector;
    }

    public Vector3D getSolution() {
        return solution;
    }

    public boolean isSolution(Vector3D s) {
        return this.solution.equals(s) || (this.solution.getX() == s.getX() && this.solution.getY() == s.getY() && this.solution.getZ() == s.getZ());
    }
}
