package com.example.carpredictor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

public class KNN extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recieverintent = getIntent();
        Car receivedCar = recieverintent.getParcelableExtra("car");
        String k = recieverintent.getStringExtra("k");
        int kValue = Integer.parseInt(k);
        // send origin to result
        //String Origin = this.knn_Algorithm(receivedCar, kValue);
        HashMap<String, String> result = null;
        try {
            result = this.predict_origin(receivedCar, kValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Intent senderIntent = new Intent(KNN.this, Result.class);
        //senderIntent.putExtra("result", Origin);
        senderIntent.putExtra("result", result.get("origin"));
        senderIntent.putExtra("precision", result.get("precision"));
        senderIntent.putExtra("accuracy", result.get("accuracy"));
        senderIntent.putExtra("fScore", result.get("fScore"));
        senderIntent.putExtra("recall", result.get("recall"));
        startActivity(senderIntent);
    }

    public HashMap<String, String> predict_origin(Car car, int k) throws Exception {
        // Initialize result list
        HashMap<String, String> results = new HashMap<String, String>();

        // Load the dataset from a .arff file
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("Cars.arff")));
        Instances data = new Instances(reader);

        // Set the class attribute (assuming it's the last attribute)
        data.setClassIndex(data.numAttributes() - 1);

        // Create a k-NN classifier (IBk)
        IBk knn = new IBk();
        knn.setKNN(k);
        knn.buildClassifier(data);

        // Make predictions for new instances
        double[] newInstValues = {car.getMpg(), car.getDisplacement(), car.getHorsePower(), car.getWeight(), car.getAcceleration()};
        Instance newInst = new DenseInstance(1.0, newInstValues);
        newInst.setDataset(data);

        // Classify the new instance
        double prediction = knn.classifyInstance(newInst);

        // Print the predicted class
        System.out.println("Predicted class: " + data.classAttribute().value((int) prediction));

        // add the origin of the car to results
        results.put("origin", data.classAttribute().value((int) prediction));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");  // Adjust the pattern to control the number of decimal places

        // get the valutation of the algorithm
        Evaluation evaluation = new Evaluation(data);
        evaluation.evaluateModel(knn, data);
        //Precision
        results.put("precision", String.valueOf(decimalFormat.format(evaluation.precision((int) prediction) * 100)));
        //Recall
        results.put("recall", String.valueOf(decimalFormat.format(evaluation.recall((int) prediction) * 100)));
        //F-Score
        results.put("fScore", String.valueOf(decimalFormat.format(evaluation.fMeasure((int) prediction) * 100)));
        //Accuracy
        results.put("accuracy", String.valueOf(decimalFormat.format(evaluation.pctCorrect())));

        return results;
    }
}
