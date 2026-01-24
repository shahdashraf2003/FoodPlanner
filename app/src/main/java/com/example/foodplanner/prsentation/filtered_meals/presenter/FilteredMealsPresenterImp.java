package com.example.foodplanner.prsentation.filtered_meals.presenter;

import android.content.Context;

import com.example.foodplanner.data.mealsfilterby.datasource.MealFilterByDataSource;
import com.example.foodplanner.data.mealsfilterby.datasource.MealFilterByNetworkResponse;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterBy;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsView;

import java.util.List;

public class FilteredMealsPresenterImp implements FilteredMealsPresenter{
    private MealFilterByDataSource mealFilterByDataSource;
    private FilteredMealsView filteredMealsView;
    public FilteredMealsPresenterImp(Context context, FilteredMealsView filteredMealsView) {
        mealFilterByDataSource = new MealFilterByDataSource();
        this.filteredMealsView =filteredMealsView;
    }
    @Override
    public void filterByIngredient(String ingredient) {
        mealFilterByDataSource.filterByIngredient(
                ingredient,
                new MealFilterByNetworkResponse(){

                    @Override
                    public void onSuccess(List<MealFilterBy> mealsFilterBy) {
                        filteredMealsView.onFilteredMealsByIngredientSuccess(mealsFilterBy);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        filteredMealsView.onFilteredMealsByIngredientError(errorMsg);

                    }

                    @Override
                    public void onLoading() {
                        filteredMealsView.onFilteredMealsByIngredientLoading();

                    }
                }
        );

    }

    @Override
    public void filterByArea(String area) {
        mealFilterByDataSource.filterByArea(
                area,
                new MealFilterByNetworkResponse(){

                    @Override
                    public void onSuccess(List<MealFilterBy> mealsFilterBy) {
                        filteredMealsView.onFilteredMealsByCountrySuccess(mealsFilterBy);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        filteredMealsView.onFilteredMealsByCountryError(errorMsg);

                    }

                    @Override
                    public void onLoading() {
                        filteredMealsView.onFilteredMealsByCountryLoading();

                    }
                }
        );
    }

    @Override
    public void filterByCategory(String category) {
        mealFilterByDataSource.filterByCategory(
                category,
                new MealFilterByNetworkResponse(){

                    @Override
                    public void onSuccess(List<MealFilterBy> mealsFilterBy) {
                        filteredMealsView.onFilteredMealsByCategorySuccess(mealsFilterBy);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        filteredMealsView.onFilteredMealsByCategoryError(errorMsg);

                    }

                    @Override
                    public void onLoading() {
                        filteredMealsView.onFilteredMealsByCategoryLoading();

                    }
                }
        );
    }



}
