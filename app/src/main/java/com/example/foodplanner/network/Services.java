package com.example.foodplanner.network;

import com.example.foodplanner.data.area.model.AreaResponse;
import com.example.foodplanner.data.category.model.CategoryResponse;
import com.example.foodplanner.data.ingredient.model.IngredientResponse;
import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.data.mealsfilterby.model.MealFilterByResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {

    @GET("random.php")
    Single<MealResponse> getRandomMeal();

    @GET("categories.php")
    Single<CategoryResponse> getAllCategories();

    @GET("lookup.php")
    Single<MealResponse> getMealDetailsById(@Query("i") String mealId);

    @GET("list.php?i=list")
    Single<IngredientResponse> getAllIngredientsList();

    @GET("list.php?a=list")
    Single<AreaResponse> getAllAreasList();

    @GET("filter.php")
    Single<MealFilterByResponse> filterByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Single<MealFilterByResponse> filterByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<MealFilterByResponse> filterByArea(@Query("a") String area);

    @GET("search.php")
    Single<MealResponse> searchMeal(@Query("s") String mealName);

}
