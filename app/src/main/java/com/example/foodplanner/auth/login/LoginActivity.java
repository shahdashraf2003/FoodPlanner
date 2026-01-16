package com.example.foodplanner.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.auth.data.model.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginContract.View  {
    private FirebaseAuth auth;
    private TextInputEditText loginEmail ,loginPassword;
    private TextView signup;
    private MaterialButton skip , loginButton,google;
    private LoginPresenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        loginEmail=findViewById(R.id.et_EmailAddress);
        loginPassword=findViewById(R.id.et_Pass);
        signup=findViewById(R.id.signup);
        loginButton = findViewById(R.id.btn_login);
        skip = findViewById(R.id.btn_skip);
        google = findViewById(R.id.btn_google);
        presenter=new LoginPresenter(
                LoginActivity.this,
                auth
        );
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(loginEmail.getText().toString(),loginPassword.getText().toString());
            }
        });
    }

    @Override
    public void onLoginSuccess(UserModel user) {
        Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}