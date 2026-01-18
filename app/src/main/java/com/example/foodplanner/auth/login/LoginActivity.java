package com.example.foodplanner.auth.login;

import static com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL;

import android.content.Intent;
import android.credentials.GetCredentialException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;

import com.example.foodplanner.MainActivity;
import com.example.foodplanner.R;
import com.example.foodplanner.auth.data.model.UserModel;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements LoginContract.View  {
    private FirebaseAuth auth;
    private TextInputEditText loginEmail ,loginPassword;
    private TextView signup;
    private MaterialButton skip , loginButton,google;
    private LoginPresenter presenter;


    CredentialManager credentialManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(getString(R.string.default_web_client_id))
                .build();

        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();


        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.et_EmailAddress);
        loginPassword = findViewById(R.id.et_Pass);
        signup = findViewById(R.id.signup);
        loginButton = findViewById(R.id.btn_login);
        skip = findViewById(R.id.btn_skip);
        google = findViewById(R.id.btn_google);
        presenter = new LoginPresenter(
                LoginActivity.this,
                auth
        );


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(loginEmail.getText().toString(), loginPassword.getText().toString());
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 credentialManager = CredentialManager.create(LoginActivity.this);

              
            }
        });
    }

    @Override
    public void onLoginSuccess(UserModel user) {
        Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(
                this, MainActivity.class));
        loginEmail.setText("");
        loginPassword.setText("");
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}