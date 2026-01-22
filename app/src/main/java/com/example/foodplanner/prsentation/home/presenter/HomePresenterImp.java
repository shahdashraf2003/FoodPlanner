package com.example.foodplanner.prsentation.home.presenter;

import android.content.Context;
import com.example.foodplanner.data.datasource.MealNetworkResponse;
import com.example.foodplanner.data.datasource.MealRemoteDataSource;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.home.view.HomeFragment;
import com.example.foodplanner.prsentation.home.view.HomeView;

import java.util.List;

public class HomePresenterImp implements HomeFragment.HomePresenter {
   private MealRemoteDataSource mealRemoteDataSource;
    private HomeView homeView;
    public HomePresenterImp(Context context, HomeView homeView) {
        mealRemoteDataSource = new MealRemoteDataSource();
        this.homeView =homeView;
    }
    public void getRandomMeal() {

        mealRemoteDataSource.getRandomMeal(
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
    public void getAllCategries() {

    }
}
