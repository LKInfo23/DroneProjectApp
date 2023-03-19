//CURRENTLY NOT IN USE
package de.grb.droneproject.excercises;

import de.grb.droneproject.vectormath.LinearFunction;

/**
 * class that provides the concrete structure of the orthogonality exercises.
 * It consists of two linear functions. One that the students see and one that is orthogonal on the XY-plane to it.
 * @author L. Janke
 */
public abstract class OrthogonalExercise extends Exercise{
    /**
     * the first linear function. This is the one that students should see
     */
    LinearFunction g1;
    /**
     * the second linear function this is orthogonal to the first on the XY-plane. This is the one that students should give as an answer
     */
    LinearFunction g2;
}
