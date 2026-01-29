package com.example.foodplanner.data.meal.datasource.local;

import android.content.Context;

import com.example.foodplanner.data.meal.model.Meal;
import com.example.foodplanner.database.AppDB;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {
    private LocalMealsDao localMealsDao;

    public MealLocalDataSource(Context context) {
        AppDB db = AppDB.getInstance(context);
        localMealsDao = db.localMealsDao();
    }

    public Completable insertFavMeal(Meal meal) {
        return localMealsDao.exists(meal.getIdMeal())
                .flatMapCompletable(count -> {
                    if (count > 0) {
                        return localMealsDao.updateFav(meal.getIdMeal(), true);
                    } else {

                        meal.setFav(true);
                        meal.setCalendar(false);
                        meal.setCalendarDate(null);
                        return localMealsDao.insertMeal(meal);
                    }
                });
    }


       public Completable deleteFavMeal(Meal meal) {
           meal.setFav(false);
        return localMealsDao.updateFav(meal.getIdMeal(),false);

    }

    public Single<List<Meal>> getFavMeals() {
        return localMealsDao.getFavMeals();
    }

    public Completable addMealToCalendar(Meal meal, String date) {
        return localMealsDao.exists(meal.getIdMeal())
                .flatMapCompletable(
                        count->{
                            if(count>0)
                                return  localMealsDao.updateCalendar(meal.getIdMeal(), true,date);
                        else{
            meal.setCalendar(true);
            meal.setCalendarDate(date);
            meal.setFav(false);
           return localMealsDao.insertMeal(meal);
        }});

    }

    public Completable removeMealFromCalendar(Meal meal) {
        meal.setCalendar(false);
        meal.setCalendarDate(null);
        return localMealsDao.updateCalendar(meal.getIdMeal(), false, null);

    }

    public Single<List<Meal>> getCalendarMeals() {
        return localMealsDao.getCalendarMeals();
    }
}
