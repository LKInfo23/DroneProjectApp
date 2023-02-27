//CURRENTLY NOT IN USE
package de.grb.droneproject.vectormath;

import java.util.Random;

/**
 * This is a class to represent a line in 3d using 2 vectors
 */
public class LinearFunction {
    Vector3D startingPoint;
    Vector3D direction;

    /**
     * Constructor to construct a line with no given parameters it will create a random line
     */
    public LinearFunction() {
        this.startingPoint = new Vector3D();
        this.direction = new Vector3D();
    }

    /**
     * Constructor to construct a linear function using two vectors
     * @param StartingPoint the starting point
     * @param Direction the direction
     */
    public LinearFunction(Vector3D StartingPoint, Vector3D Direction) {
        this.startingPoint = StartingPoint;
        this.direction = Direction;
    }

    /**
     * Function to see if two Linear functions are Parallel (doesn't check for Identical functions)
     * @param f a linear function
     * @return if they are parallel
     */
    public boolean isParallel(LinearFunction f) {
        return (f.direction.isCollinear(this.direction));
    }

    /**
     * Function to see if two linear functions are Identical
     * @param f a linear function
     * @return if they are Identical
     */
    public boolean isIdentical(LinearFunction f) {
        return (this.isParallel(f) && this.startingPoint.sub(f.startingPoint).isCollinear(this.direction));
        //checks to see if the Lines are parallel and then checks
        //if the Vector between the Starting points is collinear to the direction vectors
    }

    /**
     * Function to set a new starting point of the line.
     * The new starting point will be randomly selected by multiples of the direction vector
     */
    public void ReconfigureStartingPoint() {
        Random random = new Random();
        startingPoint.add(direction.scalarMultiplication(random.nextInt(10) - 5));
    }

    /**
     * Function to return the linear funciton as a String
     * @return the linear funciton in String for
     */
    public String toString() {
        return "x = " + startingPoint.toString() + " + r * " + direction.toString();
    }

    /**
     * Method to get the Relation of two linear functions.
     * @param f a LinearFunction
     * @return a solution
     */
    public LinearFunctionSolution getRelationTo(LinearFunction f) {
        if(this.isIdentical(f)) {
            return new LinearFunctionSolution(LinearFunctionRelation.IDENTICAL);
        }
        if(this.isParallel(f)) {
            return new LinearFunctionSolution(LinearFunctionRelation.PARALLEL);
        }
        /*first solving the first two lines of the equation using Matrix determinants
        * 1. the equation looks like this:
        * this.StartingPoint + this.Direction * r = f.StartingPoint + f.Direction * v
        * expanded:
        * this.StartingPoint.x + this.Direction.x * r = f.StartingPoint.x + f.Direction.x * v
        * this.StartingPoint.y + this.Direction.y * r = f.StartingPoint.y + f.Direction.y * v
        * this.StartingPoint.z + this.Direction.z * r = f.StartingPoint.z + f.Direction.z * v
        *
        * 2. this needs to be rearranged to:
        * this.Direction * r - f.Direction * v = f.StartingPoint - this.StartingPoint
        * neater form:
        * this.Direction * r + negFDirection * v = SubFThis
        * expanded:
        * this.Direction.x * r + negFDirection.x * v = SubFThis.x
        * this.Direction.y * r + negFDirection.y * v = SubFThis.y
        * this.Direction.z * r + negFDirection.z * v = SubFThis.z
        *
        * 3. now the Solutions are given by
        *
        * r = det((SubFThis.x, SubFTHis.y), (negFDirection.x, negFDirection.y)) /
        *     det((this.Direction.x, this.Direction.y), (negFDirection.x, negFDirection.y))
        *
        * v = det((this.Direction.x, this.Direction.y),(SubFThis.x, SubFThis.y)) /
        *     det((this.Direction.x, this.Direction.y), (negFDirection.x, negFDirection.y))
        *
        * or written out:
        * RSub = this.Direction.x * negFDirection.y - this.Direction.y * negFDirection.x
        *
        * r = SubFThis.x * negFDirection.y - SubFThis.y * negFDirection.x / RSub
        *
        * v = this.Direction.x * SubFThis.y - this.Direction.y * SubFThis.x
        *
        */
        Vector3D negFDirection = f.direction.scalarMultiplication(-1); // the direction of but negative
        double RSub = this.direction.x * negFDirection.y - this.direction.y * negFDirection.x;
        if(RSub == 0) { //if the 2x2 doesn't have a solution
            return new LinearFunctionSolution(LinearFunctionRelation.SKEWED);
        }
        Vector3D SubFThis = f.startingPoint.sub(this.startingPoint);
        double r = SubFThis.x * negFDirection.y - SubFThis.y * negFDirection.x;
        double v = this.direction.x * SubFThis.y - this.direction.y * SubFThis.x;
        //4. check the final equation:
        //this.Direction.z * r + negFDirection.z * v = SubFThis.z
        if(this.direction.z * r + negFDirection.z * v != SubFThis.z) {
            return new LinearFunctionSolution(LinearFunctionRelation.SKEWED);
        }
        Vector3D Intersection = this.startingPoint.add(this.direction.scalarMultiplication(r));
        return new LinearFunctionSolution(LinearFunctionRelation.INTERSECT, Intersection);
    }

    /**
     * Function to get the point that is x times the direction vector away from the starting point
     * @param x the factor used on the direction vector
     * @return the point that is x times the direction vector away from the starting point
     */
    public Vector3D pointAt(int x) {
        return new Vector3D(this.startingPoint.add(this.direction.scalarMultiplication(x)));
    }

    /**
     * Funciton to get the value of direction
     * @return direction
     */
    public Vector3D getDirection() {
        return direction;
    }

    /**
     * Function to return the linear function in String form but without the direction vector. This is usefull when the
     * direction vector is the solution of an exercise
     * @return the linear funciton in String form without the dirction vector
     */
    public String withoutDirection() {
        return "x = " + startingPoint.toString() + " + r * (";
    }
}