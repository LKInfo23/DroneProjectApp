package de.grb.droneproject.excercises;


/**
 * This class uses a <a href="https://www.tutorialspoint.com/design_pattern/factory_pattern.htm">factory</a>
 * to provide the infrastructure to generate concrete exercises of a specific type
 * @author L. Janke
 */
public class ExerciseFactory {

    /**
     * the type of this exercise factory
     */
    private ExerciseType type;

    /**
     * Constructor to construct the factory when no type is given. If no type is given, the type will be NULL
     */
    public ExerciseFactory() {
        this.type = null;
    }
    /**
     * Constructor to construct the factory with a type
     * @param type the type of the exercise
     */
    public ExerciseFactory(ExerciseType type) {
        this.type = type;
    }

    /**
     * Function to generate an execerice without a parameter for the type.
     * The type used will be the type of {@link ExerciseFactory#type}
     * @return returns the Exercise
     */
    public Exercise getGenerator() {
        return this.getGenerator(this.type);
    }

    /**
     * Function to generate the exercise given a parameter for the type
     * @param ExerciseType the type used when generating the exercise
     * @return returns the Exercise
     */
    public Exercise getGenerator(ExerciseType ExerciseType) {
        switch (ExerciseType) {
            case Addition:
                return new AdditionExerciseGenerator();
            case Subtraction:
                return new SubtractionExerciseGenerator();
            case Orthogonal:
                return new OrthogonalExerciseGenerator();
            case Parkour:
                return new ParkourExerciseGenerator();
            default:
                return null;
        }
    }

    /**
     * Function to get {@link ExerciseFactory#type}
     * @return the type of the exercise
     */
    public ExerciseType getType() {
        return type;
    }
}