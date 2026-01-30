package com.example.foodplanner.prsentation.profile.presenter;

import android.content.Context;

import com.example.foodplanner.data.auth.datasource.local.SessionManager;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.data.sync.model.MealSyncModel;
import com.example.foodplanner.prsentation.profile.view.ProfileView;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter {

    private final ProfileView view;
    private final SessionManager sessionManager;
    private final MealSyncModel mealSyncModel;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public ProfilePresenterImp(ProfileView view, Context context, LocalMealsDao localMealsDao) {
        this.view = view;
        this.sessionManager = new SessionManager(context);
        this.mealSyncModel = new MealSyncModel(localMealsDao, FirebaseDatabase.getInstance());
    }

    @Override
    public void loadUser() {
        disposables.add(
                sessionManager.getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> view.showUser(user),
                                throwable -> view.showGuestMode(),
                                () -> view.showGuestMode()
                        )
        );
    }

    @Override
    public Completable uploadAndClearMeals(String userId) {
        return mealSyncModel.uploadLocalMeals(userId)
                .andThen(mealSyncModel.clearLocalMeals())
                .andThen(sessionManager.logout())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void logout() {
        if (sessionManager.isGuest()) {
            disposables.add(
                    sessionManager.logout()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> view.openLoginScreen(),
                                    throwable -> view.showMessage("Logout failed: " + throwable.getMessage())
                            )
            );
        } else {
            String userId = sessionManager.getUserId();
            if (userId != null) {
                disposables.add(
                        uploadAndClearMeals(userId)
                                .subscribe(
                                        () -> view.openLoginScreen(),
                                        throwable -> view.showMessage("Logout failed: " + throwable.getMessage())
                                )
                );
            } else {
                disposables.add(
                        sessionManager.logout()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> view.openLoginScreen(),
                                        throwable -> view.showMessage("Logout failed: " + throwable.getMessage())
                                )
                );
            }
        }
    }

    public void onDestroy() {
        disposables.clear();
    }
}
