//CURRENTLY NOT IN USE
package de.grb.droneproject.vectormath;

import de.grb.droneproject.vectormath.LinearFunctionRelation;
import de.grb.droneproject.vectormath.Vector3D;
/**
 * This is a class representing the solution to the problem of the relation of two
 * Linear Functions.
 * @author L. Janke
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
     * @param solution the relation of the two functions
     * @param point the point of the solution
     */
    public LinearFunctionSolution(LinearFunctionRelation solution, Vector3D point) {
        this.solution = solution;
        this.point = point;
    }

    /**
     * Constructor to Construct a Solution without a point
     * @param solution the relation of the two functions
     */
    public LinearFunctionSolution(LinearFunctionRelation solution) {
        this.solution = solution;
        this.point = new Vector3D();
    }
}
