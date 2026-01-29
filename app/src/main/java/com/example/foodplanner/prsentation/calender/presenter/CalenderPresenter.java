package com.example.foodplanner.prsentation.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface CalenderPresenter {
     void removeMealFromCalendar(Meal meal);

   Single<List<Meal>> getCalendarMeals();
}
