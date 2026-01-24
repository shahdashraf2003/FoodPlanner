package com.example.foodplanner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.datasource.local.MealLocalDataSource;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.datasource.remote.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

public class MealRepo {
    private MealLocalDataSource mealLocalDataSource;
    private MealRemoteDataSource mealRemoteDataSource;

    public MealRepo(Context context) {
        this.mealLocalDataSource = new MealLocalDataSource(context);
        this.mealRemoteDataSource = new MealRemoteDataSource();
    }
    public void getRandomMeal(MealNetworkResponse mealResponse){
        mealRemoteDataSource.getRandomMeal(mealResponse);
    }

    public void getDetailsOfMeal(MealNetworkResponse mealResponse,String mealId){
        mealRemoteDataSource.getDetailsOfMeal(mealResponse,mealId);
    }

     public void getSearchedMeal(MealNetworkResponse mealResponse,String mealName){
         mealRemoteDataSource.getSearchedMeal(mealResponse,mealName);
     }
    public LiveData<List<Meal>> getFavMeals()
    {
        return  mealLocalDataSource.getMeals();
    }

    public void insertFavMeal(Meal meal)
    {
        mealLocalDataSource.insertMeal(meal);
    }

    public void deleteFavMeal(Meal meal)
    {
        mealLocalDataSource.deleteMeal(meal);
    }
}
