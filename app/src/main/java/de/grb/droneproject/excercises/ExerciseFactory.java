package de.grb.droneproject.excercises;

public class ExerciseFactory {

    private ExerciseType type;

    public ExerciseFactory() {

    }

    public ExerciseFactory(ExerciseType type) {
        this.type = type;
    }

    public Exercise getGenerator() {
        return this.getGenerator(this.type);
    }

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

    public ExerciseType getType() {
        return type;
    }
}