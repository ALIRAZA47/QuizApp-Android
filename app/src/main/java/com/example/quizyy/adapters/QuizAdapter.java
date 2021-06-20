package com.example.quizyy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.R;
import com.example.quizyy.models.Quiz;
import com.example.quizyy.utils.ColorPicker;
import com.example.quizyy.utils.IconPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    Context context;
    ArrayList<Quiz> quizzes = new ArrayList<>();

    public QuizAdapter() {

    }

    public QuizAdapter(Context context, ArrayList<Quiz> quizzes) {
        this.quizzes = quizzes;
        this.context = context;

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
                Toast.makeText(context,  quizzes.get(position).title, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class QuizViewHolder extends RecyclerView.ViewHolder {
        ImageView quizIcon;
        TextView quizTitle;
        CardView cardContainer;

        public QuizViewHolder(View itemView) {
            super(itemView);
            quizIcon = itemView.findViewById(R.id.quizIcon);
            quizTitle = itemView.findViewById(R.id.quizTitle);
            cardContainer = itemView.findViewById(R.id.cardContainer);
        }
    }

}
