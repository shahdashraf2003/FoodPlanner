package com.example.foodplanner.data.sync.model;

import android.util.Log;

import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.data.meal.model.Meal;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class MealSyncModel {

    private final LocalMealsDao localMealsDao;
    private final FirebaseDatabase firebaseDatabase;

    public MealSyncModel(LocalMealsDao localMealsDao, FirebaseDatabase firebaseDatabase) {
        this.localMealsDao = localMealsDao;
        this.firebaseDatabase = firebaseDatabase;
    }

    public Completable uploadLocalMeals(String userId) {
        return localMealsDao.getMeals()
                .flattenAsObservable(meals -> meals)
                .flatMapCompletable(meal -> {
                    Log.d("SYNC", "Uploading meal: " + meal.getIdMeal());
                    return Completable.fromAction(() ->
                            firebaseDatabase.getReference("meals")
                                    .child(userId)
                                    .child(meal.getIdMeal())
                                    .setValue(meal)
                    );
                });
    }

    public Completable clearLocalMeals() {
        return localMealsDao.clearMeals();
    }

    public Single<List<Meal>> fetchRemoteMeals(String userId) {
        return Single.create(emitter -> {
            firebaseDatabase.getReference("meals")
                    .child(userId)
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        List<Meal> meals = new ArrayList<>();
                        for (var child : snapshot.getChildren()) {
                            Meal meal = child.getValue(Meal.class);
                            if (meal != null && meal.getIdMeal() != null) meals.add(meal);
                        }
                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable saveRemoteMealsToLocal(List<Meal> meals) {
        return localMealsDao.insertMeals(meals);
    }

    public Completable syncRemoteMealsToLocal(String userId) {
        return fetchRemoteMeals(userId)
                .flatMapCompletable(meals ->
                        clearLocalMeals()
                                .andThen(saveRemoteMealsToLocal(meals))
                );
    }
}
