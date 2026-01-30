package com.example.foodplanner.data.auth.datasource.remote;

import com.example.foodplanner.data.auth.model.UserModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDataSource {

    private final FirebaseAuth auth;

    public AuthRemoteDataSource() {
        auth = FirebaseAuth.getInstance();
    }

    public Single<UserModel> login(String email, String password) {
        return Single.create(emitter ->
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(result -> {
                            FirebaseUser user = result.getUser();
                            if (user != null) {
                                emitter.onSuccess(new UserModel(
                                        user.getUid(),
                                        user.getDisplayName() != null ? user.getDisplayName() : "User",
                                        user.getEmail()
                                ));
                            } else {
                                emitter.onError(new Throwable("User is null"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<UserModel> loginWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return Single.create(emitter ->
                auth.signInWithCredential(credential)
                        .addOnSuccessListener(result -> {
                            FirebaseUser user = result.getUser();
                            if (user != null) {
                                emitter.onSuccess(new UserModel(
                                        user.getUid(),
                                        user.getDisplayName() != null ? user.getDisplayName() : "User",
                                        user.getEmail()
                                ));
                            } else {
                                emitter.onError(new Throwable("User is null"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }
}
