package com.example.foodplanner.prsentation.calender.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;

import java.util.List;

public interface CalenderPresenter {
     void removeMealFromCalendar(LocalMeal meal);

   LiveData <List<LocalMeal>> getCalendarMeals();
}
