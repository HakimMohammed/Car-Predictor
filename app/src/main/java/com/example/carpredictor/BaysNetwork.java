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
import java.util.HashMap;
import java.util.List;

public class BaysNetwork extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent recieverintent = getIntent();
        Car receivedCar = recieverintent.getParcelableExtra("car");

        String Origin = this.bayes_Algorithm(receivedCar);

        Intent senderIntent = new Intent(BaysNetwork.this, Result.class);
        senderIntent.putExtra("result", Origin);
        startActivity(senderIntent);
    }

    public String bayes_Algorithm(Car car) {
        List<Car> list = this.readData();
        ArrayList<HashMap<String, Double>> probabilities = new ArrayList<HashMap<String, Double>>();

        ArrayList<Car> JA = new ArrayList<Car>();
        ArrayList<Car> EU = new ArrayList<Car>();
        ArrayList<Car> AM = new ArrayList<Car>();

        for (Car item : list)
            switch (item.getOrigin()) {
                case "japanese":
                    JA.add(item);
                    break;
                case "american":
                    AM.add(item);
                    break;
                case "european":
                    EU.add(item);
                    break;
                default:
                    break;
            }

        probabilities.add(classification(JA));    // 0=drugA
        probabilities.add(classification(AM));    // 1=drugB
        probabilities.add(classification(EU));    // 2=drugC

        probabilities.get(0).put("proba", (double) JA.size() / list.size());
        probabilities.get(1).put("proba", (double) AM.size() / list.size());
        probabilities.get(2).put("proba", (double) EU.size() / list.size());

        return appropriateOrigin(car, probabilities);
    }

    private static HashMap<String, Double> classification(ArrayList<Car> list) {
        HashMap<String, Double> hm = new HashMap<String, Double>();

        double aux1 = 0.0, aux2 = 0.0, aux3 = 0.0;
        //debut : MPG
        for (Car car : list)
            switch (car.getMpgCat().toLowerCase()) {
                case "high":
                    aux1++;
                    break;
                case "low":
                    aux2++;
                    break;
                default:
                    break;
            }

        hm.put("mpg_high", (double) aux1 / list.size());
        hm.put("mpg_low", (double) aux2 / list.size());
        aux1 = 0.0;
        aux2 = 0.0;
        aux3 = 0.0;
        //fin : MPG

        // Displacement
        for (Car car : list) {
            switch (car.getDisplacementCat().toLowerCase()) {
                case "high":
                    aux1++;
                    break;
                case "low":
                    aux2++;
                    break;
                default:
                    break;
            }
        }

        hm.put("displacement_high", (double) aux1 / list.size());
        hm.put("displacement_low", (double) aux2 / list.size());
        aux1 = 0.0;
        aux2 = 0.0;
        aux3 = 0.0;

// Horsepower
        for (Car car : list) {
            switch (car.getHorsepowerCat().toLowerCase()) {
                case "high":
                    aux1++;
                    break;
                case "low":
                    aux2++;
                    break;
                default:
                    break;
            }
        }

        hm.put("horsepower_high", (double) aux1 / list.size());
        hm.put("horsepower_low", (double) aux2 / list.size());
        aux1 = 0.0;
        aux2 = 0.0;
        aux3 = 0.0;

// Weight
        for (Car car : list) {
            switch (car.getWeightCat().toLowerCase()) {
                case "high":
                    aux1++;
                    break;
                case "low":
                    aux2++;
                    break;
                default:
                    break;
            }
        }

        hm.put("weight_high", (double) aux1 / list.size());
        hm.put("weight_low", (double) aux2 / list.size());
        aux1 = 0.0;
        aux2 = 0.0;
        aux3 = 0.0;

// Acceleration
        for (Car car : list) {
            switch (car.getAccelerationCat().toLowerCase()) {
                case "high":
                    aux1++;
                    break;
                case "low":
                    aux2++;
                    break;
                default:
                    break;
            }
        }

        hm.put("acceleration_high", (double) aux1 / list.size());
        hm.put("acceleration_low", (double) aux2 / list.size());
        aux1 = 0.0;
        aux2 = 0.0;
        aux3 = 0.0;


        return hm;
    }

    private static String appropriateOrigin(Car car, ArrayList<HashMap<String, Double>> probabilities) {
        HashMap<String, Double> origins = new HashMap<String, Double>();
        origins.put("japanese", calcProb(car, probabilities.get(0)));
        origins.put("american", calcProb(car, probabilities.get(1)));
        origins.put("european", calcProb(car, probabilities.get(2)));

        double maxVal = origins.get("japanese");
        String origin = "japanese";

        for (String key : origins.keySet())
            if (maxVal < origins.get(key)) {
                maxVal = origins.get(key);
                origin = key;
            }

        Log.d("appropriateOrigin","origin :"+origin);

        return origin;
    }

    private static double calcProb(Car car, HashMap<String, Double> hm) {
        double probability;

        String mpgCat = "mpg_" + String.valueOf(car.getMpgCat()).toLowerCase();
        String displacementCat = "displacement_" + String.valueOf(car.getDisplacementCat()).toLowerCase();
        String accelerationCat = "acceleration_" + String.valueOf(car.getAccelerationCat()).toLowerCase();
        String weightCat = "weight_" + String.valueOf(car.getWeightCat()).toLowerCase();
        String horsePowerCat = "horsepower_" + String.valueOf(car.getHorsepowerCat()).toLowerCase();

        probability = hm.get(mpgCat) * hm.get(displacementCat) * hm.get(accelerationCat) *
                hm.get(weightCat) * hm.get(horsePowerCat) * hm.get("proba");

        return probability;
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
                car = new Car(values[0], values[1], values[2], values[3], values[4], values[5]);
                list.add(car);
            }
        } catch (IOException e) {
            Toast.makeText(BaysNetwork.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
        Log.d("ReadData", "Data well recieved" + list.size());
        return list;
    }
}
