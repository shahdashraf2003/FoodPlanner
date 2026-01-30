package com.example.foodplanner.prsentation.calender.view;

import com.example.foodplanner.data.meal.model.Meal;

public interface OnCalenderedMealClickListener {
    void onCalenderedMealDeleteClick(Meal meal);
    void onCalenderedMealClick(Meal meal);

}
