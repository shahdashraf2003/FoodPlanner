package com.example.foodplanner.network;

import com.example.foodplanner.data.area.model.AreaResponse;
import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.data.all_categories.model.AllCategoriesResponse;
import com.example.foodplanner.data.ingredient.model.IngredientResponse;
import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterByResponse;

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


    @GET("filter.php")
    Call<MealFilterByResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<MealFilterByResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Call<MealFilterByResponse> filterByArea(@Query("a") String area);

}



