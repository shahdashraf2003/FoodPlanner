package com.example.foodplanner.data.models.ingredient;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {
    @SerializedName("meals")
    private List<Ingredient> ingredients;
    public List<Ingredient> getAllIngredientsList() {
        return ingredients;
    }
}
