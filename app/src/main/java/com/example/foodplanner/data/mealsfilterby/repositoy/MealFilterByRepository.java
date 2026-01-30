package com.example.foodplanner.data.mealsfilterby.repositoy;

import android.content.Context;

import com.example.foodplanner.data.mealsfilterby.datasource.remote.MealFilterByRemoteDataSource;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterByResponse;

import io.reactivex.rxjava3.core.Single;

public class MealFilterByRepository {
    private MealFilterByRemoteDataSource mealFilterByRemoteDataSource;

    public MealFilterByRepository(Context context ) {
        this.mealFilterByRemoteDataSource = new MealFilterByRemoteDataSource();
    }


    public Single<MealFilterByResponse> filterByIngredient(String ingredient) {
        return mealFilterByRemoteDataSource.filterByIngredient(ingredient);

    }
    public Single<MealFilterByResponse> filterByCategory(String category) {
        return mealFilterByRemoteDataSource.filterByCategory(category);

    }

    public Single<MealFilterByResponse> filterByArea(String area) {
        return mealFilterByRemoteDataSource.filterByArea(area);

    }

}
