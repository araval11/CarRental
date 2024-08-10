package com.example.carrentalfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public Button login;
    public Button register;
    public EditText email;
    public EditText passwordText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        email = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);


        if (isLoggedIn()) {
            navigateToUserViewActivity();
        } else {
            ClickListenHandler();
        }
    }

    private boolean isLoggedIn() {
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        return !savedUsername.isEmpty() && !savedPassword.isEmpty();
    }

    private void navigateToUserViewActivity() {
        Intent intent = new Intent(LoginActivity.this, user_view_activity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void ClickListenHandler(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = email.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                String savedUsername = sharedPreferences.getString("username", "");
                String savedPassword = sharedPreferences.getString("password", "");

                if (username.equals(savedUsername) && password.equals(savedPassword)) {
                    navigateToUserViewActivity();
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homePage = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(homePage);
            }
        });
    }
}
