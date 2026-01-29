package com.example.foodplanner.prsentation.favorite.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.view.FavView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl  implements FavPresenter {

        private MealRepo mealRepo;
        private FavView favView;
        public FavPresenterImpl(Context context, FavView favView) {
            this.mealRepo = new MealRepo(context);
            this.favView=favView;
        }


        @Override
        public Single<List<Meal>> getFavMeals() {
            return  mealRepo.getFavMeals();

        }

    public Completable deleteFavMeal(Meal meal) {

       return mealRepo.deleteFavMeal(meal)
                .subscribeOn(Schedulers.io());

    }
}

