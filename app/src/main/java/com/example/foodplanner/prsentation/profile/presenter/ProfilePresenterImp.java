package com.example.foodplanner.prsentation.profile.presenter;

import android.content.Context;

import com.example.foodplanner.data.auth.local.SessionManager;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.data.sync.model.MealSyncModel;
import com.example.foodplanner.prsentation.profile.view.ProfileView;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView view;
    private SessionManager sessionManager;
    private MealSyncModel mealSyncModel;

    public ProfilePresenterImp(ProfileView view, Context context, LocalMealsDao localMealsDao){
        this.view = view;
        this.sessionManager = new SessionManager(context);
        this.mealSyncModel = new MealSyncModel(localMealsDao, FirebaseDatabase.getInstance());
    }


    @Override
    public void loadUser() {
        sessionManager.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> view.showUser(user),
                        throwable -> view.showGuestMode(),
                        () -> view.showGuestMode()
                );
    }

    @Override
    public Completable logout() {
       return sessionManager.logout();
    }

    public Completable uploadAndClearMeals(String userId) {
        return mealSyncModel.uploadLocalMeals(userId)
                .andThen(mealSyncModel.clearLocalMeals())
                .andThen(sessionManager.logout());
    }

}
