package com.example.foodplanner.data.meal.datasource.local;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.database.AppDB;

import java.util.List;

public class MealLocalDataSource {
    private LocalMealsDao localMealsDao;

    public MealLocalDataSource(Context context) {
        AppDB db = AppDB.getInstance(context);
        localMealsDao = db.localMealsDao();
    }

    public void insertFavMeal(LocalMeal meal) {
        new Thread(() -> {

            if (localMealsDao.exists(meal.getIdMeal()) > 0) {
                localMealsDao.updateFav(meal.getIdMeal(), true);
            } else {
                meal.setFav(true);
                meal.setCalendar(false);
                meal.setCalendarDate(null);
                localMealsDao.insertMeal(meal);
            }

        }).start();
    }




    public void deleteFavMeal(LocalMeal meal) {
        new Thread(() -> {
            meal.setFav(false);
            localMealsDao.updateFav(meal.getIdMeal(), false);
            Log.d("favorite", "deleteFavMeal: " + meal.getStrMeal());
        }).start();
    }

    public LiveData<List<LocalMeal>> getFavMeals() {
        return localMealsDao.getFavMeals();
    }

    public void addMealToCalendar(LocalMeal meal, String date) {
        new Thread(() -> {

            if (localMealsDao.exists(meal.getIdMeal()) > 0) {
                localMealsDao.updateCalendar(meal.getIdMeal(), true, date);
            } else {
                meal.setCalendar(true);
                meal.setCalendarDate(date);
                meal.setFav(false);
                localMealsDao.insertMeal(meal);
            }

        }).start();
    }

    public void removeMealFromCalendar(LocalMeal meal) {
        new Thread(() -> {
            meal.setCalendar(false);
            meal.setCalendarDate(null);
            localMealsDao.updateCalendar(meal.getIdMeal(), false, null);
            Log.d("calendar", "removeMealFromCalendar: " + meal.getStrMeal());
        }).start();
    }

    public LiveData<List<LocalMeal>> getCalendarMeals() {
        return localMealsDao.getCalendarMeals();
    }
}
