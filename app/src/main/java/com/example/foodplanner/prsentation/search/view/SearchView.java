package com.example.foodplanner.prsentation.search.view;

import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.meal.model.remote.Meal;

import java.util.List;

public interface SearchView {
    void onCategoriesFilterListFetchError(String errMsg);
    void onCategoriesFilterFetchLoading();
    void onCategoriesFilterFetchSuccess(List<Category> allCategories);

    void onIngredientsListFetchError(String errMsg);
    void onIngredientsFetchLoading();
    void onIngredientsFetchSuccess(List<Ingredient> ingredients);

    void onAreaListFetchError(String errMsg);
    void onAreaListFetchLoading();
    void onAreaFetchSuccess(List<Area> areas);

    void onSearchedMaelFetchError(String errMsg);
    void onSearchedMaelFetchLoading();
    void onSearchedMaelFetchSuccess(List<Meal> meals);

}
