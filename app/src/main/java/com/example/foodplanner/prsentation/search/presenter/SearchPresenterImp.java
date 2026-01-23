package com.example.foodplanner.prsentation.search.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.data.datasource.area.AreaNetworkResponse;
import com.example.foodplanner.data.datasource.area.AreaRemoteDataSource;
import com.example.foodplanner.data.datasource.category_filter.CategoryFilterNetworkResponse;
import com.example.foodplanner.data.datasource.category_filter.CategoryFilterRemoteDataSource;
import com.example.foodplanner.data.datasource.ingredient.IngredientNetworkResponse;
import com.example.foodplanner.data.datasource.ingredient.IngredientRemoteDataSource;
import com.example.foodplanner.data.models.area.Area;
import com.example.foodplanner.data.models.category_filter.CategoryFilter;
import com.example.foodplanner.data.models.ingredient.Ingredient;
import com.example.foodplanner.prsentation.search.view.SearchView;

import java.util.List;

public class SearchPresenterImp implements SearchPresenter {

    private final AreaRemoteDataSource areaRemoteDataSource;
    private final CategoryFilterRemoteDataSource categoryFilterRemoteDataSource;
    private final IngredientRemoteDataSource ingredientRemoteDataSource;
    private final SearchView searchView;

    public SearchPresenterImp(Context context, SearchView searchView) {
        areaRemoteDataSource = new AreaRemoteDataSource();
        categoryFilterRemoteDataSource = new CategoryFilterRemoteDataSource();
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
        this.searchView = searchView;
    }

    @Override
    public void getAllIngredientsList() {
        ingredientRemoteDataSource.getAllIngredients(new IngredientNetworkResponse() {
            @Override
            public void onSuccess(List<Ingredient> ingredients) {
                searchView.onIngredientsFetchSuccess(ingredients);
            }

            @Override
            public void onError(String errorMsg) {
                searchView.onIngredientsListFetchError(errorMsg);
            }

            @Override
            public void onLoading() {
                searchView.onIngredientsFetchLoading();
            }
        });
    }

    @Override
    public void getAllCategoriesList() {
        categoryFilterRemoteDataSource.getAllCategoriesFilter(new CategoryFilterNetworkResponse() {
            @Override
            public void onSuccess(List<CategoryFilter> categoryFilters) {
                searchView.onCategoriesFilterFetchSuccess(categoryFilters);
            }

            @Override
            public void onError(String errorMsg) {
                searchView.onCategoriesFilterListFetchError(errorMsg);
            }

            @Override
            public void onLoading() {
                searchView.onCategoriesFilterFetchLoading();
            }
        });
    }

    @Override

        public void getAllAreasList() {
            Log.d("SearchPresenter", "Fetching areas...");
            areaRemoteDataSource.getAllAreasList(new AreaNetworkResponse() {
                @Override
                public void onSuccess(List<Area> areas) {
                    Log.d("SearchPresenter", "Areas fetched: " + areas.size());
                    searchView.onAreaFetchSuccess(areas);
                }

                @Override
                public void onError(String errorMsg) {
                    Log.e("SearchPresenter", "Error fetching areas: " + errorMsg);
                    searchView.onAreaListFetchError(errorMsg);
                }

                @Override
                public void onLoading() {
                    Log.d("SearchPresenter", "Areas loading...");
                    searchView.onAreaListFetchLoading();
                }
            });
        }

    }
