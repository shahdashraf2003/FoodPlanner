package com.example.foodplanner.data.meal.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

@Dao
public interface LocalMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);


    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getMeals();

    @Query("SELECT * FROM meals WHERE is_fav = 1")
    LiveData<List<Meal>> getFavMeals();

    @Query("SELECT * FROM meals WHERE is_calendar = 1")
    LiveData<List<Meal>> getCalendarMeals();

    @Query("SELECT COUNT(*) FROM meals WHERE idMeal = :mealId")
    int exists(String mealId);


    @Query("UPDATE meals SET is_fav = :isFav WHERE idMeal = :mealId")
    void updateFav(String mealId, boolean isFav);

    @Query("UPDATE meals SET is_calendar = :isCalendar, calendar_date = :date WHERE idMeal = :mealId")
    void updateCalendar(String mealId, boolean isCalendar, String date);
}
