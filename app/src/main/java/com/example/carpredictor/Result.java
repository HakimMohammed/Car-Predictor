package com.example.carpredictor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

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
