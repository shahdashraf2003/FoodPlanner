package com.example.foodplanner.data.mealsfilterby.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealFilterByResponse {


    @SerializedName("meals")
    private List<MealFilterBy> mealsFilterBy;

    public List<MealFilterBy> getMealsFilterBy() { return mealsFilterBy; }
}
