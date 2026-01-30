package com.example.foodplanner.prsentation.favorite.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.repository.MealRepository;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.view.FavView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl implements FavPresenter {

    private MealRepository mealRepo;
    private FavView favView;

    public FavPresenterImpl(Context context, FavView favView) {
        this.mealRepo = new MealRepository(context);
        this.favView = favView;
    }

    @Override
    public void getFavMeals() {
        mealRepo.getFavMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (meals == null || meals.isEmpty()) {
                                favView.onNoMealsFounded();
                            } else {
                                favView.showMeals(meals);
                            }
                        },
                        throwable -> favView.showMessage("Failed to load favorites")
                );
    }

    @Override
    public void deleteFavMeal(Meal meal) {
        mealRepo.deleteFavMeal(meal)
                .subscribeOn(Schedulers.io())
                .andThen(mealRepo.getFavMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            favView.showMessage("Meal removed");
                            favView.showMeals(meals);
                        },
                        throwable -> favView.showMessage("Failed to delete meal")
                );
    }


}
