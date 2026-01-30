package com.example.foodplanner.prsentation.filtered_meals.presenter;

public interface FilteredMealsPresenter {
    void filterByIngredient(String ingredient);
    void filterByArea(String area);
    void filterByCategory(String category);
}
