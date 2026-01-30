package com.example.foodplanner.prsentation.search.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.data.area.repository.AreaRepository;
import com.example.foodplanner.data.category.repository.CategoryRepository;
import com.example.foodplanner.data.ingredient.repository.IngredientRepository;
import com.example.foodplanner.data.meal.repository.MealRepository;
import com.example.foodplanner.prsentation.search.view.SearchView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {

    private final AreaRepository areaRepo;
    private final CategoryRepository allCategoryRepo;
    private final IngredientRepository ingredientRepo;
    private final MealRepository mealRepo;
    private final SearchView searchView;

    public SearchPresenterImp(Context context, SearchView searchView) {
        areaRepo = new AreaRepository(context);
        allCategoryRepo = new CategoryRepository(context);
        ingredientRepo = new IngredientRepository(context);
        mealRepo= new MealRepository(context);
        this.searchView = searchView;
    }

    @Override
    public void getAllIngredientsList() {
        searchView.onIngredientsFetchLoading();
        ingredientRepo.getAllIngredients()
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
        allCategoryRepo.getAllCategories()
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

            areaRepo.getAllAreasList()
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
        if (mealName == null || mealName.trim().isEmpty()) {
            searchView.onSearchedMaelFetchError("Please enter a keyword to search");
            return;
        }
        mealRepo.getSearchedMeal(mealName)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> searchView.onSearchedMaelFetchSuccess(meals),
                        throwable -> searchView.onSearchedMaelFetchError(" No meals found matching your search")
                );
    }




}
