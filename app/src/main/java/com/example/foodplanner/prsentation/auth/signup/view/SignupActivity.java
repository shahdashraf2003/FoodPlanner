package com.example.foodplanner.prsentation.auth.signup.view;

import static com.example.foodplanner.utils.SnackBarUtil.showSnack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.foodplanner.R;
import com.example.foodplanner.data.auth.model.UserModel;
import com.example.foodplanner.prsentation.MainActivity;
import com.example.foodplanner.prsentation.auth.login.view.LoginActivity;
import com.example.foodplanner.prsentation.auth.signup.presenter.SignupPresenterImp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity implements SignupView{

    SignupPresenterImp presenter;
    private TextInputEditText etName, etEmail, etPassword;
    private MaterialButton btnSignup;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        presenter = new SignupPresenterImp(this);

        etName = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email_signup);
        etPassword = findViewById(R.id.et_password_signup);
        btnSignup = findViewById(R.id.btn_signup);
        login=findViewById(R.id.btn_login_redirect);
        btnSignup.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showSnack(findViewById(android.R.id.content), "Please fill all fields");
                return;
            }

            presenter.signup(name, email, password);
        });
        login.setOnClickListener(v->{
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
    @Override
    public void onSignupSuccess(UserModel user) {
        showSnack(findViewById(android.R.id.content),"Signup Successful! Welcome "+user.getUserName());
        startActivity(new Intent(this, LoginActivity.class));


    }

    @Override
    public void onSignupError(String message) {
        showSnack(findViewById(android.R.id.content),message);


    }
}