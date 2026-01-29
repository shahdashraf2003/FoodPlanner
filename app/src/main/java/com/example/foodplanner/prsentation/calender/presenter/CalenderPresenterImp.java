package com.example.foodplanner.prsentation.calender.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.prsentation.calender.view.CalenderView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenterImp implements CalenderPresenter{
    private MealRepo mealRepo;
    private CalenderView calenderView;
    public CalenderPresenterImp(Context context, CalenderView    calenderView) {
        this.mealRepo = new MealRepo(context);
        this.calenderView=calenderView;
    }

    @Override
    public void removeMealFromCalendar(Meal meal) {
        mealRepo.removeCalenderedMeal(meal)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->{
                            calenderView.onMealRemoved(meal);
                        }
                );


    }

    @Override
    public Single<List<Meal>> getCalendarMeals() {
        return  mealRepo.getCalendarMeals();
    }



}
