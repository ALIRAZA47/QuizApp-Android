package com.example.quizyy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.activities.QuestionActivity;
import com.example.quizyy.adapters.QuizAdapter;
import com.example.quizyy.models.Course;
import com.example.quizyy.models.Quiz;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    TextView courseNameTextView;
    TextView courseTitleTextView;
    FirebaseFirestore fireStore;
    RecyclerView quizRecyclerView;
    QuizAdapter adapter;
    String courseName = new String();
    ArrayList<Quiz> quizzes = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        setUpViews();
    }

    private void setUpViews() {


        setupFireStore();
        setupRecyclerView();
        setupDatePicker();
    }



    private void setupFireStore() {
        fireStore = FirebaseFirestore.getInstance();
        Log.i("AliKhan", ": entered : setupFireStore: entered");
        courseNameTextView = findViewById(R.id.courseNamee);
        courseName = getIntent().getStringExtra("courseName");
        courseNameTextView.setText("Quizzes for " +courseName);
        fireStore.collection("courses").whereEqualTo("courseName", courseName)
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (snapshots != null && !snapshots.isEmpty()) {
                        Log.i("Courses", snapshots.toObjects(Course.class).toString());
//                        quizzes.clear();

                    } else {
                        Log.i("Courses", "No data retrieved--");
                    }

                    courses.addAll(snapshots.toObjects(Course.class));
                    Log.i("TAG-ALI", "setupFireStore: " + courses.toString());
                    for (int i = 1; i <= courses.get(0).quizzes.size(); i++) {
                        quizzes.add(courses.get(0).quizzes.get("quiz" + i));

                    }
                    Log.i("TAG-ALI", "setupFireStore: " + quizzes.toString());
//                    questions = quizzes.get("quiz1").questions;
//            quizzes.clear();
//            courses.addAll(snapshots.toObjects(Course.class));
//            quizzes = courses.get(0).quizzes;
//            questions = quizzes.get("quiz1").questions;
                    adapter.notifyDataSetChanged();


                });

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
                    Log.i("Date", "onPositiveButtonClick: " + dateStr);
                    intent.putExtra("DATE", dateStr);
                    startActivity(intent);
                });
                datePicker.addOnNegativeButtonClickListener(v1 -> {
                    Log.i("Date", "onNegativeButtonListener: " + datePicker.getHeaderText());
                });


            } //end of onclick
        });
    }

    private void setupRecyclerView() {
        quizRecyclerView = findViewById(R.id.quizRecyclerView);
        Log.i("TAG-ALI", "setupRecyclerView: " + quizzes.toString());
        adapter = new QuizAdapter(this, quizzes, courseName);
        quizRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        quizRecyclerView.setAdapter(adapter);
    }
}