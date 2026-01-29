package com.example.foodplanner.data.meal.datasource.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface LocalMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Update
    Completable updateMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("SELECT * FROM meals")
    Single<List<Meal>> getMeals();

    @Query("SELECT * FROM meals WHERE is_fav = 1")
    Single<List<Meal>> getFavMeals();

    @Query("SELECT * FROM meals WHERE is_calendar = 1")
    Single<List<Meal>> getCalendarMeals();

    @Query("SELECT COUNT(*) FROM meals WHERE idMeal = :mealId")
    Single<Integer> exists(String mealId);


    @Query("UPDATE meals SET is_fav = :isFav WHERE idMeal = :mealId")
    Completable updateFav(String mealId, boolean isFav);

    @Query("UPDATE meals SET is_calendar = :isCalendar, calendar_date = :date WHERE idMeal = :mealId")
    Completable updateCalendar(String mealId, boolean isCalendar, String date);

    @Query("DELETE FROM meals")
    Completable clearMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<Meal> meals);


}
