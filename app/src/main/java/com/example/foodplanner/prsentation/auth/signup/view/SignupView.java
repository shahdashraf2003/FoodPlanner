package com.example.foodplanner.prsentation.auth.signup.view;

import com.example.foodplanner.data.auth.model.UserModel;

public interface SignupView {
    void onSignupSuccess(UserModel user);
    void onSignupError(String message);
    void onSignupLoading();

}
