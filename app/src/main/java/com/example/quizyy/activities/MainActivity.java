package com.example.quizyy.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.quizyy.R;
import com.example.quizyy.adapters.QuizAdapter;
import com.example.quizyy.models.Quiz;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    FirebaseFirestore fireStore;
    MaterialToolbar appBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    RecyclerView quizRecyclerView;
    QuizAdapter adapter;
    ArrayList<Quiz> quizzes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpViews();
    }

    private void setUpViews() {
        setupFireStore();
        setupDrawerLayout();
        setupRecyclerView();
        setupDatePicker();
    }

    private void setupDatePicker() {
        FloatingActionButton btnDatePicker;
        Intent intent = new Intent(this, QuestionActivity.class);
        btnDatePicker = findViewById(R.id.btn_date_picker);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker().build();
                datePicker.show(getSupportFragmentManager(), "DatePicker");
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                    String dateStr = dateFormatter.format(selection);
                    Log.i("Date", "onPositiveButtonClick: "+ dateStr);
                    intent.putExtra("DATE", dateStr);
                    startActivity(intent);
                });
                datePicker.addOnNegativeButtonClickListener(v1 -> {
                    Log.i("Date", "onNegativeButtonListener: "+ datePicker.getHeaderText());
                });


            } //end of onclick
        });
    }

    private void setupFireStore() {
        fireStore = FirebaseFirestore.getInstance();
        Log.i("AliKhan", ": entered : setupFireStore: entered");
        fireStore.collection("quizzes").addSnapshotListener((value, error) -> {
            Log.i("AliKhan", "setupFireStore: entered Snapshot Listener");
            Log.w("AliError", "setupFireStore: ----> \n\n", error);
           if(value != null){
               Log.i("AliDone", value.toObjects(Quiz.class).toString());
               //Toast.makeText(this, "Data Has Been Retrieved", Toast.LENGTH_SHORT);

           }
            if(value == null || error != null){
                Toast.makeText(this, "Cannot retrieve Data", Toast.LENGTH_SHORT);
            }
            quizzes.clear();
            quizzes.addAll(value.toObjects(Quiz.class));
            adapter.notifyDataSetChanged();


        });

    }

    private void setupRecyclerView() {
        quizRecyclerView = findViewById(R.id.quizRecyclerView);
        adapter = new QuizAdapter(this, quizzes);
        quizRecyclerView.setLayoutManager(new GridLayoutManager(this, 2) );
        quizRecyclerView.setAdapter(adapter);


    }

    private void setupDrawerLayout() {
        Intent intentProfile = new Intent(this, ProfileActivity.class);
        Intent intentSignout = new Intent(this, LoginActivity.class);
        navigationView = findViewById(R.id.navigationView);
        appBar = findViewById(R.id.topAppBar);
        setSupportActionBar(appBar);
        drawerLayout = findViewById(R.id.mainDrawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navProfile){
                startActivity(intentProfile);
                finish();
                drawerLayout.closeDrawers();
            }

            return true;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.navSignout){
                startActivity(intentSignout);
                finish();
                drawerLayout.closeDrawers();
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}