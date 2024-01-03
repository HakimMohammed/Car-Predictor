package com.example.carpredictor;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Result extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        ConstraintLayout constraintLayout = findViewById(R.id.resultPage);
        CardView cardView = findViewById(R.id.card);

        // Check the current night mode setting
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Dark mode is enabled, set green background
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.bg_primary));
                cardView.setBackgroundColor(getResources().getColor(R.color.bg_secondary));
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // Light mode is enabled, set white background
                constraintLayout.setBackgroundColor(getResources().getColor(R.color.white));
                cardView.setBackgroundColor(getResources().getColor(R.color.light_cyan));
                break;
        }

        Button redirectToAlgoritms = findViewById(R.id.redirectToAlgorithms);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Make the button visible after 3 seconds
                redirectToAlgoritms.setVisibility(View.VISIBLE);
            }
        }, 3000);

        redirectToAlgoritms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DestinationActivity
                Intent intent = new Intent(Result.this, Algorithms.class);
                startActivity(intent);
            }
        });

        Intent recieverintent = getIntent();
        String origin = recieverintent.getStringExtra("result");
        String precision = recieverintent.getStringExtra("precision");
        String fScore = recieverintent.getStringExtra("fScore");
        String accuracy = recieverintent.getStringExtra("accuracy");
        String recall = recieverintent.getStringExtra("recall");


        TextView result = findViewById(R.id.result);
        result.setText(origin);

        TextView precisionTV = findViewById(R.id.precision);
        precisionTV.setText(precisionTV.getText().toString() + precision + "%");
        TextView accuracyTV = findViewById(R.id.accuracy);
        accuracyTV.setText(accuracyTV.getText().toString() + accuracy + "%");
        TextView recallTV = findViewById(R.id.recall);
        recallTV.setText(recallTV.getText().toString() + recall + "%");
        TextView fScoreTV = findViewById(R.id.fScore);
        fScoreTV.setText(fScoreTV.getText().toString() + fScore + "%");
    }
}
