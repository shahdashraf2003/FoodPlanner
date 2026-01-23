package com.example.foodplanner.network;

import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.data.ingredient.Ingredient;
import com.example.foodplanner.data.ingredient.IngredientResponse;
import com.example.foodplanner.data.meal.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoryResponse> getAllCategories();

    @GET("lookup.php")
    Call<MealResponse> getMealDetailsById(@Query("i") String mealId);

    @GET("list.php?i=list")
    Call<IngredientResponse> getAllIngredients();


}
