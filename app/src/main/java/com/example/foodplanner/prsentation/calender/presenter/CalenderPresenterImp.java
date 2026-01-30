package com.example.foodplanner.prsentation.calender.presenter;

import android.content.Context;

import com.example.foodplanner.data.meal.repository.MealRepository;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.calender.view.CalenderView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenterImp implements CalenderPresenter {

    private MealRepository mealRepo;
    private CalenderView view;

    public CalenderPresenterImp(Context context, CalenderView view) {
        this.mealRepo = new MealRepository(context);
        this.view = view;
    }

    @Override
    public void getCalendarMeals(String date) {
        mealRepo.getCalendarMeals()
                .subscribeOn(Schedulers.io())
                .map(meals -> {
                    List<Meal> filtered = new ArrayList<>();
                    for (Meal meal : meals) {
                        if (date.equals(meal.getCalendarDate())) {
                            filtered.add(meal);
                        }
                    }
                    return filtered;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meals -> {
                            if (meals.isEmpty()) view.onNoMealsFounded();
                            else view.showMeals(meals);
                        },
                        throwable -> view.showError(throwable.getMessage())
                );
    }

    @Override
    public void removeMealFromCalendar(Meal meal) {
        mealRepo.removeCalenderedMeal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onMealRemoved(meal),
                        throwable -> view.showError(throwable.getMessage())
                );
    }
}
