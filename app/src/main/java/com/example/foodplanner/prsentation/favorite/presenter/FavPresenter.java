package com.example.foodplanner.prsentation.favorite.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface FavPresenter {
    Single<List<Meal>> getFavMeals();
    Completable deleteFavMeal(Meal meal);
}
