package com.example.foodplanner.prsentation.auth.login.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.data.meal.datasource.local.LocalMealsDao;
import com.example.foodplanner.database.AppDB;
import com.example.foodplanner.prsentation.MainActivity;
import com.example.foodplanner.prsentation.auth.login.presenter.LoginPresenterImp;
import com.example.foodplanner.prsentation.auth.signup.view.SignupActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private FirebaseAuth auth;
    private TextInputEditText loginEmail ,loginPassword;
    private TextView signup;
    private MaterialButton skip , loginButton,google;
    private LoginContract.Presenter presenter;




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
        LocalMealsDao localMealsDao = AppDB.getInstance(this).localMealsDao();
        presenter = new LoginPresenterImp(this, this, localMealsDao);

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String pass = loginPassword.getText().toString().trim();
            presenter.login(email, pass);
        });

        skip.setOnClickListener(v -> presenter.loginAsGuest());
      /* google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            presenter.loginWithGoogle();
              
            }
        });*/
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }




    @Override
    public void onLoginSuccess(UserModel user) {
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
        View rootView = findViewById(android.R.id.content);

        showSnack(rootView,message);
    }

    private void openHomeScreen() {
        startActivity(new Intent( this, MainActivity.class));
    }



}