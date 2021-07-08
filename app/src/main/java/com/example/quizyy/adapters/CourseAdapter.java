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

import com.example.quizyy.QuizActivity;
import com.example.quizyy.R;
import com.example.quizyy.models.Course;
import com.example.quizyy.utils.ColorPicker;
import com.example.quizyy.utils.IconPicker;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context context;
    ArrayList<Course> courses = new ArrayList<>();

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        String courseName = courses.get(position).courseName;
        holder.courseName.setText(courseName + "");
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(new ColorPicker().getColors()));
        holder.courseIcon.setImageResource(new IconPicker().getIcons());
        holder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizActivity.class);
                intent.putExtra("courseName", courses.get(position).courseName);
                context.startActivity(intent);
                Toast.makeText(context, courses.get(position).courseName, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView courseIcon;
        TextView courseName;
        CardView cardContainer;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseIcon = itemView.findViewById(R.id.courseIcon);
            courseName = itemView.findViewById(R.id.courseName);
            cardContainer = itemView.findViewById(R.id.cardContainerCourse);
        }
    }
}
