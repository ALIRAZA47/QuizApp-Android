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

public class SignpActivity extends AppCompatActivity {
    EditText email;
    EditText password, confirmPassword;
    Button btnSignup;
    TextView btnLogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signp);
        mAuth = FirebaseAuth.getInstance();
        btnSignup = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnTextLogin);

        Intent intentLoginActivity = new Intent(this, LoginActivity.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentLoginActivity);
                finish();
            }
        });
    }

    private void signUpNewUser(){
        Intent intentLoginActivity = new Intent(this, MainActivity.class);
        email = findViewById(R.id.signUpEmailAddress);
        password = findViewById(R.id.signUpPassword);
        confirmPassword = findViewById(R.id.signUpPasswordConfirm);
        String emailStr = email.getText().toString();
        String passStr = password.getText().toString();
        String passConfirmStr = confirmPassword.getText().toString();
        Log.i("AliKhan", "Pass is - "+passStr+" and confirm paas is - "+passConfirmStr);
        if(emailStr.isEmpty() || passStr.isEmpty()){
            Toast.makeText(this, "Please Provide Email & Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passStr.equals(passConfirmStr)){
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passStr.length() < 6){
            Toast.makeText(this, "Password must contain at least 6 Character", Toast.LENGTH_SHORT).show();

        }
        mAuth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, Make a Toast to Confirm
                    Toast.makeText(SignpActivity.this, "Registered User Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(intentLoginActivity);
                    finish();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth", "onComplete: ", task.getException());
                    Toast.makeText(SignpActivity.this, "Couldn't Signup", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}