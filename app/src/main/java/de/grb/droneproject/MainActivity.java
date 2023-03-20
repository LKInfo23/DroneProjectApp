package de.grb.droneproject;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import de.grb.droneproject.excercises.AdditionExerciseGenerator;
import de.grb.droneproject.excercises.ExerciseFactory;
import de.grb.droneproject.excercises.ExerciseType;
import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.vectormath.Vector3D;

import java.util.Arrays;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    // UI elements
    private Button checkButton;
    private ToggleButton droneSwitch;
    private TextView firstVector;
    private TextView secondVector;
    private TextView resultText;
    private EditText inputX;
    private EditText inputY;
    private EditText inputZ;
    private Button droneButton;
    // colors for the text
    private final int colorGreen = Color.rgb(45, 183, 112);
    private final int colorRed = Color.rgb(175, 35, 35);
    private final int colorNone = Color.argb(0, 0, 0, 0);
    // used to check if the user has entered the correct answer
    private boolean goNext;
    // instance of the DroneCommunicator
    private DroneCommunicator droneCommunicator;
    // used to enter debug mode
    private double[] debugArray = new double[]{1, 8, 7};

    /**
     * Starts the activity and initializes the UI elements. After that it calls the initExercise method to start the first exercise.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        System.out.println("started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initVariables();
        initExercise();

        /*
        The following navigation bar currently not in use, as at the time of the initial release,
        there is only addition exercises.

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.plus);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.minus) {
                startActivity(new Intent(MainActivity.this, SubtractionActivity.class));
                return true;
            }
            return false;
        });

        */
    }

    /**
     * Destroys the Activity and disconnects the Drone.
     */
    @Override
    protected void onDestroy() {
        droneCommunicator.disconnect();
        super.onDestroy();
    }

    /**
     * Initializes the exercises and begins the "game" loop.
     */
    @SuppressLint("SetTextI18n")
    private void initExercise() {
        ExerciseFactory ef = new ExerciseFactory(ExerciseType.Addition);
        AdditionExerciseGenerator exerciseGen = (AdditionExerciseGenerator) ef.getGenerator();
        generateAndFillNewExercise(exerciseGen);


        // listener
        checkButton.setOnClickListener(v -> {
            handleCheckButton(exerciseGen);
        });
        // listener for the "send to drone" button
        droneButton.setOnClickListener(v -> {
            if (!goNext) showText("Du musst erst die Aufgabe lösen und die Drohne verbinden", colorGreen);
            if (goNext) {
                if (droneCommunicator.isConnected()) {
                    showText("Drohne fliegt zum Ziel", colorGreen);
                    droneCommunicator.send(generateGoToCommand(exerciseGen.getSolution()));
                    droneButton.setEnabled(false);
                } else {
                    showText("Drohne ist nicht verbunden", colorRed);
                    droneCommunicator.connectToDrone();
                }
            }
        });
        // listener for button that makes the app connect to the drone
        droneSwitch.setOnClickListener(v -> {
            // if the switch is toggled on, the app will try to connect to the drone
            if (droneSwitch.isChecked()) {
                droneCommunicator = Keeper.getDroneCommunicator();
                if (droneCommunicator.connectToDrone()) {
                    showText("Drohne verbunden", colorGreen);
                    // tries to start the drone
                    String takeoff = droneCommunicator.sendAndReceive("takeoff");
                    // if the drone could not start, the app will show an error message
                    if (takeoff.equalsIgnoreCase("error")) {
                        handleError("Drohne konnte nicht starten. Überprüfe die Akkuladung.");
                    }
                    // if the drone could not be connected to, the app will show an error message
                } else {
                    handleError("Verbindung gescheitert");
                }
                // if the switch is toggled off, the app will try to land the drone
            } else {
                if (droneCommunicator != null) droneCommunicator.sendAndReceive("land");
                // this isn't really an error but red text looks better in this context
                handleError("Verbindung getrennt");
            }
        });

    }

    /**
     * Generates a new exercise and fills the UI elements with the new exercise.
     *
     * @param exerciseGen The exercise generator that will be used to generate the new exercise.
     */
    private void handleCheckButton(AdditionExerciseGenerator exerciseGen) {
        // goNext = true means that the next exercise will be generated
        if (goNext) {
            // generate new exercise
            generateAndFillNewExercise(exerciseGen);

        } else {
            // gets input
            double[] xyzDoubles = inputToDoubleArray();
            // checks if input is correct
            goNext = checkSolution(exerciseGen, xyzDoubles);

        }
    }

    /**
     * Gets the input from the UI elements and converts it to a double array.
     *
     * @return The input from inputX, inputY and inpuntZ as a double array.
     */
    private double[] inputToDoubleArray() {
        double[] xyzDoubles = {Double.NaN, Double.NaN, Double.NaN};
        String[] xyzStrings = {
                inputX.getText().toString(),
                inputY.getText().toString(),
                inputZ.getText().toString()
        };

        try {
            for (int i = 0; i < 3; i++) xyzDoubles[i] = Double.parseDouble(xyzStrings[i]);
        } catch (NumberFormatException ignored) {
        }
        return xyzDoubles;
    }

    /**
     * Checks if the input is correct.
     *
     * @param exerciseGen The exercise generator that will be used to check the input.
     * @param xyz         The user input as a double array.
     * @return True if the input is correct, false if the input is incorrect.
     */
    private boolean checkSolution(AdditionExerciseGenerator exerciseGen, double[] xyz) {
        if (exerciseGen.isSolution(new Vector3D(xyz))) {
            showText("Richtig", colorGreen);
            droneButton.setEnabled(droneSwitch.isChecked());
            checkButton.setText("Next");
            return true;
        } else {
            if (Arrays.equals(xyz, debugArray)) startActivity(new Intent(MainActivity.this, DebugActivity.class));
            showText("Falsch, " + exerciseGen.getSolution().toString() + " wäre richtig gewesen", colorRed);
            return false;
        }
    }

    /**
     * Generates a new exercise and fills the UI elements with the new exercise.
     *
     * @param exerciseGen The exercise generator that will be used to generate the new exercise.
     */
    private void generateAndFillNewExercise(AdditionExerciseGenerator exerciseGen) {
        exerciseGen.Generate();
        firstVector.setText(exerciseGen.getFirstVector().toTextView());
        secondVector.setText(exerciseGen.getSecondVector().toTextView());
        goNext = false;
        checkButton.setText("Check");
        droneButton.setEnabled(false);
        inputX.setText("");
        inputY.setText("");
        inputZ.setText("");
    }

    /**
     * Shows an error message.
     *
     * @param text The error message that will be shown.
     */
    private void handleError(String text) {
        showText(text, colorRed);
        droneSwitch.setChecked(false);
        droneCommunicator.disconnect();
    }

    /**
     * Shows a String in the specified color.
     *
     * @param text  The text that will be shown.
     * @param color The color of the text.
     */
    private void showText(String text, int color) {
        resultText.setVisibility(TextView.VISIBLE);
        resultText.setTextColor(color);
        resultText.setText(text);
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(resultText, "textColor",
                color, colorNone);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(5000);
        colorAnim.start();
    }

    /**
     * Generates a command for the drone to go to a specific position.
     *
     * @param v The position the drone should go to.
     * @return The command for the drone to go to the specified position.
     */
    private String generateGoToCommand(Vector3D v) {
        return String.format("go %s %s %s 80", v.getX() * 100, v.getY() * 100, v.getZ() * 100);
    }

    /**
     * Initializes all the variables.
     */
    private void initVariables() {
        checkButton = findViewById(R.id.checkButton);
        droneButton = findViewById(R.id.droneButton);
        droneButton.setEnabled(false);
        firstVector = findViewById(R.id.firstVector);
        secondVector = findViewById(R.id.secondVector);
        resultText = findViewById(R.id.resultText);
        inputX = findViewById(R.id.inputX);
        inputY = findViewById(R.id.inputY);
        inputZ = findViewById(R.id.inputZ);
        droneSwitch = findViewById(R.id.droneSwitch);
        droneSwitch.setChecked(false);
        // this is done so we can send network requests on the main thread
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    }

}