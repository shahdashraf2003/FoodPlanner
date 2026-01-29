package com.example.foodplanner.prsentation.meal_details.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImp implements MealDetailsPresenter{

    private MealRepo mealRepo;
    private MealDetailsView mealDetailsView;
    public MealDetailsPresenterImp(Context context, MealDetailsView mealDetailsView) {
        mealRepo = new MealRepo(context);
        this.mealDetailsView =mealDetailsView;
    }
    @Override
    public void getMealDetailsById(String mealId) {
        mealDetailsView.onMealDetailsFetchLoading();
        mealRepo.getDetailsOfMeal(mealId)
                .subscribeOn(Schedulers.io())
                .map(mealResponse -> mealResponse.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals->{
                                    mealDetailsView.onMealDetailsFetchSuccess(meals);
                                },
                                throwable ->{
                                    mealDetailsView.onMealDetailsFetchError(throwable.getMessage());
                                }
                        );

    }

    @Override
    public void insertMealToFav(Meal meal) {
        mealRepo.insertFavMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()-> {
                            mealDetailsView.showMessage("Meal added to favorites");
                        });


    }

    @Override
    public MealRepo getMealRepo() {
        return mealRepo;
    }
    @Override
    public void addMealToCalendar(Meal meal, String date) {

        meal.setCalendar(true);
        meal.setCalendarDate(date);
        mealRepo.addMealToCalendar(meal, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                           mealDetailsView.showMessage("Meal added to calendar");
                        }
                );
    }

}
