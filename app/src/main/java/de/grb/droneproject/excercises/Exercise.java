package de.grb.droneproject.excercises;


/**
 * Abstract class that provides a common interface for exercises
 *
 * This generation follows a <a href="https://www.tutorialspoint.com/design_pattern/factory_pattern.htm">factory Design
 * Pattern</a> using an abstract class as the base. The execercise is then further divided down into different types
 * which have their own generators.
 */
public abstract class Exercise {

    /**
     * The function which lower classes will use to generate the resulting exercises.
     */
    public abstract void Generate();

    /**
     * Function that returns the exercise in String format as a german exercise in line with ABITUR standards.
     * @return a String that contains an exercise with the generated vectors
     */
    public abstract String AsStringExercise();

    /**
     * Function that returns the exercise in String format as a german exercise in line with ABITUR standards and also
     * includes the solution.
     * @return a String that contains an exercise with the generated vectors and the solution
     */
    public abstract String AsStringExerciseWSolution();
}
