package de.grb.droneproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import de.grb.droneproject.networking.DroneCommunicator;
import de.grb.droneproject.util.Logger;

public class DebugActivity extends AppCompatActivity {

    // variables representing the UI elements
    private Button batteryButton;
    private Button reconnectButton;
    private Button flyButton;
    private Button backButton;
    private EditText xValue;
    private EditText yValue;
    private EditText zValue;
    private TextView droneLog;
    private TextView currentSpeedView;
    private SeekBar speedBar;
    // droneCommunicator and logger are used to communicate with the drone and to log the output
    private DroneCommunicator droneCommunicator = Keeper.getDroneCommunicator();
    private Logger logger = Keeper.getLogger();
    // speed is used to set the speed of the drone
    private int speed = 10;

    /**
     * Called when the activity is starting.  This is where most initialization
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        initVariables();
    }

    /**
     * Initializes the variables representing the UI elements.
     */
    private void initVariables() {

        this.batteryButton = findViewById(R.id.batteryButton);
        batteryButton.setOnClickListener(v -> {
            droneCommunicator.sendAndReceive("battery?");
            this.droneLog.setText(logger.getLog());
        });
        this.reconnectButton = findViewById(R.id.reconnectButton);
        reconnectButton.setOnClickListener(v -> {
            droneCommunicator.connectToDrone();
        });
        this.flyButton = findViewById(R.id.flyButton);
        flyButton.setOnClickListener(v -> {
            droneCommunicator.sendAndReceive("go " + xValue.getText() + " " + yValue.getText() + " " + zValue.getText() + " " + speed);
            this.droneLog.setText(logger.getLog());
        });
        this.backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            startActivity(new Intent(DebugActivity.this, MainActivity.class));
        });
        this.xValue = findViewById(R.id.xValue);
        this.yValue = findViewById(R.id.yValue);
        this.zValue = findViewById(R.id.zValue);
        this.droneLog = findViewById(R.id.droneLog);
        droneLog.setMovementMethod(new ScrollingMovementMethod());
        this.speedBar = findViewById(R.id.speedBar);
        int minSpeed = 10;
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed = progress + minSpeed;
                currentSpeedView.setText((progress + minSpeed)+ " cm/s");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        this.currentSpeedView = findViewById(R.id.currentSpeedView);


    }
}
