package com.example.foodplanner.prsentation.profile.view;

import com.example.foodplanner.data.auth.model.UserModel;

public interface ProfileView {
    void showUser(UserModel user);
    void showGuestMode();
    void openLoginScreen();
    void showMessage(String message);
    void closeApp();
}
