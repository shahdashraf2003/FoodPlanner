package com.example.foodplanner.prsentation.search.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.data.area.datasource.AreaNetworkResponse;
import com.example.foodplanner.data.area.datasource.AreaRemoteDataSource;
import com.example.foodplanner.data.all_categories.datasource.AllCategoriesNetworkResponse;
import com.example.foodplanner.data.all_categories.datasource.AllCategoriesRemoteDataSource;
import com.example.foodplanner.data.ingredient.datasource.IngredientNetworkResponse;
import com.example.foodplanner.data.ingredient.datasource.IngredientRemoteDataSource;
import com.example.foodplanner.data.area.model.Area;
import com.example.foodplanner.data.all_categories.model.AllCategories;
import com.example.foodplanner.data.ingredient.model.Ingredient;
import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.datasource.remote.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.search.view.SearchView;

import java.util.List;

public class SearchPresenterImp implements SearchPresenter {

    private final AreaRemoteDataSource areaRemoteDataSource;
    private final AllCategoriesRemoteDataSource allCategoriesRemoteDataSource;
    private final IngredientRemoteDataSource ingredientRemoteDataSource;
    private final MealRepo mealRepo;
    private final SearchView searchView;

    public SearchPresenterImp(Context context, SearchView searchView) {
        areaRemoteDataSource = new AreaRemoteDataSource();
        allCategoriesRemoteDataSource = new AllCategoriesRemoteDataSource();
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
        mealRepo= new MealRepo(context);
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
        allCategoriesRemoteDataSource.getAllCategoriesFilter(new AllCategoriesNetworkResponse() {
            @Override
            public void onSuccess(List<AllCategories> allCategories) {
                searchView.onCategoriesFilterFetchSuccess(allCategories);
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

    @Override
    public void getSearchedMeal(String mealName) {
        mealRepo.getSearchedMeal(new MealNetworkResponse() {
            @Override
            public void onSuccess(List<Meal> meals) {
                    searchView.onSearchedMaelFetchSuccess(meals);
            }

            @Override
            public void onError(String errorMsg) {
                    searchView.onAreaListFetchError(errorMsg);
            }

            @Override
            public void onLoading() {

            }
        },mealName);
    }

}
