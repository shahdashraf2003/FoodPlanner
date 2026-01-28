package com.example.foodplanner.data.auth.repository;

import com.example.foodplanner.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Single;


public interface AuthRepo {
    Single<UserModel> login(String email, String password);
    Single<UserModel> loginWithGoogle(String idToken);
}
