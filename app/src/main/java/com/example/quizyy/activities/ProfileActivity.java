package com.example.quizyy.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizyy.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView txtEmail;
    Button btnSignOut;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        intent = new Intent(this, LoginActivity.class);

        txtEmail = findViewById(R.id.txtEmail);
        btnSignOut = findViewById(R.id.btnLogout);
        if(firebaseAuth.getCurrentUser() != null){
            txtEmail.setText(firebaseAuth.getCurrentUser().getEmail());
        }

        btnSignOut.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(intent);
            finish();
        });

    }

}