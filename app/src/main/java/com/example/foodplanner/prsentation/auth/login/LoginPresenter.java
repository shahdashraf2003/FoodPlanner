package com.example.foodplanner.prsentation.auth.login;




import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CustomCredential;
import com.example.foodplanner.prsentation.auth.data.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View view;
    private FirebaseAuth auth;

    public LoginPresenter(LoginContract.View view, FirebaseAuth auth) {
        this.view = view;
        this.auth = auth;
    }

    @Override

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.onLoginError("Email and Password are required");
            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            UserModel user = new UserModel(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
                            view.onLoginSuccess(user);

                        } else {
                            Exception e = task.getException();
                            if (e != null) {
                                if (e instanceof FirebaseAuthException) {
                                    String errorCode = ((FirebaseAuthException) e).getErrorCode();

                                    switch (errorCode) {
                                        case "ERROR_INVALID_EMAIL":
                                            view.onLoginError("Invalid email format");
                                            break;
                                        case "ERROR_USER_NOT_FOUND":
                                            view.onLoginError("No account found with this email");
                                            break;
                                        case "ERROR_WRONG_PASSWORD":
                                            view.onLoginError("Incorrect password");
                                            break;
                                        case "ERROR_USER_DISABLED":
                                            view.onLoginError("This user account has been disabled");
                                            break;
                                        default:
                                            view.onLoginError(e.getMessage());
                                    }
                                } else {
                                    view.onLoginError(e.getMessage());
                                }
                            } else {
                                view.onLoginError("Unknown error occurred");
                            }


                        }

                    }
                }
        );
    }
    public void handleSignIn(Credential credential) {
        // Check if credential is of type Google ID
        if (credential instanceof CustomCredential customCredential
                && credential.getType().equals(TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            // Create Google ID Token
            Bundle credentialData = customCredential.getData();
            GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credentialData);

            // Sign in to Firebase with using the token
            loginWithGoogle(googleIdTokenCredential.getIdToken());
        } else {

        }
    }
    @Override
    public void loginWithGoogle(String idToken) {

            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            auth.signInWithCredential(credential)
                    .addOnCompleteListener((Executor) this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            UserModel user = new UserModel(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail());
                            view.onLoginSuccess(user);


                        } else {
                            view.onLoginError(task.getException().getMessage());
                        }
                    });
        }



}