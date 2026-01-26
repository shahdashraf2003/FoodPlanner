package com.example.foodplanner.prsentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.prsentation.auth.login.view.LoginActivity;
import com.airbnb.lottie.LottieAnimationView;


public class Splash extends AppCompatActivity {
    LottieAnimationView lottie;

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_splash);
        lottie = findViewById(R.id.splash_lottie);
        lottie.playAnimation();
        handler.postDelayed(() -> {
            Intent intent = new Intent(Splash.this, LoginActivity.class);
            startActivity(intent
            );
        }, 5000);


    }
}