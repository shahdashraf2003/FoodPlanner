package com.example.foodplanner.prsentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.prsentation.auth.login.LoginActivity;


public class Splash extends AppCompatActivity {

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_splash);
        ImageView iv_splash = findViewById(R.id.iv_splash);
        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        iv_splash.startAnimation(scaleAnimation);
        handler.postDelayed(() -> {
            Intent intent = new Intent(Splash.this, LoginActivity.class);
            startActivity(intent
            );
        }, 5000);


    }
}