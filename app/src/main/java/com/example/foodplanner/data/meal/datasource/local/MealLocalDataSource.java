package com.example.foodplanner.data.meal.datasource.local;

import android.content.Context;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.database.AppDB;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {

    private LocalMealsDao localMealsDao;

    public MealLocalDataSource(Context context) {
        AppDB db = AppDB.getInstance(context);
        localMealsDao = db.localMealsDao();
    }

    public Completable insertFavMeal(Meal meal) {
        return localMealsDao.insertMeal(meal);
    }

    public Completable updateFavMeal(String mealId, boolean isFav) {
        return localMealsDao.updateFav(mealId, isFav);
    }

    public Completable deleteFavMeal(Meal meal) {
        return localMealsDao.updateFav(meal.getIdMeal(), false);
    }

    public Single<List<Meal>> getFavMeals() {
        return localMealsDao.getFavMeals();
    }

    public Completable addMealToCalendar(Meal meal) {
        return localMealsDao.insertMeal(meal);
    }

    public Completable updateMealCalendar(String mealId, boolean isCalendar, String date) {
        return localMealsDao.updateCalendar(mealId, isCalendar, date);
    }

    public Completable removeMealFromCalendar(Meal meal) {
        return localMealsDao.updateCalendar(meal.getIdMeal(), false, null);
    }

    public Single<List<Meal>> getCalendarMeals() {
        return localMealsDao.getCalendarMeals();
    }

    public Single<List<Meal>> getAllMeals() {
        return localMealsDao.getMeals();
    }

    public Single<Meal> getMealById(String mealId) {
        return localMealsDao.getMealById(mealId);
    }

    public Single<Integer> mealExists(String mealId) {
        return localMealsDao.exists(mealId);
    }
}
