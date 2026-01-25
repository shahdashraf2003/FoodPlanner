package com.example.foodplanner.prsentation.favorite.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.favorite.view.FavView;

import java.util.List;

public class FavPresenterImpl  implements FavPresenter {

        private MealRepo mealRepo;
        private FavView favView;
        public FavPresenterImpl(Context context, FavView favView) {
            this.mealRepo = new MealRepo(context);
            this.favView=favView;
        }


        @Override
        public LiveData<List<Meal>> getFavMeals() {
            return  mealRepo.getFavMeals();
        }

        public void deleteFavMeal(Meal meal) {

            mealRepo.deleteFavMeal(meal);
            favView.onMealDeleted();

        }
    }

