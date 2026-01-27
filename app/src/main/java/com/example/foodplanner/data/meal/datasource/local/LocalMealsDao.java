package com.example.foodplanner.data.meal.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplanner.data.meal.model.loacl.LocalMeal;

import java.util.List;

@Dao
public interface LocalMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(LocalMeal meal);


    @Update
    void updateMeal(LocalMeal meal);

    @Delete
    void deleteMeal(LocalMeal meal);

    @Query("SELECT * FROM meals")
    LiveData<List<LocalMeal>> getMeals();

    @Query("SELECT * FROM meals WHERE is_fav = 1")
    LiveData<List<LocalMeal>> getFavMeals();

    @Query("SELECT * FROM meals WHERE is_calendar = 1")
    LiveData<List<LocalMeal>> getCalendarMeals();

    @Query("SELECT COUNT(*) FROM meals WHERE id = :mealId")
    int exists(String mealId);


    @Query("UPDATE meals SET is_fav = :isFav WHERE id = :mealId")
    void updateFav(String mealId, boolean isFav);

    @Query("UPDATE meals SET is_calendar = :isCalendar, calendar_date = :date WHERE id = :mealId")
    void updateCalendar(String mealId, boolean isCalendar, String date);
}
