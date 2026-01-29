package com.example.foodplanner.utils;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import com.example.foodplanner.R;
import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderUtil {

    public static void showCalendarDialog(Meal meal, Context context, MealRepo mealRepo) {

        Dialog dialog = new Dialog(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) (8 * context.getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(context.getResources().getColor(R.color.primary));
        bg.setStroke(2, context.getResources().getColor(R.color.white));
        bg.setCornerRadius(8);
        layout.setBackground(bg);

        CalendarView calendarView = new CalendarView(context);
        calendarView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        calendarView.setBackgroundColor(context.getResources().getColor(R.color.white));
        calendarView.setWeekDayTextAppearance(android.R.style.TextAppearance_Small);
        layout.addView(calendarView);
        dialog.setContentView(layout);
        View rootView = ((Activity) context)
                .getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth, 0, 0, 0);
            selectedDate.set(Calendar.MILLISECOND, 0);

            Log.d("showCalendarDialog", "showCalendarDialog: "+mealRepo);
            if (selectedDate.before(today)) {
                showSnack(view,"You can't add to previous day");
            } else  {
                Log.d("showCalendarDialog", "showCalendarDialog: "+meal);

                SimpleDateFormat sdf =
                        new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(selectedDate.getTime());

                Log.d("showCalendarDialog", "showCalendarDialog: "+meal);

                mealRepo.addMealToCalendar(meal, formattedDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    showSnack(rootView,"Meal added to calendar");
                                    dialog.dismiss();
                                },
                                throwable -> {
                                    Log.e("CalenderUtil", "Failed to add meal to calendar", throwable);
                                    showSnack(rootView,"Failed to add meal");
                                }
                        );

                showSnack(rootView,"meal added to calendar");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
