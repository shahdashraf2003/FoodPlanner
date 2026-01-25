package com.example.foodplanner.data.meal.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

@Dao
public interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insertMeal(Meal meal);
    @Query("Select * from meals")
  LiveData<List<Meal>>getMeals();
    @Delete
    void deleteMeal(Meal meal);
}
