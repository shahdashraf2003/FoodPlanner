package com.example.foodplanner.prsentation.auth.login.presenter;

public interface LoginPresenter {
    void login(String email, String password);
    void loginWithGoogle(String idToken);
    void loginAsGuest();
}
