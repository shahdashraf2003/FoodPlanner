package com.example.foodplanner.prsentation.auth.login.view;

import com.example.foodplanner.data.auth.model.UserModel;

public interface LoginView {
    void onLoginSuccess(UserModel user);
    void onLoginError(String message);
    void onLoginLoading();
}
