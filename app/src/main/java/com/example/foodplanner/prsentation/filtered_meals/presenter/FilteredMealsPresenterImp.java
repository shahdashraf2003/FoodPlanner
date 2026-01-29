package com.example.foodplanner.prsentation.filtered_meals.presenter;

import android.content.Context;

import com.example.foodplanner.data.mealsfilterby.datasource.MealFilterByDataSource;
import com.example.foodplanner.prsentation.filtered_meals.view.FilteredMealsView;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilteredMealsPresenterImp implements FilteredMealsPresenter{
    private MealFilterByDataSource mealFilterByDataSource;
    private FilteredMealsView filteredMealsView;

    public FilteredMealsPresenterImp(Context context, FilteredMealsView filteredMealsView) {
        mealFilterByDataSource = new MealFilterByDataSource();
        this.filteredMealsView =filteredMealsView;
    }
    @Override
    public void filterByIngredient(String ingredient) {
        filteredMealsView.onFilteredMealsByIngredientLoading();
        mealFilterByDataSource.filterByIngredient(ingredient)
                .subscribeOn(Schedulers.io())
                .map(mealFilterByResponse -> mealFilterByResponse.getMealsFilterBy())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            filteredMealsView.onFilteredMealsByIngredientSuccess(meals);
                        },
                        throwable ->{
                            filteredMealsView.onFilteredMealsByIngredientError(throwable.getMessage());
                        }
                );

    }

    @Override
    public void filterByArea(String area) {
        filteredMealsView.onFilteredMealsByCountryLoading();
        mealFilterByDataSource.filterByArea(area)
                .subscribeOn(Schedulers.io())
                .map(mealFilterByResponse -> mealFilterByResponse.getMealsFilterBy())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            filteredMealsView.onFilteredMealsByCountrySuccess(meals);
                        },
                        throwable ->{
                            filteredMealsView.onFilteredMealsByCountryError(throwable.getMessage());
                        }
                );
    }

    @Override
    public void filterByCategory(String category) {
        filteredMealsView.onFilteredMealsByCategoryLoading();
        mealFilterByDataSource.filterByCategory(category)
                .subscribeOn(Schedulers.io())
                .map(mealFilterByResponse -> mealFilterByResponse.getMealsFilterBy())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            filteredMealsView.onFilteredMealsByCategorySuccess(meals);
                        },
                        throwable ->{
                            filteredMealsView.onFilteredMealsByCategoryError(throwable.getMessage());
                        }
                );
    }



}
