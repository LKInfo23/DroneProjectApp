package de.grb.droneproject.vectormath;

import java.util.Random;

/**
 * Class representing a three-dimensional vector, which is used to control the Drone's movement.
 *
 * @author M. Hagen
 * @author L. Janke
 */
public class Vector3D {
    /**
     * the x coordinate of the vector representing the movement in the x direction
     */
    double x;
    /**
     * the y coordinate of the vector representing the movement in the y direction
     */
    double y;
    /**
     * the z coordinate of the vector representing the movement in the z direction
     */
    double z;

    /**
     * Constructor to construct the class without any parameters. All three components of the vector will be initialized as 0
     */
    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Constructor to construct a random vector given an upper and lower bound.
     *
     * @param upperBound Upper bound of the random values to be generated.
     * @param lowerBound Lower bound of the random values to be generated.
     */
    public Vector3D(int upperBound, int lowerBound) {
        Random random = new Random();
        this.x = random.nextInt(upperBound - lowerBound) + lowerBound;
        this.y = random.nextInt(upperBound - lowerBound) + lowerBound;
        this.z = random.nextInt(upperBound - lowerBound) + lowerBound;
    }


    /**
     * Constructor to the construct a vector given the x, y, and z values.
     *
     * @param x the x-component of the vector.
     * @param y the y-component of the vector.
     * @param z the z-component of the vector.
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double[] xyz) {
        if(xyz.length != 3)
            throw new IllegalArgumentException("xyz must have length 3");
        this.x = xyz[0];
        this.y = xyz[1];
        this.z = xyz[2];
    }

    /**
     * Constructor copy a vector.
     *
     * @param v The vector to be copied.
     */
    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    /**
     * Function returning the magnitude \sqrt{x^2+y^2+z^2} of this vector.
     *
     * @return the magnitude of this vector
     */
    public double magnitude() {
        return Math.sqrt(
                this.x * this.x +
                        this.y * this.y +
                        this.z * this.z
        );
    }

    /**
     * Function for adding a vector to this vector.
     *
     * @param v The vector to be added.
     * @return The resulting vector.
     */
    public Vector3D add(Vector3D v) {
        //for some reason the previous solution did not work;
        Vector3D sol = new Vector3D();
        sol.x = this.x + v.x;
        sol.y = this.y + v.y;
        sol.z = this.z + v.z;
        return sol;
    }

    /**
     * Function for subtracting a vector from this vector.
     *
     * @param v The vector to be subtracted.
     * @return The resulting vector.
     */
    public Vector3D sub(Vector3D v) {
        //for some reason the previous solution did not work;
        Vector3D sol = new Vector3D();
        sol.x = this.x - v.x;
        sol.y = this.y - v.y;
        sol.z = this.z - v.z;
        return sol;
    }

    /**
     * Function to calculate the cross product of this vector with a given vector.
     *
     * @param v The vector to calculate the cross product with.
     * @return The resulting vector.
     */
    public Vector3D cross(Vector3D v) {
        this.x = this.y * v.z - this.z * v.y;
        this.y = this.z * v.x - this.x * v.z;
        this.z = this.x * v.y - this.y * v.x;
        return this;
    }

    /**
     * Function to calculate the dot product of this vector and a given vector.
     *
     * @param v The vector to calculate the dot product with.
     * @return The resulting product.
     */
    public double dot(Vector3D v) {
        return (
                this.x * v.x +
                        this.y * v.y +
                        this.z * v.z
        );
    }

    /**
     * Function to normalize this vector. Normalizing means diving the vector by length of the vector to create a unit
     * vector, a vector of length 1 and keeping the original direction.
     *
     * @return The resulting normalized vector.
     */
    public Vector3D normalize() {
        Vector3D unitVector = new Vector3D();
        unitVector.x = this.x / this.magnitude();
        unitVector.y = this.y / this.magnitude();
        unitVector.z = this.z / this.magnitude();
        return unitVector;
    }

    /**
     * Function to multiply this vector with a given scalar.
     *
     * @param scalar the scalar to multiply this vector with.
     * @return The scaled vector.
     */
    public Vector3D scalarMultiplication(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    /**
     * Function to return the vector in String form
     *
     * @return the Vector in String form.
     */
    public String toString() {
        return "(" + (int) (this.x) + ", " + (int) (this.y) + ", " + (int) (this.z) + ")";
    }

    /**
     * Function to return the Vector as a String displayable in a TextView.
     *
     * @return Vector as a {@link String}
     */
    public String toTextView() {
        return (int) (this.x) + "\n" + (int) (this.y) + "\n" + (int) (this.z);
    }

    /**
     * Function to check if two vectors are collinear.
     *
     * @param v the vector where the collinearity needs to be found with
     * @return a boolean that is <b>TRUE</b> if the vectors are collinear and <b>FALSE</b> if not
     */
    public boolean isCollinear(Vector3D v) {
        Vector3D Zero = new Vector3D();
        return (this.cross(v).equals(Zero));
        //if the cross product of the vectors equals the Zero element they are collinear
    }

    /**
     * Function to return a normalized vector that is orthogonal on the XY-Axis (z = 0).
     *
     * @return the normalized orthogonal Vector
     */
    public Vector3D OrthogonalXY() {
        return new Vector3D(1, -this.x / this.y, 0).normalize();
    }

    /**
     * Function to get the x value of this vector
     *
     * @return the x value of this vector
     */
    public double getX() {
        return x;
    }

    /**
     * Function to set the x value of this vector
     *
     * @param x the x value of this vector
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Function to get the y value of this vector
     *
     * @return the y value of this vector
     */
    public double getY() {
        return y;
    }

    /**
     * Function to set the y value of this vector
     *
     * @param y the y value of this vector
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Function to get the z value of this vector
     *
     * @return the z value of this vector
     */
    public double getZ() {
        return z;
    }

    /**
     * Function to set the z value of this vector
     *
     * @param z the z value of this vector
     */
    public void setZ(double z) {
        this.z = z;
    }
}
