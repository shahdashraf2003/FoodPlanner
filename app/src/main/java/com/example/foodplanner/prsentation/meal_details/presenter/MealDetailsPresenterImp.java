package com.example.foodplanner.prsentation.meal_details.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.datasource.MealNetworkResponse;
import com.example.foodplanner.data.meal.datasource.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;
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
        mealRemoteDataSource.getDetailsOfMeal(
                new MealNetworkResponse() {
                    @Override
                    public void onSuccess(List<Meal> meals) {
                        mealDetailsView.onMealDetailsFetchSuccess(meals);
                    }

                    @Override
                    public void onError(String errorMsg) {
                        mealDetailsView.onMealDetailsFetchError(errorMsg);
                    }

                    @Override
                    public void onLoading() {
                            mealDetailsView.onMealDetailsFetchLoading();
                    }
                },mealId
        );



    }
}
