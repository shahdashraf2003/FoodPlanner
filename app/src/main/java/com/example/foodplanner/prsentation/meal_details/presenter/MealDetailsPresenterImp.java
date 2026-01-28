package com.example.foodplanner.prsentation.meal_details.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsView;

import java.util.List;

public class MealDetailsPresenterImp implements MealDetailsPresenter{

    private MealRepo mealRepo;
    private MealDetailsView mealDetailsView;
    public MealDetailsPresenterImp(Context context, MealDetailsView mealDetailsView) {
        mealRepo = new MealRepo(context);
        this.mealDetailsView =mealDetailsView;
    }
    @Override
    public void getMealDetailsById(String mealId) {
        mealRepo.getDetailsOfMeal(
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

    @Override
    public void insertMealToFav(Meal meal) {
        mealRepo.insertFavMeal(meal);
        mealDetailsView.showMessage("Meal added to favorites");


    }

    @Override
    public MealRepo getMealRepo() {
        return mealRepo;
    }

    @Override
    public void addMealToCalendar(Meal meal, String date) {
        meal.setCalendar(true);
        meal.setCalendarDate(date);
        mealRepo.addMealToCalendar(meal, date);
    }


}
