package com.example.foodplanner.prsentation.calender.presenter;


import com.example.foodplanner.data.meal.model.Meal;

public interface CalenderPresenter {

    void getCalendarMeals(String date);
    void removeMealFromCalendar(Meal meal);
}
