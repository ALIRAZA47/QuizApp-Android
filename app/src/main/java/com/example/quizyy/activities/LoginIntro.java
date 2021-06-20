package com.example.quizyy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quizyy.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginIntro extends AppCompatActivity {
    Button btnGetStarted;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_intro);
        mAuth = FirebaseAuth.getInstance();
        btnGetStarted = findViewById(R.id.btnGetStarted);

        //Check if the use is already logged in or not
        if(mAuth.getCurrentUser() != null){
            redirectToActivities("MAIN");
        }
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToActivities("LOGIN");
            }
        });
    }

    private void redirectToActivities(String name) {
        Intent intent;
        if(name.equals("MAIN")){
            intent=new Intent(this, MainActivity.class);
        }
        else if(name.equals("LOGIN")){
            intent=new Intent(this, LoginActivity.class);
        }
        else{
            throw new RuntimeException("Invalid path");
        }
        startActivity(intent);
        finish();

    }
}