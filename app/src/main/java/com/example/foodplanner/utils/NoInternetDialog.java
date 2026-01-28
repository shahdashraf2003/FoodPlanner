package com.example.foodplanner.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;

public class NoInternetDialog extends Dialog {

    private LottieAnimationView lottieNoInternet;

    public NoInternetDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_no_internet, null, false);
        setContentView(view);
        lottieNoInternet = view.findViewById(R.id.lottieNoInternet);
        setCancelable(false);
    }

    public void showDialog() {
        if (!isShowing()) show();
        if (lottieNoInternet != null) lottieNoInternet.playAnimation();
    }

    public void hideDialog() {
        if (isShowing()) dismiss();
    }
}
