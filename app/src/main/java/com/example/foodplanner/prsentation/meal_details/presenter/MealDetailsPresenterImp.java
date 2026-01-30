package com.example.foodplanner.prsentation.meal_details.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.data.meal.model.MealResponse;
import com.example.foodplanner.data.meal.repository.MealRepository;
import com.example.foodplanner.prsentation.meal_details.view.MealDetailsView;

import java.util.Collections;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenterImp implements MealDetailsPresenter {

    private MealRepository mealRepository;
    private MealDetailsView mealDetailsView;

    public MealDetailsPresenterImp(MealDetailsView view, Context context) {
        this.mealDetailsView = view;
        this.mealRepository = new MealRepository(context);
    }

    @Override
    public void getMealDetailsById(String mealId) {
        mealDetailsView.onMealDetailsFetchLoading();
        mealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .map(meal -> Collections.singletonList(meal))
                .onErrorResumeNext(throwable ->
                        mealRepository.getDetailsOfMeal(mealId)
                                .map(MealResponse::getMeals)
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> mealDetailsView.onMealDetailsFetchSuccess(meals),
                        throwable -> {
                            String message;

                            if (throwable instanceof java.net.UnknownHostException ||
                                    throwable instanceof java.net.SocketTimeoutException) {

                                message = "No internet connection. Please check your network and try again.";

                            } else {
                                message = "Failed to load meal details. Please try again.";
                            }

                            mealDetailsView.onMealDetailsFetchError(message);
                        }
                );

    }


    @Override
    public void insertMealToFav(Meal meal) {

        mealRepository.mealExists(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(exists -> {

                    meal.setFav(true);

                    if (exists > 0) {
                        return mealRepository.updateFavMeal(meal.getIdMeal(), true);
                    } else {
                        return mealRepository.insertFavMeal(meal);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mealDetailsView.showMessage("Meal added to favorites ❤️"),
                        throwable -> mealDetailsView.showMessage("Failed to add to favorites 😢")
                );
    }


    @Override
    public void addMealToCalendar(Meal meal, String date) {
        meal.setCalendar(true);
        meal.setCalendarDate(date);

        mealRepository.mealExists(meal.getIdMeal())
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(exists -> {
                    if (exists > 0) {
                        return mealRepository.updateMealCalendar(
                                meal.getIdMeal(),
                                true,
                                date
                        );
                    } else {
                        meal.setFav(false);
                        return mealRepository.addMealToCalendar(meal);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> mealDetailsView.showMessage("Meal added to calendar 📅"),
                        throwable -> mealDetailsView.showMessage("Failed to add to calendar ❌")
                );
    }
}