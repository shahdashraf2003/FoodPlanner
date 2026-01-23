package com.example.foodplanner.prsentation.meal_details.presenter;

import android.content.Context;

import com.example.foodplanner.data.datasource.meal.MealNetworkResponse;
import com.example.foodplanner.data.datasource.meal.MealRemoteDataSource;
import com.example.foodplanner.data.models.meal.Meal;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsView;

import java.util.List;

public class MealDetailsPresenterImp implements MealDetailsPresenter{

    private MealRemoteDataSource mealRemoteDataSource;
    private MealDetailsView mealDetailsView;
    public MealDetailsPresenterImp(Context context, MealDetailsView mealDetailsView) {
        mealRemoteDataSource = new MealRemoteDataSource();
        this.mealDetailsView =mealDetailsView;
    }
    @Override
    public void getMealDetailsById(String mealId) {
        mealRemoteDataSource.getRandomMeal(
                new MealNetworkResponse() {
                    @Override
                    public void onSuccess(List<Meal> meal) {
                        mealDetailsView.onMealDetailsFetchSuccess(meal);

                    }

                    @Override
                    public void onError(String errorMsg) {

                        mealDetailsView.onMealDetailsFetchError(errorMsg);

                    }

                    @Override
                    public void onLoading() {
                        mealDetailsView.onMealDetailsFetchLoading();


                }}

        );
    }
}
