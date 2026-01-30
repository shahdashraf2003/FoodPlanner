package com.example.foodplanner.prsentation.auth.signup.presenter;

import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.prsentation.auth.signup.view.SignupView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignupPresenterImp implements SignupPresenter {
    private SignupView view;
    private FirebaseAuth auth;

    public SignupPresenterImp(SignupView view) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void signup(String name, String email, String password) {
        view.onSignupLoading();
        signupWithRx(name, email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        view::onSignupSuccess,
                        throwable -> view.onSignupError(throwable.getMessage())
                );
    }

    private Single<UserModel> signupWithRx(String name, String email, String password) {
        return Single.create(emitter ->
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            if (firebaseUser != null) {
                                emitter.onSuccess(
                                        new UserModel(firebaseUser.getUid(), name, email)
                                );
                            } else {
                                emitter.onError(new Exception("User is null"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }


}