package com.example.foodplanner.prsentation.search.view;

import com.example.foodplanner.data.models.area.Area;
import com.example.foodplanner.data.models.all_categories.AllCategories;
import com.example.foodplanner.data.models.ingredient.Ingredient;

import java.util.List;

public interface SearchView {
    void onCategoriesFilterListFetchError(String errMsg);
    void onCategoriesFilterFetchLoading();
    void onCategoriesFilterFetchSuccess(List<AllCategories> allCategories);

    void onIngredientsListFetchError(String errMsg);
    void onIngredientsFetchLoading();
    void onIngredientsFetchSuccess(List<Ingredient> ingredients);

    void onAreaListFetchError(String errMsg);
    void onAreaListFetchLoading();
    void onAreaFetchSuccess(List<Area> areas);

}
