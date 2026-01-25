package com.example.foodplanner.prsentation.auth.login.view;

import com.example.foodplanner.data.auth.model.UserModel;

public interface LoginContract {
    interface View {
        void onLoginSuccess(UserModel user);
        void onLoginError(String message);
    }

    interface Presenter {
        void login(String email, String password);
        void loginWithGoogle(String idToken);

    }
}
