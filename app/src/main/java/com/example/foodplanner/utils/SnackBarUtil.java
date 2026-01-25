package com.example.foodplanner.utils;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.foodplanner.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtil {

    public static void showSnack(View view, String msg) {

        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();

        snackView.setBackgroundTintList(null);

        int primaryColor = ContextCompat.getColor(
                view.getContext(),
                R.color.primary
        );

        int borderColor = darkenColor(primaryColor);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(primaryColor);
        drawable.setCornerRadius(40f);
        drawable.setStroke(4, borderColor);

        snackView.setBackground(drawable);

        TextView tv =
                snackView.findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(20);
        tv.setMaxLines(2);
        tv.setGravity(Gravity.CENTER);

        snackbar.show();
    }

    private static int darkenColor(int color) {
        float factor = 0.85f;
        return Color.rgb(
                Math.round(Color.red(color) * factor),
                Math.round(Color.green(color) * factor),
                Math.round(Color.blue(color) * factor)
        );
    }
}
