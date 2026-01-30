package com.example.foodplanner.data.auth.datasource.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.foodplanner.data.auth.model.UserModel;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_GUEST = "is_guest";

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("app_session", Context.MODE_PRIVATE);
    }

    public Completable saveUser(UserModel user) {
        return Completable.fromAction(() -> {
            sharedPreferences.edit()
                    .putString(KEY_USER_ID, user.getUserID())
                    .putString(KEY_USER_NAME, user.getUserName())
                    .putString(KEY_USER_EMAIL, user.getEmail())
                    .putBoolean(KEY_IS_GUEST, false)
                    .apply();
        });
    }

    public Completable saveGuest() {
        return Completable.fromAction(() -> sharedPreferences.edit().putBoolean(KEY_IS_GUEST, true).apply());
    }

    public Maybe<UserModel> getUser() {
        return Maybe.create(emitter -> {
            String id = sharedPreferences.getString(KEY_USER_ID, null);
            String name = sharedPreferences.getString(KEY_USER_NAME, null);
            String email = sharedPreferences.getString(KEY_USER_EMAIL, null);

            if (id != null && name != null && email != null) {
                emitter.onSuccess(new UserModel(id, name, email));
            }
            emitter.onComplete();
        });
    }

    public boolean isGuest() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }

    public Completable logout() {
        return Completable.fromAction(() -> {
            sharedPreferences.edit().clear().apply();
        });
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }
}
