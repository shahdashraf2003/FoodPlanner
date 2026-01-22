package com.example.foodplanner.network;

import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.data.meal.model.MealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoryResponse> getAllCategories();

}
