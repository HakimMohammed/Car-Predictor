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

        TextView textView = findViewById(R.id.result);
        textView.setText(origin);
    }
}
