package com.example.carpredictor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Algorithms extends AppCompatActivity {
    Button DecisionTreeButton, KNNButton, BaysButton;
    EditText k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.algorithms);

        Intent intent = getIntent();
        Car receivedCar = intent.getParcelableExtra("car");


        // Decision Tree Algorithm
        DecisionTreeButton = (Button) findViewById(R.id.decision_tree_btn);
        /*DecisionTreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Algorithms.this, DecisionTree.class);
                intent.putExtra("car", receivedCar);
                startActivity(intent);
            }
        });*/

        // KNN Algorithm
        KNNButton = (Button) findViewById(R.id.knn_btn);
        KNNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout knnLayout = findViewById(R.id.containerLayoutKNN);
                if (knnLayout.getVisibility() != View.VISIBLE) {
                    knnLayout.setVisibility(View.VISIBLE);
                } else {
                    knnLayout.setVisibility(View.INVISIBLE);
                }
            }
        });

        Button KNNButtonValue = findViewById(R.id.submitKNN);
        KNNButtonValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k = (EditText) findViewById(R.id.k);
                String kValue = k.getText().toString();
                if (kValue.isEmpty()) {
                    Toast.makeText(Algorithms.this, "knn field is required", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(Algorithms.this, KNN.class);
                    intent.putExtra("car", receivedCar);
                    intent.putExtra("k", kValue);
                    startActivity(intent);
                }
            }
        });

        // Bays Network Algorithm
        BaysButton = (Button) findViewById(R.id.bays_btn);
        BaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Algorithms.this, BaysNetwork.class);
                intent.putExtra("car", receivedCar);
                startActivity(intent);
            }
        });

    }
}
