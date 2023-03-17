//CURRENTLY NOT IN USE
package de.grb.droneproject.vectormath;

import de.grb.droneproject.vectormath.LinearFunctionRelation;
import de.grb.droneproject.vectormath.Vector3D;
/**
 * This is a class representing the solution to the problem of the relation of two
 * Linear Functions.
 */
public class LinearFunctionSolution {
    /**
     * The relation of the two functions
     */
    LinearFunctionRelation solution;
    /**
     * If the two functions intersect at point this will be the intersection point
     */
    Vector3D point;

    /**
     * Constructor to construct a solution with a point
     */
    public LinearFunctionSolution(LinearFunctionRelation Solution, Vector3D Point) {
        this.solution = Solution;
        this.point = Point;
    }

    /**
     * Constructor to Construct a Solution without a point
     * @param Solution
     */
    public LinearFunctionSolution(LinearFunctionRelation Solution) {
        this.solution = Solution;
        this.point = new Vector3D();
    }
}
