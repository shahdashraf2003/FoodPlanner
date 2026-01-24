package com.example.foodplanner.prsentation.search.view;

import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.all_categories.model.AllCategories;
import com.example.foodplanner.data.ingredient.model.Ingredient;

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
