package com.example.foodplanner.data.ingredient.datasource;

import com.example.foodplanner.data.ingredient.model.IngredientResponse;
import com.example.foodplanner.network.Network;
import com.example.foodplanner.network.Services;

import io.reactivex.rxjava3.core.Single;

public class IngredientRemoteDataSource {
    private final Services ingredientService;

    public IngredientRemoteDataSource() {
        ingredientService = Network.getInstance().services;
    }

    public Single<IngredientResponse> getAllIngredients() {
        return ingredientService.getAllIngredientsList();

    }
}