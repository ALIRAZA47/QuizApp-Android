package com.example.quizyy.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.R;
import com.example.quizyy.adapters.OptionAdapter;
import com.example.quizyy.models.Course;
import com.example.quizyy.models.Question;
import com.example.quizyy.models.Quiz;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivity extends AppCompatActivity {

    Button btnPrev;
    Button btnNext;
    ArrayList<Course> courses = new ArrayList<>();

    Button btnSubmit;

    ArrayList<Quiz> quizzes = new ArrayList<>();
    HashMap<String, Question> questions = new HashMap<String, Question>();
    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setupFireStore();
        setupEventListeners();
    }

    private void bindViews() {

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);


        btnNext.setVisibility(View.GONE);
        btnPrev.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);

        if (index == 1 && questions.size() == 1) {
            btnSubmit.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            btnNext.setVisibility(View.VISIBLE);
        } else if (index == questions.size()) {
            btnPrev.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }

        Question quest = questions.get("question"+index);
        if (quest != null) {
            Log.i("Qali", "bindViews: --> " + questions + " and size of Question is --> " + questions.size());
            TextView description = findViewById(R.id.quizDescription);
            description.setText(quest.description);
            description.setTypeface(Typeface.DEFAULT_BOLD);
            OptionAdapter optionAdapter = new OptionAdapter(this, quest);
            RecyclerView optionList = findViewById(R.id.optionsList);
            optionList.setLayoutManager(new LinearLayoutManager(this));
            optionList.setAdapter(optionAdapter);
            optionList.hasFixedSize();
        }


    }

    private void setupFireStore() {
        FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
        String date = new String();
        String courseName = new String();
        date = getIntent().getStringExtra("DATE");
        courseName = getIntent().getStringExtra("courseName");
        if (date != null) {
            String finalDate = date;
            fireStore.collection("courses").whereEqualTo("courseName", courseName)
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        if (snapshots != null && !snapshots.isEmpty()) {
                            Log.i("DateWithCourse", snapshots.toObjects(Course.class).toString());
                            courses.addAll(snapshots.toObjects(Course.class));
                            Log.i("TAG-HEHE", "setupFireStore: -->"+courses.get(0).toString());

                            for(int i = 1; i <= courses.get(0).quizzes.size(); i++){
                                Quiz quiz = courses.get(0).quizzes.get("quiz"+i);
                                if(quiz.title.equals(finalDate)){
                                    quizzes.add(quiz);
                                }
                            }
                            questions = (HashMap<String, Question>) quizzes.get(0).questions;
                            bindViews();
                        } else {
                            Toast.makeText(this, "No Quiz for given Date::", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
        }

    }

    private void setupEventListeners() {
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
        Intent intent = new Intent(this, ResultActivity.class);
        btnPrev.setOnClickListener(v -> {
            index--;
            bindViews();
        });

        btnNext.setOnClickListener(v -> {
            index++;
            bindViews();
        });

        btnSubmit.setOnClickListener(v -> {
            Log.i("RESULTDATA", "setupEventListeners: data before send--> " + questions.toString());
            intent.putExtra("QMap", new Gson().toJson(quizzes.get(0)));
            startActivity(intent);
        });
    }

}