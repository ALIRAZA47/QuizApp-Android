package com.example.quizyy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.R;
import com.example.quizyy.activities.QuestionActivity;
import com.example.quizyy.models.Quiz;
import com.example.quizyy.utils.ColorPicker;
import com.example.quizyy.utils.IconPicker;

import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    Context context;
    String courseName = new String();
    ArrayList<Quiz> quizzes = new ArrayList<>();

    public QuizAdapter() {

    }

    public QuizAdapter(Context context, ArrayList<Quiz> quizzes, String courseName) {
        this.quizzes = quizzes;
        this.context = context;
        this.courseName = courseName;


    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.quiz_item, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        String title = quizzes.get(position).title;

        holder.quizTitle.setText(title);
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(new ColorPicker().getColors()));
        holder.quizIcon.setImageResource(new IconPicker().getIcons());
        holder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra("DATE", quizzes.get(position).title);
                intent.putExtra("courseName", courseName);
                context.startActivity(intent);
                Toast.makeText(context, quizzes.get(position).title, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView quizCourseName;
        TextView courseQuizTitle;
        ImageView quizIcon;
        TextView quizTitle;
        CardView cardContainer;

        public QuizViewHolder(View itemView) {
            super(itemView);
            quizIcon = itemView.findViewById(R.id.quizIcon);
            quizTitle = itemView.findViewById(R.id.quizTitle);
            quizCourseName = itemView.findViewById(R.id.courseName);
            cardContainer = itemView.findViewById(R.id.cardContainer);
        }
    }

}
