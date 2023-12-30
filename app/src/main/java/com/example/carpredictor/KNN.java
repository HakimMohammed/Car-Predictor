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
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class KNN extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recieverintent = getIntent();
        Car receivedCar = recieverintent.getParcelableExtra("car");
        String k = recieverintent.getStringExtra("k");
        int kValue = Integer.parseInt(k);
        // send origin to result
        String Origin = this.knn_Algorithm(receivedCar, kValue);

        Intent senderIntent = new Intent(KNN.this, Result.class);
        senderIntent.putExtra("result", Origin);
        startActivity(senderIntent);
    }

    public String knn_Algorithm(Car car, int k) {
        List<Car> list = this.readData();
        String origin = "";

        ArrayList<Double> distancesEuclidean = new ArrayList<>();
        ArrayList<Double> distancesManhattan = new ArrayList<>();

        for (Car item : list) {
            distancesManhattan.add(car.distanceManhattan(item));
            distancesEuclidean.add(car.distanceEuclidean(item));
        }

        List<Integer> indicesManhattan = getIndicesOfKMinValues(distancesManhattan, k);
        List<Integer> indicesEuclidean = getIndicesOfKMinValues(distancesEuclidean, k);

        String[] originsManhattan = getOrigins(list, indicesManhattan);
        String[] originsEuclidean = getOrigins(list, indicesEuclidean);

        // Find the majority class
        String majorityOriginM = findMajorityClass(originsManhattan);
        String majorityOriginE = findMajorityClass(originsEuclidean);

        // Choose the origin based on the majority class
        origin = (majorityOriginE.equals(majorityOriginM)) ? majorityOriginE : "Undetermined";
        Log.d("Algorithm","Origin well predicted"+origin);

        return origin;
    }

    private List<Integer> getIndicesOfKMinValues(List<Double> distances, int k) {
        List<Integer> indices = new ArrayList<>();
        List<Double> copyDistances = new ArrayList<>(distances);

        for (int i = 0; i < k; i++) {
            int minIndex = copyDistances.indexOf(Collections.min(copyDistances));
            indices.add(minIndex);
            copyDistances.set(minIndex, Double.MAX_VALUE); // Mark the minimum as "visited"
        }

        return indices;
    }

    private String[] getOrigins(List<Car> list, List<Integer> indices) {
        String[] origins = new String[indices.size()];
        int i = 0;

        for (int index : indices) {
            origins[i++] = list.get(index).getOrigin();
        }

        return origins;
    }

    private String findMajorityClass(String[] classes) {
        // Find the majority class in an array of classes
        // If there's a tie or the array is empty, return "Undetermined"

        if (classes.length == 0) {
            return "Undetermined";
        }

        int maxCount = 0;
        String majorityClass = "Undetermined";

        for (String currentClass : classes) {
            int count = 0;
            for (String otherClass : classes) {
                if (currentClass.equals(otherClass)) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                majorityClass = currentClass;
            }
        }

        return majorityClass;
    }

    private ArrayList<Car> readData() {
        ArrayList<Car> list = new ArrayList<Car>();
        try {
            // Get Data from .csv file
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("Cars.txt")));
            String ligne = null;
            Car car = null;
            String[] values = null;


            while ((ligne = reader.readLine()) != null) {
                values = ligne.split(",");
                Log.d("reading", "Input string: [" + values[0] + "]");
                car = new Car(values[0],values[1],values[2],values[3],values[4], values[5]);
                list.add(car);
            }
        } catch (IOException e) {
            Toast.makeText(KNN.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        Log.d("ReadData","Data well recieved"+list.size());
        return list;
    }

}
