package com.example.foodplanner.prsentation.auth.login;

import com.example.foodplanner.prsentation.auth.data.model.UserModel;

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
