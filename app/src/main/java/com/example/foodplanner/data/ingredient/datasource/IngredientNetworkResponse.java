package com.example.foodplanner.data.ingredient.datasource;

import com.example.foodplanner.data.ingredient.model.Ingredient;

import java.util.List;

public interface IngredientNetworkResponse {
    void onSuccess (List<Ingredient> ingredients);
    void onError (String errorMsg);
    void onLoading();
}
