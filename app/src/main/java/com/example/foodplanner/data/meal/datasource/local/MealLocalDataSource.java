package com.example.foodplanner.data.meal.datasource.local;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.database.AppDB;


import java.util.List;

public class MealLocalDataSource {
    private MealsDao mealsDao;

    public MealLocalDataSource(Context context) {
        AppDB db = AppDB.getInstance(context);
        mealsDao = db.mealsDao();
    }
    public void insertMeal(Meal meal)
    {
        new  Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("favorite", "run: "+ meal);

                mealsDao.insertMeal(meal);
            }
        }).start();

    }

    public void deleteMeal(Meal meal) {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                mealsDao.deleteMeal(meal);
            }
        }).start();
    }

    public LiveData< List<Meal> >getMeals()
    {
        return mealsDao.getMeals();
    }
}
