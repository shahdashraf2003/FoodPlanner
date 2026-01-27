package com.example.foodplanner.prsentation.favorite.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.data.meal.model.remote.Meal;
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
        public LiveData<List<LocalMeal>> getFavMeals() {
            return  mealRepo.getFavMeals();
        }

        public void deleteFavMeal(LocalMeal meal) {

            mealRepo.deleteFavMeal(meal);
            favView.onMealDeleted();

        }
    }

