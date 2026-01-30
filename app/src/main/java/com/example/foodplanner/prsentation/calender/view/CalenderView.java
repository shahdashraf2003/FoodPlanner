package com.example.foodplanner.prsentation.calender.view;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public interface CalenderView {
    void onMealRemoved(Meal meal);
    void onNoMealsFounded();
    void showMeals(List<Meal> meals);
    void showError(String msg);


}
