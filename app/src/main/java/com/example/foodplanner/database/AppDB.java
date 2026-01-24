package com.example.foodplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodplanner.data.meal.datasource.local.MealsDao;
import com.example.foodplanner.data.meal.model.Meal;


@Database(entities = {Meal.class} , version = 1)
public abstract class AppDB extends RoomDatabase {

    public abstract MealsDao mealsDao();
    private static  AppDB instance =null;
    public static AppDB getInstance(Context context) {
        if (instance==null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDB.class,
                    "mealdb")
                    .build();
        }
        return instance;
    }

}
