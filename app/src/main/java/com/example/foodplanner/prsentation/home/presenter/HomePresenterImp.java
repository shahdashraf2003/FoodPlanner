package com.example.foodplanner.prsentation.home.presenter;

import android.content.Context;

import com.example.foodplanner.data.category.model.Category;
import com.example.foodplanner.data.category.datasource.CategoryNetworkResponse;
import com.example.foodplanner.data.category.datasource.CategoryRemoteDataSource;
import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.datasource.remote.MealNetworkResponse;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.home.view.HomeView;

import java.util.List;

public class HomePresenterImp implements HomePresenter {
    private MealRepo mealRepo;
    private CategoryRemoteDataSource categoryRemoteDataSource;
    private HomeView homeView;
    public HomePresenterImp(Context context, HomeView homeView) {
        mealRepo = new MealRepo(context);
        categoryRemoteDataSource=new CategoryRemoteDataSource();
        this.homeView =homeView;
    }
    public void getRandomMeal() {

        mealRepo.getRandomMeal(
                new MealNetworkResponse() {
                    @Override
                    public void onSuccess(List<Meal> meal) {
                        homeView.onRandomMealFetchSuccess(meal);

                    }

                    @Override
                    public void onError(String errorMsg) {

                        homeView.onRandomMealFetchError(errorMsg);

                    }

                    @Override
                    public void onLoading() {
                        homeView.onRandomMealFetchLoading();
                    }


                }

        );
    }

    @Override
    public void getAllCategories() {
        categoryRemoteDataSource.getAllCategories(
                new CategoryNetworkResponse() {
                    @Override
                    public void onSuccess(List<Category> categories) {
                        homeView.onAllCategoriesFetchSuccess(categories);

                    }

                    @Override
                    public void onError(String errorMsg) {

                        homeView.onAllCategoriesFetchError(errorMsg);

                    }

                    @Override
                    public void onLoading() {

                        homeView.onAllCategoriesFetchLoading();
                    }


                }

        );
    }


}
