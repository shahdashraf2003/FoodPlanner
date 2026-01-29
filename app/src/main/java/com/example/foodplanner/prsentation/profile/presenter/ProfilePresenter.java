package com.example.foodplanner.prsentation.profile.presenter;

import io.reactivex.rxjava3.core.Completable;

public interface ProfilePresenter {
    void loadUser();
    Completable logout();
    Completable uploadAndClearMeals(String userId);
}
