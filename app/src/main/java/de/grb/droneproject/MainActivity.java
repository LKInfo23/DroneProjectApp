package de.grb.droneproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import de.grb.droneproject.excercises.AdditionExerciseGenerator;
import de.grb.droneproject.excercises.ExerciseFactory;
import de.grb.droneproject.excercises.ExerciseType;
import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.vectormath.Vector3D;

public class MainActivity extends AppCompatActivity {

    private Button checkButton;
    private ToggleButton droneSwitch;
    private TextView firstVector;
    private TextView secondVector;
    private TextView resultText;
    private EditText inputX;
    private EditText inputY;
    private EditText inputZ;
    private final Animation out = new AlphaAnimation(1.0f, 0.0f);
    private Button droneButton;
    private boolean goNext;
    private DroneCommunicator droneCommunicator;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        initVariables();
        initExercise();

        /*
        The following navigation bar currently not in use, as at the time of the initial release,
        there is only addition exercises.
        */

        /*

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

    @Override
    protected void onDestroy() {
        droneCommunicator.disconnect();
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    private void initExercise() {
        ExerciseFactory ef = new ExerciseFactory(ExerciseType.Addition);
        AdditionExerciseGenerator exerciseGen = (AdditionExerciseGenerator) ef.getGenerator();
        exerciseGen.Generate();
        firstVector.setText(exerciseGen.getFirstVector().toTextView());
        secondVector.setText(exerciseGen.getSecondVector().toTextView());


        // listener
        checkButton.setOnClickListener(v -> {

            // goNext = true means that the next excercise will be generated
            if (goNext) {
                // generate new exercise
                exerciseGen.Generate();
                firstVector.setText(exerciseGen.getFirstVector().toTextView());
                secondVector.setText(exerciseGen.getSecondVector().toTextView());
                goNext = false;
                checkButton.setText("Check");
                droneButton.setEnabled(false);
                inputX.setText("");
                inputY.setText("");
                inputZ.setText("");

            } else {
                // gets input
                double[] xyzDoubles = {Double.NaN, Double.NaN, Double.NaN};
                String[] xyzStrings = {
                        inputX.getText().toString(),
                        inputY.getText().toString(),
                        inputZ.getText().toString()
                };

                for (int i = 0; i < 3; i++) {
                    try { xyzDoubles[i] = Double.parseDouble(xyzStrings[i]); }
                    catch (NumberFormatException ignored) {}
                }

                double x = xyzDoubles[0], y = xyzDoubles[1], z = xyzDoubles[2];

                resultText.setVisibility(TextView.VISIBLE);
                // checks if input is correct
                if (exerciseGen.isSolution(new Vector3D(x, y, z))) {
                    resultText.setTextColor(Color.rgb(0, 255, 0));
                    resultText.setText("Richtig");
                    droneButton.setEnabled(droneSwitch.isChecked());
                    checkButton.setText("Next");
                } else {
                    if(x == 1 && y == 8 && z == 7){
                        startActivity(new Intent(MainActivity.this, DebugActivity.class));
                    }
                    resultText.setTextColor(Color.RED);
                    resultText.setText("Falsch, " + exerciseGen.getSolution().toString() + " wäre richtig gewesen");
                }
                goNext = true;
                resultText.startAnimation(out);
            }
        });

        droneButton.setOnClickListener(v -> {
            if (!goNext) {
                showText("Du musst erst die Aufgabe lösen oder die drone ist nicht verbunden", Color.GREEN);
            }
            if (goNext) {
                if (droneCommunicator.isConnected()) {
                    showText("Drone fliegt zum Ziel", Color.GREEN);
                    droneCommunicator.send(generateGoToCommand(exerciseGen.getSolution()));
                    droneButton.setEnabled(false);
                } else {
                    showText("Drone ist nicht verbunden", Color.RED);
                    droneCommunicator.connectToDrone();
                }
            }
        });

        droneSwitch.setOnClickListener(v -> {
            if (droneSwitch.isChecked()) {
                droneCommunicator = Keeper.getDroneCommunicator();
                if (droneCommunicator.connectToDrone()) {
                    showText("Drone verbunden", Color.GREEN);
                    String takeoff = droneCommunicator.sendAndReceive("takeoff");
                    if (takeoff.equalsIgnoreCase("error")) {
                        handleError("Drone konnte nicht gestartet. Überprüfe die Akkuladung.");
                    }
                } else {
                    handleError("Drone konnte nicht verbunden werden");
                }
            } else {
                if (droneCommunicator != null) droneCommunicator.sendAndReceive("land");
                handleError("drone wurde getrennt");
            }
        });

    }

    private void handleError(String text) {
        showText(text, Color.RED);
        droneSwitch.setChecked(false);
        droneCommunicator.disconnect();
    }

    private void showText(String text, int color) {
        resultText.setVisibility(TextView.VISIBLE);
        resultText.setTextColor(color);
        resultText.setText(text);
        resultText.startAnimation(out);
    }

    private static int goValueCorrected(double value) {
        // for x = 0: 0
        // otherwise: 10 * x * ln(x) / 4 + 5
        return value == 0 ? 0 : (int) (10 * value * Math.log(value) / 4 + 5);
    }

    private String generateGoToCommand(Vector3D v) {
        return "go " + goValueCorrected(v.getX()) + " " + goValueCorrected(v.getY()) + " " + goValueCorrected(v.getZ()) + " 10";
    }

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
        ExerciseFactory ef = new ExerciseFactory(ExerciseType.Addition);
        droneSwitch = findViewById(R.id.droneSwitch);
        droneSwitch.setChecked(false);
        out.setDuration(5000);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                resultText.setVisibility(TextView.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public Context getAppContext() {
        return getApplicationContext();
    }


}