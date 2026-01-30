package com.example.foodplanner.data.meal.repository;

import android.content.Context;

import com.example.foodplanner.data.meal.datasource.local.MealLocalDataSource;
import com.example.foodplanner.data.meal.datasource.remote.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.data.meal.model.MealResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealRepository {

    private MealLocalDataSource mealLocalDataSource;
    private MealRemoteDataSource mealRemoteDataSource;

    public MealRepository(Context context) {
        this.mealLocalDataSource = new MealLocalDataSource(context);
        this.mealRemoteDataSource = new MealRemoteDataSource();
    }

    // Remote
    public Single<MealResponse> getRandomMeal() {
        return mealRemoteDataSource.getRandomMeal();
    }

    public Single<MealResponse> getDetailsOfMeal(String mealId) {
        return mealRemoteDataSource.getDetailsOfMeal(mealId);
    }

    public Single<MealResponse> getSearchedMeal(String mealName) {
        return mealRemoteDataSource.getSearchedMeal(mealName);
    }

    // Local
    public Single<List<Meal>> getFavMeals() {
        return mealLocalDataSource.getFavMeals();
    }

    public Completable insertFavMeal(Meal meal) {
        return mealLocalDataSource.insertFavMeal(meal);
    }

    public Completable updateFavMeal(String mealId, boolean isFav) {
        return mealLocalDataSource.updateFavMeal(mealId, isFav);
    }

    public Completable deleteFavMeal(Meal meal) {
        return mealLocalDataSource.deleteFavMeal(meal);
    }

    public Completable addMealToCalendar(Meal meal) {
        return mealLocalDataSource.addMealToCalendar(meal);
    }

    public Completable updateMealCalendar(String mealId, boolean isCalendar, String date) {
        return mealLocalDataSource.updateMealCalendar(mealId, isCalendar, date);
    }

    public Completable removeCalenderedMeal(Meal meal) {
        return mealLocalDataSource.removeMealFromCalendar(meal);
    }

    public Single<List<Meal>> getCalendarMeals() {
        return mealLocalDataSource.getCalendarMeals();
    }

    public Single<List<Meal>> getAllMeals() {
        return mealLocalDataSource.getAllMeals();
    }

    public Single<Meal> getMealById(String mealId) {
        return mealLocalDataSource.getMealById(mealId);
    }

    public Single<Integer> mealExists(String mealId) {
        return mealLocalDataSource.mealExists(mealId);
    }
}
