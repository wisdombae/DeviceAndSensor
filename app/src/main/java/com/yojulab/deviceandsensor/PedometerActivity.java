package com.yojulab.deviceandsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PedometerActivity extends AppCompatActivity implements SensorEventListener, SeekBar.OnSeekBarChangeListener {

    private TextView textViewGx, textViewGy, textViewGz;
    private TextView textViewSteps;

    Button buttonResetCounter;

    private SensorManager sensorManager;
    private float acceleration, previousY, currentY;
    private int steps;

    int threshold = 0;
    SeekBar seekBarSensitive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        textViewGx = findViewById(R.id.textViewGx);
        textViewGy = findViewById(R.id.textViewGy);
        textViewGz = findViewById(R.id.textViewGz);

        textViewSteps = findViewById(R.id.textViewSteps);
        seekBarSensitive = findViewById(R.id.seekBarSensitive);
        seekBarSensitive.setProgress(9);
        seekBarSensitive.setOnSeekBarChangeListener(this);
        threshold = seekBarSensitive.getProgress();

        previousY = currentY = steps = 0;
        acceleration = 0.0f;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if(sensorManager != null){
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        currentY = y;
        if(Math.abs(currentY - previousY) > threshold){
            steps++;
            textViewSteps.setText(String.valueOf(steps));
        }

        textViewGx.setText(String.valueOf(x));
        textViewGy.setText(String.valueOf(y));
        textViewGz.setText(String.valueOf(z));

        previousY = y;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        threshold = seekBarSensitive.getProgress();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
