package com.example.carpredictor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;

public class WelcomePage extends AppCompatActivity {

    Button predictButton;
    private EditText mpg, displacement, horsepower, weight, acceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        predictButton = (Button) findViewById(R.id.predict_btn);
        mpg = (EditText) findViewById(R.id.MPG);
        displacement = (EditText) findViewById(R.id.Displacement);
        horsepower = (EditText) findViewById(R.id.HorsePower);
        weight = (EditText) findViewById(R.id.Weight);
        acceleration = (EditText) findViewById(R.id.Acceleration);
        setFocusChangeListeners();

        ConstraintLayout constraintLayout = findViewById(R.id.welcome);

        // Check the current night mode setting
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Dark mode is enabled, set green background
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.bg_primary));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // Light mode is enabled, set white background
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
                break;
        }


        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mpgValue = mpg.getText().toString();
                String displacementValue = displacement.getText().toString();
                String horsepowerValue = horsepower.getText().toString();
                String weightValue = weight.getText().toString();
                String accelerationValue = acceleration.getText().toString();

                // Validate input
                if (mpgValue.isEmpty() || displacementValue.isEmpty() || accelerationValue.isEmpty() || weightValue.isEmpty() || horsepowerValue.isEmpty()) {

                    // Set error messages for each empty field
                    if (mpgValue.isEmpty()) {
                        ((TextInputLayout) findViewById(R.id.MPG_inputLayout)).setError("MPG is required");
                    }
                    if (displacementValue.isEmpty()) {
                        ((TextInputLayout) findViewById(R.id.Displacement_inputLayout)).setError("Displacement is required");
                    }
                    if (horsepowerValue.isEmpty()) {
                        ((TextInputLayout) findViewById(R.id.HorsePower_inputLayout)).setError("Horse Power is required");
                    }
                    if (weightValue.isEmpty()) {
                        ((TextInputLayout) findViewById(R.id.Weight_inputLayout)).setError("Weight is required");
                    }
                    if (accelerationValue.isEmpty()) {
                        ((TextInputLayout) findViewById(R.id.Acceleration_inputLayout)).setError("Acceleration is required");
                    }
                    Toast.makeText(WelcomePage.this, "All fields are required", Toast.LENGTH_LONG).show();

                } else {
                    Car car = new Car(mpgValue, displacementValue, accelerationValue, weightValue, horsepowerValue, "");

                    Intent intent = new Intent(WelcomePage.this, Algorithms.class);
                    intent.putExtra("car", car);
                    startActivity(intent);

                }

            }
        });
    }
    private void setFocusChangeListeners() {
        // Focus change listener for MPG
        mpg.setOnFocusChangeListener(getFocusChangeListener((TextInputLayout) findViewById(R.id.MPG_inputLayout)));
        acceleration.setOnFocusChangeListener(getFocusChangeListener((TextInputLayout) findViewById(R.id.Acceleration_inputLayout)));
        horsepower.setOnFocusChangeListener(getFocusChangeListener((TextInputLayout) findViewById(R.id.HorsePower_inputLayout)));
        weight.setOnFocusChangeListener(getFocusChangeListener((TextInputLayout) findViewById(R.id.Weight_inputLayout)));
        displacement.setOnFocusChangeListener(getFocusChangeListener((TextInputLayout) findViewById(R.id.Displacement_inputLayout)));
        // Repeat for other EditText fields...
    }

    private View.OnFocusChangeListener getFocusChangeListener(final TextInputLayout textInputLayout) {
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Clear the error message when the EditText loses focus
                    textInputLayout.setError(null);
                }
            }
        };
    }


}
