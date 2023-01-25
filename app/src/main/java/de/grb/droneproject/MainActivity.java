package de.grb.droneproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import de.grb.droneproject.excercises.AdditionExerciseGenerator;
import de.grb.droneproject.excercises.ExerciseFactory;
import de.grb.droneproject.excercises.ExerciseType;
import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.vectormath.Vector3D;

import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button checkButton;
    private ToggleButton droneSwitch;
    private TextView firstVector;
    private TextView secondVector;
    private TextView resultText;
    private EditText inputX;
    private EditText inputY;
    private EditText inputZ;
    private ExerciseFactory ef;
    private Animation out = new AlphaAnimation(1.0f, 0.0f);
    private Button droneButton;
    private boolean goNext;
    private DroneCommunicator droneCommunicator;
    private boolean fly = true;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        initVariables();
        initExercise();
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
            if (fly) {
                droneCommunicator.send("takeoff");
            } else {
                droneCommunicator.send("land");
            }
            fly = !fly;
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
                double x = Double.parseDouble(inputX.getText().toString());
                double y = Double.parseDouble(inputY.getText().toString());
                double z = Double.parseDouble(inputZ.getText().toString());
                resultText.setVisibility(TextView.VISIBLE);
                // checks if input is correct
                if (exerciseGen.isSolution(new Vector3D(x, y, z))) {
                    resultText.setTextColor(Color.rgb(0, 255, 0));
                    resultText.setText("Richtig");
                    droneButton.setEnabled(true);
                    goNext = true;
                    checkButton.setText("Next");
                } else {
                    resultText.setTextColor(Color.RED);
                    resultText.setText("Falsch, " + exerciseGen.getSolution().toString() + " wäre richtig gewesen");
                    goNext = true;
                }
                resultText.startAnimation(out);
            }
        });

        droneButton.setOnClickListener(v -> {
            if (!goNext) {
                resultText.setVisibility(TextView.VISIBLE);
                resultText.setTextColor(Color.RED);
                resultText.setText("Du musst erst die Aufgabe lösen oder die drone ist nicht verbunden");
                resultText.startAnimation(out);
            }
            if (goNext) {
                if (droneCommunicator.isConnected()) {
                    resultText.setVisibility(TextView.VISIBLE);
                    resultText.setTextColor(Color.GREEN);
                    resultText.setText("Drone wird gestartet");
                    resultText.startAnimation(out);
                    droneCommunicator.send(generateGoToCommand(exerciseGen.getSolution()));
                    droneButton.setEnabled(false);
                } else {
                    resultText.setVisibility(TextView.VISIBLE);
                    resultText.setTextColor(Color.RED);
                    resultText.setText("Drone ist nicht verbunden, versuche es erneut");
                    resultText.startAnimation(out);
                    droneCommunicator.connectToDrone();
                }
            }
        });

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
        droneCommunicator = new DroneCommunicator("192.168.10.1", 8889, this.getApplicationContext());
        checkButton = findViewById(R.id.checkButton);
        droneButton = findViewById(R.id.droneButton);
        droneButton.setEnabled(false);
        firstVector = findViewById(R.id.firstVector);
        secondVector = findViewById(R.id.secondVector);
        resultText = findViewById(R.id.resultText);
        inputX = findViewById(R.id.inputX);
        inputY = findViewById(R.id.inputY);
        inputZ = findViewById(R.id.inputZ);
        ef = new ExerciseFactory(ExerciseType.Addition);
        droneSwitch = findViewById(R.id.droneSwitch);
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