package com.example.carpredictor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {

    Button predictButton;
    private EditText mpg, displacement, horsepower, weight, acceleration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        predictButton = (Button) findViewById(R.id.predict_btn);


        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mpg = (EditText) findViewById(R.id.MPG);
                String mpgValue = mpg.getText().toString();
                displacement = (EditText) findViewById(R.id.Displacement);
                String displacementValue = displacement.getText().toString();
                horsepower = (EditText) findViewById(R.id.HorsePower);
                String horsepowerValue = horsepower.getText().toString();
                weight = (EditText) findViewById(R.id.Weight);
                String weightValue = weight.getText().toString();
                acceleration = (EditText) findViewById(R.id.Acceleration);
                String accelerationValue = acceleration.getText().toString();

                // Validate input
                if (mpgValue.isEmpty() || displacementValue.isEmpty() || accelerationValue.isEmpty() || weightValue.isEmpty() || horsepowerValue.isEmpty()) {
                    Toast.makeText(WelcomePage.this, "All fields are required", Toast.LENGTH_LONG).show();
                } else {
                    Car car = new Car(mpgValue, displacementValue, accelerationValue, weightValue, horsepowerValue , "");

                    Intent intent = new Intent(WelcomePage.this, Algorithms.class);
                    intent.putExtra("car", car);
                    startActivity(intent);

                }

            }
        });
    }

}
