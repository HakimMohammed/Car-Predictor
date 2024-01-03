package com.example.carpredictor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    public static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView splashImageView = findViewById(R.id.imageView4);

        Glide.with(this)
                .asGif()
                .load(R.drawable.car_driving_62) // Replace with your GIF file name without extension
                .into(splashImageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomePage.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);



    }
}