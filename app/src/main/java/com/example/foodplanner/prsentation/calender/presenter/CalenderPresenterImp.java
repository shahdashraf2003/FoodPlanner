package com.example.foodplanner.prsentation.calender.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodplanner.data.meal.MealRepo;
import com.example.foodplanner.data.meal.model.loacl.LocalMeal;
import com.example.foodplanner.prsentation.calender.view.CalenderView;

import java.util.List;

public class CalenderPresenterImp implements CalenderPresenter{
    private MealRepo mealRepo;
    private CalenderView calenderView;
    public CalenderPresenterImp(Context context, CalenderView    calenderView) {
        this.mealRepo = new MealRepo(context);
        this.calenderView=calenderView;
    }

    @Override
    public void removeMealFromCalendar(LocalMeal meal) {
        mealRepo.removeCalenderedMeal(meal);
        calenderView.onMealRemoved(meal);


    }

    @Override
    public LiveData<List<LocalMeal>> getCalendarMeals() {
        return  mealRepo.getCalendarMeals();
    }



}
