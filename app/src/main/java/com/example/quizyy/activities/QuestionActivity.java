package com.example.quizyy.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizyy.R;
import com.example.quizyy.adapters.OptionAdapter;
import com.example.quizyy.models.Question;
import com.example.quizyy.models.Quiz;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.compatqual.NullableType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class QuestionActivity extends AppCompatActivity {

    Button btnPrev;
    Button btnNext;
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
        }
        else if (index == 1) {
            btnNext.setVisibility(View.VISIBLE);
        }

        else if (index == questions.size()) {
            btnPrev.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnPrev.setVisibility(View.VISIBLE);
        }

        Question quest = questions.get("question"+index);
        if (quest != null){
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
        date = getIntent().getStringExtra("DATE");

        if (date != null) {
            fireStore.collection("quizzes").whereEqualTo("title", date)
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        if (snapshots != null && !snapshots.isEmpty()) {
//                            Log.i("Date",snapshots.toObjects(Quiz.class).toString());
                            quizzes.addAll(snapshots.toObjects(Quiz.class));
                            questions = (HashMap<String, Question>) quizzes.get(0).questions;
                            bindViews();
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