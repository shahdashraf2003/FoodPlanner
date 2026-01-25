package com.example.foodplanner.prsentation.auth.signup.presenter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.prsentation.auth.login.view.LoginContract;
import com.example.foodplanner.prsentation.auth.signup.view.SignupView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class SignupPresenterImp implements SignupPresenter {
    private SignupView view;
    private FirebaseAuth auth;

    public SignupPresenterImp(SignupView view) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
    }

    public void signup(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            UserModel userModel = new UserModel(firebaseUser.getUid(), name, email);
                            view.onSignupSuccess(userModel);
                        } else {
                            String errorMsg = task.getException() != null ? task.getException().getMessage() : "Signup failed";
                            view.onSignupError(errorMsg);
                        }
                    }
                });
    }
}