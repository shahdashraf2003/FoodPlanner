package com.example.foodplanner.prsentation.auth.login.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.database.AppDB;
import com.example.foodplanner.prsentation.MainActivity;
import com.example.foodplanner.prsentation.auth.login.presenter.LoginPresenter;
import com.example.foodplanner.prsentation.auth.login.presenter.LoginPresenterImp;
import com.example.foodplanner.prsentation.auth.signup.view.SignupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private FirebaseAuth auth;
    private TextInputEditText loginEmail, loginPassword;
    private TextView signup;
    private MaterialButton skip, loginButton, google;
    private LoginPresenter presenter;
    private ProgressBar progressBar;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.et_EmailAddress);
        loginPassword = findViewById(R.id.et_Pass);
        signup = findViewById(R.id.sginup);
        loginButton = findViewById(R.id.btn_login);
        skip = findViewById(R.id.btn_skip);
        google = findViewById(R.id.btn_google);
        progressBar = findViewById(R.id.pd_login);

        LocalMealsDao localMealsDao = AppDB.getInstance(this).localMealsDao();
        presenter = new LoginPresenterImp(this, this, localMealsDao);

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();
            presenter.login(email, pass);
        });

        skip.setOnClickListener(v -> presenter.loginAsGuest());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        google.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 1001);
        });


        signup.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    @Override
    public void onLoginSuccess(UserModel user) {
        progressBar.setVisibility(View.GONE);
        View rootView = findViewById(android.R.id.content);

        if(user != null){
            showSnack(rootView, "Welcome back " + user.getUserName());
        } else {
            showSnack(rootView, "Welcome Guest");
        }

        loginEmail.setText("");
        loginPassword.setText("");
        openHomeScreen();
    }

    @Override
    public void onLoginError(String message) {
        progressBar.setVisibility(View.GONE);
        View rootView = findViewById(android.R.id.content);
        showSnack(rootView, message);
    }

    @Override
    public void onGuestLogin() {
        progressBar.setVisibility(View.GONE);
        View rootView = findViewById(android.R.id.content);
        showSnack(rootView, "Welcome Guest");
        openHomeScreen();
    }

    @Override
    public void onLoginLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginFinished() {
        progressBar.setVisibility(View.GONE);
    }

    private void openHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter instanceof LoginPresenterImp){
            ((LoginPresenterImp) presenter).onDestroy();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(Exception.class);
                if(account != null && account.getIdToken() != null){
                    presenter.loginWithGoogle(account.getIdToken());
                }
            } catch (Exception e) {
                View rootView = findViewById(android.R.id.content);
                showSnack(rootView, "Google Sign-In failed: " + e.getMessage());
            }
        }
    }

}
