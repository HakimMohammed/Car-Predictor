package com.example.carpredictor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static int TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomePage.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);

        ConstraintLayout constraintLayout = findViewById(R.id.myapp);

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

    }
}