package com.example.quizyy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizyy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnLogin;
    TextView signUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //Set listener on Login Button
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        signUp = findViewById(R.id.btnTextSignUp);
        Intent intentSignUpActivity = new Intent(this, SignpActivity.class);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LoginActivity.this, "HEHE", Toast.LENGTH_SHORT).show();
                startActivity(intentSignUpActivity);
            }
        });

    }

    private void loginUser() {
        email = findViewById(R.id.loginEmailAddress);
        Intent intentSignUpActivity = new Intent(this, MainActivity.class);
        password = findViewById(R.id.loginPassword);
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();
        if(emailStr.isEmpty() || passwordStr.isEmpty()){
            Toast.makeText(this, "Please Provide Email & Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordStr.length() < 6){
            Toast.makeText(this, "Password must contain at least 6 Character", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, Make a Toast to Confirm
                    Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                    startActivity(intentSignUpActivity);
                    finish();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "onComplete: ", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}