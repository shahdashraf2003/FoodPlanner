package com.example.foodplanner.network;

import com.example.foodplanner.data.models.area.AreaResponse;
import com.example.foodplanner.data.models.category.CategoryResponse;
import com.example.foodplanner.data.models.all_categories.AllCategoriesResponse;
import com.example.foodplanner.data.models.ingredient.IngredientResponse;
import com.example.foodplanner.data.models.meal.MealResponse;

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
    Call<IngredientResponse> getAllIngredientsList();

    @GET("list.php?c=list")
    Call<AllCategoriesResponse> getAllCategoriesList();

    @GET("list.php?a=list")
    Call<AreaResponse> getAllAreasList();



}
