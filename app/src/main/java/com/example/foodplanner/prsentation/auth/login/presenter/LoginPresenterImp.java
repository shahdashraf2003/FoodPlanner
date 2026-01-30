package com.example.foodplanner.prsentation.auth.login.presenter;

import android.content.Context;

import com.example.foodplanner.data.auth.repository.AuthRepository;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.data.sync.model.MealSyncModel;
import com.example.foodplanner.prsentation.auth.login.view.LoginView;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImp implements LoginPresenter {

    private final LoginView view;
    private final AuthRepository repository;
    private final MealSyncModel mealSyncModel;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public LoginPresenterImp(LoginView view, Context context, LocalMealsDao localMealsDao) {
        this.view = view;
        this.repository = new AuthRepository(context);
        this.mealSyncModel = new MealSyncModel(localMealsDao, com.google.firebase.database.FirebaseDatabase.getInstance());
    }

    @Override
    public void login(String email, String password) {
        disposables.add(
                repository.login(email, password)
                        .flatMapCompletable(user ->
                                repository.saveUser(user)
                                        .andThen(mealSyncModel.fetchRemoteMeals(user.getUserID())
                                                .flatMapCompletable(remoteMeals ->
                                                        mealSyncModel.clearLocalMeals()
                                                                .andThen(mealSyncModel.saveRemoteMealsToLocal(remoteMeals))
                                                )
                                        )
                        )
                        .andThen(repository.getCurrentUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> view.onLoginLoading())
                        .doFinally(() -> view.onLoginFinished())
                        .subscribe(
                                user -> view.onLoginSuccess(user),
                                throwable -> view.onLoginError(getErrorMessage(throwable))
                        )
        );
    }

    @Override
    public void loginWithGoogle(String idToken) {
        disposables.add(
                repository.loginWithGoogle(idToken)
                        .flatMapCompletable(user ->
                                repository.saveUser(user)
                                        .andThen(
                                                mealSyncModel.fetchRemoteMeals(user.getUserID())
                                                        .flatMapCompletable(remoteMeals ->
                                                                mealSyncModel.clearLocalMeals()
                                                                        .andThen(mealSyncModel.saveRemoteMealsToLocal(remoteMeals))
                                                        )
                                        )
                        )
                        .andThen(repository.getCurrentUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> view.onLoginLoading())
                        .doFinally(() -> view.onLoginFinished())
                        .subscribe(
                                user -> view.onLoginSuccess(user),
                                throwable -> view.onLoginError(getErrorMessage(throwable))
                        )
        );
    }

    @Override
    public void loginAsGuest() {
        disposables.add(
                repository.saveGuest()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> view.onLoginLoading())
                        .doFinally(() -> view.onLoginFinished())
                        .subscribe(
                                () -> view.onGuestLogin(),
                                throwable -> view.onLoginError(throwable.getMessage())
                        )
        );
    }

    private String getErrorMessage(Throwable throwable) {
        if (throwable instanceof FirebaseAuthInvalidUserException) {
            return "No account found with this email.";
        } else if (throwable instanceof FirebaseAuthInvalidCredentialsException) {
            return "Incorrect email or password.";
        } else if (throwable instanceof FirebaseAuthUserCollisionException) {
            return "This email is already in use.";
        } else if (throwable instanceof FirebaseNetworkException) {
            return "Please check your internet connection.";
        } else {
            return "Email and Password are required.";
        }
    }

    public void onDestroy() {
        disposables.clear();
    }
}
