package com.example.foodplanner.prsentation.home.presenter;

import android.content.Context;

import com.example.foodplanner.data.category.repository.CategoryRepository;
import com.example.foodplanner.data.meal.repository.MealRepository;
import com.example.foodplanner.prsentation.home.view.HomeView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter {
    private MealRepository mealRepo;
    private CategoryRepository categoryRepo;
    private HomeView homeView;
    public HomePresenterImp(Context context, HomeView homeView) {
        mealRepo = new MealRepository(context);
        categoryRepo=new CategoryRepository(context);
        this.homeView =homeView;
    }
    public void getRandomMeal() {
        homeView.onRandomMealFetchLoading();
        mealRepo.getRandomMeal()
                .subscribeOn(Schedulers.io())
               .map(mealResponse -> {//list of meals
                  return mealResponse.getMeals().get(0); //take first meal
               })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                       meal->{
                           homeView.onRandomMealFetchSuccess(meal);
                       },
                       throwable ->{
                           homeView.onRandomMealFetchError(throwable.getMessage());
                       }
               );

    }

    @Override
    public void getAllCategories() {
        homeView.onAllCategoriesFetchLoading();
        categoryRepo.getAllCategories()
                .subscribeOn(Schedulers.io())
                .map(categoryResponse -> categoryResponse.getCategories())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals->{
                            homeView.onAllCategoriesFetchSuccess(meals);
                        }
                        ,throwable ->{
                            homeView.onAllCategoriesFetchError(throwable.getMessage());
                        }
                );

              /*  new CategoryNetworkResponse() {
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

        );*/
    }


}
