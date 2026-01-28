package com.example.foodplanner.prsentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.local.SessionManager;
import com.example.foodplanner.prsentation.auth.login.view.LoginActivity;
import com.airbnb.lottie.LottieAnimationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
            SessionManager sessionManager = new SessionManager(this);

            sessionManager.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(user -> openMainActivity(), throwable -> {}, () -> {
                        if(sessionManager.isGuest()) {
                            openMainActivity();
                        } else {
                            openLoginActivity();
                        }
                    });

        }, 5000);


    }

    public void openMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
public void openLoginActivity() {
    startActivity(new Intent(this, LoginActivity.class));
    finish();

}

}