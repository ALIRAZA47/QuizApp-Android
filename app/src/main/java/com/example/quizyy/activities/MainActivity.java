package com.example.quizyy.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quizyy.R;
import com.example.quizyy.adapters.QuizAdapter;
import com.example.quizyy.models.Quiz;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
        Quiz sample = new Quiz();
        Quiz sample2 = new Quiz();
        Quiz sample3 = new Quiz();
//        sample.id="1234";
        sample.title="ali";
//        sample2.id="456";
        sample2.title="raza";
//        sample3.id="789";
        sample3.title="khan";
        quizzes.add(sample);
        quizzes.add(sample2);
        quizzes.add(sample3);
        quizRecyclerView = findViewById(R.id.quizRecyclerView);
        adapter = new QuizAdapter(this, quizzes);
        quizRecyclerView.setLayoutManager(new GridLayoutManager(this, 2) );
        quizRecyclerView.setAdapter(adapter);


    }

    private void setupDrawerLayout() {
        appBar = findViewById(R.id.topAppBar);
        setSupportActionBar(appBar);
        drawerLayout = findViewById(R.id.mainDrawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}