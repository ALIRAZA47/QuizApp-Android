package com.example.quizyy.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.quizyy.R;
import com.example.quizyy.models.Question;
import com.example.quizyy.models.Quiz;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    TextView txtScore;
    TextView txtText;
    Quiz quiz ;
    int quizScore = 0;
    HashMap<String, Question> questions = new HashMap<String, Question>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setupViews();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupViews() {
        String i = getIntent().getStringExtra("QMap");
//        Map<String, Question> quizDataStr = (HashMap<String, Question>) getIntent().getSerializableExtra("QMapi");
        quiz = new Gson().fromJson(i, Quiz.class);
        Log.i("RESULTDATA", "setupViews: data is --> "+quiz.toString());
        questions = quiz.questions;
        calculateScore();
        setAnswerView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void calculateScore() {
        txtScore = findViewById(R.id.txtScore);
        //Log.i("SCORE", "calculateScore: questions are --> " + questions.toString());
        questions.forEach((s, question) -> {
            if(question.userAnswer.equals(question.answer)){
                quizScore = quizScore + 10;
            }

        });
        //Log.i("SCORE", "calculateScore: score is --> " + quizScore);
        txtScore.setTypeface(Typeface.DEFAULT_BOLD);
        txtScore.setText("Your Score: "+quizScore);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAnswerView() {

//        StringBuilder builder = new StringBuilder();
//        StringBuilder builder1 = new StringBuilder();
//        txtText = findViewById(R.id.txtAnswer);
//
//        questions.forEach((s, question) -> {
//
//            builder.append("<font color='#009688'>Answer: "+question.answer+"</font><br/><br/>");
//            builder1.append("<font color'#FF000000'>Question: "+question.answer+"</font><br/><br/>");
//
//        });
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            txtText.setText( Html.fromHtml(builder.toString()+""+builder1.toString(), Html.FROM_HTML_MODE_COMPACT));
//        } else {
//            txtText.setText(Html.fromHtml(builder.toString()));
//        }


    }
}