package com.example.foodplanner.prsentation.auth.login.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodplanner.data.auth.local.SessionManager;
import com.example.foodplanner.data.auth.repository.AuthRepo;
import com.example.foodplanner.data.auth.repository.AuthRepoImp;
import com.example.foodplanner.prsentation.auth.login.view.LoginContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImp implements LoginContract.Presenter {

    private LoginContract.View view;
    private AuthRepo authRepo;
    private SessionManager sessionManager;

    public LoginPresenterImp(LoginContract.View view, Context context) {
        this.view = view;
        authRepo = new AuthRepoImp();
        sessionManager = new SessionManager(context);
    }

    @Override
    public void login(String email, String password) {
        authRepo.login(email, password)
                .flatMapCompletable(user -> sessionManager.saveUser(user))
                .andThen(sessionManager.getUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> view.onLoginSuccess(user),
                        throwable -> view.onLoginError(throwable.getMessage())
                );
    }



    @Override
    public void loginWithGoogle(String idToken) {
        authRepo.loginWithGoogle(idToken)
                .flatMapCompletable(user -> sessionManager.saveUser(user))
                .andThen(sessionManager.getUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> view.onLoginSuccess(user),
                        throwable -> view.onLoginError(throwable.getMessage())
                );
    }

    @Override
    public void loginAsGuest() {
        sessionManager.saveGuest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> view.onLoginSuccess(null),
                        throwable -> view.onLoginError(throwable.getMessage())
                );
    }

}
