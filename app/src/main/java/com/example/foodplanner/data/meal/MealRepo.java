package com.example.foodplanner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.datasource.local.MealLocalDataSource;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.datasource.remote.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.data.meal.model.remote.Meal;

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
    public LiveData<List<LocalMeal>> getFavMeals()
    {
        return  mealLocalDataSource.getFavMeals();
    }

    public void insertFavMeal(LocalMeal meal)
    {meal.setFav(true);
        mealLocalDataSource.insertFavMeal(meal);
    }

    public void deleteFavMeal(LocalMeal meal)
    {
        mealLocalDataSource.deleteFavMeal(meal);
    }

    public void addMealToCalendar(LocalMeal meal,String date)
    {
        mealLocalDataSource.addMealToCalendar(meal,date);
    }
    public void removeCalenderedMeal(LocalMeal meal)
    {
        mealLocalDataSource.removeMealFromCalendar(meal);

    }
    public LiveData<List<LocalMeal>> getCalendarMeals()
    {
        return  mealLocalDataSource.getCalendarMeals();
    }


}
