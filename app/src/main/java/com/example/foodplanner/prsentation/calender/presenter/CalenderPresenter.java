package com.example.foodplanner.prsentation.calender.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface CalenderPresenter {
     void removeMealFromCalendar(Meal meal);

   LiveData <List<Meal>> getCalendarMeals();
}
