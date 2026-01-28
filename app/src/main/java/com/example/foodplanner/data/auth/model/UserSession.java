package com.example.foodplanner.data.auth.model;

public class UserSession {
    private boolean isLoggedIn;
    private boolean isGuest;

    public UserSession(boolean isLoggedIn, boolean isGuest) {
        this.isLoggedIn = isLoggedIn;
        this.isGuest = isGuest;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
