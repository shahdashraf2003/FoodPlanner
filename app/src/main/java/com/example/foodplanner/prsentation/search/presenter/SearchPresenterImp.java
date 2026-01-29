package com.example.foodplanner.prsentation.search.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.data.area.datasource.AreaRemoteDataSource;
import com.example.foodplanner.data.category.datasource.CategoryRemoteDataSource;
import com.example.foodplanner.data.ingredient.datasource.IngredientRemoteDataSource;
import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.prsentation.search.view.SearchView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private final AreaRemoteDataSource areaRemoteDataSource;
    private final CategoryRemoteDataSource allCategoriesRemoteDataSource;
    private final IngredientRemoteDataSource ingredientRemoteDataSource;
    private final MealRepo mealRepo;
    private final SearchView searchView;

    public SearchPresenterImp(Context context, SearchView searchView) {
        areaRemoteDataSource = new AreaRemoteDataSource();
        allCategoriesRemoteDataSource = new CategoryRemoteDataSource();
        ingredientRemoteDataSource = new IngredientRemoteDataSource();
        mealRepo= new MealRepo(context);
        this.searchView = searchView;
    }

    @Override
    public void getAllIngredientsList() {
        searchView.onIngredientsFetchLoading();
        ingredientRemoteDataSource.getAllIngredients()
                .map(categoryResponse -> categoryResponse.getAllIngredientsList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            searchView.onIngredientsFetchSuccess(meals);
                        }
                        ,throwable ->{
                            searchView.onIngredientsListFetchError(throwable.getMessage());
                        }
                );

    }

    @Override
    public void getAllCategoriesList() {
        searchView.onCategoriesFilterFetchLoading();
        allCategoriesRemoteDataSource.getAllCategories()
                .map(categoryResponse -> categoryResponse.getCategories())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            searchView.onCategoriesFilterFetchSuccess(meals);
                        }
                        ,throwable ->{
                            searchView.onCategoriesFilterListFetchError(throwable.getMessage());
                        }
                );
    }

    @Override

        public void getAllAreasList() {
        searchView.onAreaListFetchLoading();
            Log.d("SearchPresenter", "Fetching areas...");

            areaRemoteDataSource.getAllAreasList()
                    .subscribeOn(Schedulers.io())
                    .map(areaResponse -> areaResponse.getAllAreas())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            areas -> {
                                searchView.onAreaListFetchSuccess(areas);
                            },
                            throwable -> {
                                searchView.onAreaListFetchError(throwable.getMessage());
                            }
                    );
        }

    @Override
    public void getSearchedMeal(String mealName) {
        searchView.onSearchedMaelFetchLoading();
        mealRepo.getSearchedMeal(mealName)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            searchView.onSearchedMaelFetchSuccess(meals);
                        },
                        throwable ->{
                            searchView.onSearchedMaelFetchError(throwable.getMessage());
                        }
                );
    }



}
