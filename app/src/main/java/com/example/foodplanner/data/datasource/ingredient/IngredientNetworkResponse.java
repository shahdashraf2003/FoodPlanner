package com.example.foodplanner.data.datasource.ingredient;

import com.example.foodplanner.data.models.ingredient.Ingredient;

import java.util.List;

public interface IngredientNetworkResponse {
    void onSuccess (List<Ingredient> ingredients);
    void onError (String errorMsg);
    void onLoading();
}
