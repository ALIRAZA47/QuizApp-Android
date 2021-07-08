package com.example.quizyy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.R;
import com.example.quizyy.adapters.CourseAdapter;
import com.example.quizyy.adapters.QuizAdapter;
import com.example.quizyy.models.Course;
import com.example.quizyy.models.Question;
import com.example.quizyy.models.Quiz;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    FirebaseFirestore fireStore;
    MaterialToolbar appBar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    RecyclerView courseRecyclerView;
    CourseAdapter adapter;
    ArrayList<Course> courses = new ArrayList<>();
    ArrayList<Quiz> quizzes = new ArrayList<>();
    HashMap<String, Question> questions = new HashMap<>();

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
    }


    private void setupFireStore() {
        fireStore = FirebaseFirestore.getInstance();
        Log.i("AliKhan", ": entered : setupFireStore: entered");
        fireStore.collection("courses")
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (snapshots != null && !snapshots.isEmpty()) {
                        Log.i("Courses", snapshots.toObjects(Course.class).toString());
//                        quizzes.clear();

                    } else {
                        Log.i("Courses", "No data retrieved--");
                    }

                    courses.addAll(snapshots.toObjects(Course.class));

//                    questions = quizzes.get("quiz1").questions;
//            quizzes.clear();
//            courses.addAll(snapshots.toObjects(Course.class));
//            quizzes = courses.get(0).quizzes;
//            questions = quizzes.get("quiz1").questions;
                    adapter.notifyDataSetChanged();


                });

    }

    private void setupRecyclerView() {
        courseRecyclerView = findViewById(R.id.courseRecyclerView);
        Log.i("TAG-ALI", "setupRecyclerView: " + courses.toString());
        adapter = new CourseAdapter(this, courses);
        courseRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        courseRecyclerView.setAdapter(adapter);


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
            if (item.getItemId() == R.id.navProfile) {
                startActivity(intentProfile);
                finish();
                drawerLayout.closeDrawers();
            }

            return true;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navSignout) {
                startActivity(intentSignout);
                finish();
                drawerLayout.closeDrawers();
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}