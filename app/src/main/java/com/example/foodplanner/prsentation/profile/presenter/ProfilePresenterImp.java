package com.example.foodplanner.prsentation.profile.presenter;

import android.content.Context;
import com.example.foodplanner.data.auth.local.SessionManager;
import com.example.foodplanner.prsentation.profile.view.ProfileView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImp implements ProfilePresenter {

    private ProfileView view;
    private SessionManager sessionManager;

    public ProfilePresenterImp(ProfileView view, Context context){
        this.view = view;
        this.sessionManager = new SessionManager(context);
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
}
