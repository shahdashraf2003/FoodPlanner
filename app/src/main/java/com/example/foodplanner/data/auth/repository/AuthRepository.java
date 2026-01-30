package com.example.foodplanner.data.auth.repository;

import android.content.Context;
import com.example.foodplanner.data.auth.datasource.local.SessionManager;
import com.example.foodplanner.data.auth.datasource.remote.AuthRemoteDataSource;
import com.example.foodplanner.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRepository {

    private final AuthRemoteDataSource remoteDataSource;
    private final SessionManager sessionManager;

    public AuthRepository(Context context) {
        this.remoteDataSource = new AuthRemoteDataSource();
        this.sessionManager = new SessionManager(context);
    }

    public Single<UserModel> login(String email, String password) {
        return remoteDataSource.login(email, password);
    }

    public Single<UserModel> loginWithGoogle(String idToken) {
        return remoteDataSource.loginWithGoogle(idToken);
    }

    public Completable saveUser(UserModel user) {
        return sessionManager.saveUser(user);
    }

    public Completable saveGuest() {
        return sessionManager.saveGuest();
    }

    public Completable logout() {
        return sessionManager.logout();
    }

    public Single<UserModel> getCurrentUser() {
        return sessionManager.getUser().toSingle();
    }

    public boolean isGuest() {
        return sessionManager.isGuest();
    }
}
