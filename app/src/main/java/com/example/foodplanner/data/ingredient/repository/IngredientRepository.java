package com.example.foodplanner.data.ingredient.repository;

import android.content.Context;

import com.example.foodplanner.data.ingredient.datasource.remote.IngredientRemoteDataSource;
import com.example.foodplanner.data.ingredient.model.IngredientResponse;

import io.reactivex.rxjava3.core.Single;

public class IngredientRepository {

    private final IngredientRemoteDataSource ingredientRemoteDataSource;

    public IngredientRepository(Context context) {
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
    }

    public Single<IngredientResponse> getAllIngredients() {
        return ingredientRemoteDataSource.getAllIngredients();
    }
}
