package com.example.foodplanner.data.meal;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.datasource.local.MealLocalDataSource;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.datasource.remote.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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
     //done
    public Single<List<Meal>> getFavMeals()
    {
        return  mealLocalDataSource.getFavMeals();
    }

    //done
    public Completable insertFavMeal(Meal meal)
    {
        return mealLocalDataSource.insertFavMeal(meal);
    }

    //done
    public Completable deleteFavMeal(Meal meal)
    {
        return mealLocalDataSource.deleteFavMeal(meal);
    }
    //done
    public Completable addMealToCalendar(Meal meal,String date)
    {
       return mealLocalDataSource.addMealToCalendar(meal,date);
    }

    //done
    public Completable removeCalenderedMeal(Meal meal)
    {
        return mealLocalDataSource.removeMealFromCalendar(meal);
    }
    public Single<List<Meal>> getCalendarMeals()
    {
        return  mealLocalDataSource.getCalendarMeals();
    }


}
