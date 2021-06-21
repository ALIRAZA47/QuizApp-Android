package com.example.quizyy.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizyy.R;
import com.example.quizyy.models.Question;

import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    Context context;
    public Question question = new Question();
    public ArrayList<String> options = new ArrayList<>() ;



    public OptionAdapter(Context context, Question question) {
        this.context = context;
        this.question = question;
        this.options.add(question.option1);
        this.options.add(question.option2);
        this.options.add(question.option3);
        this.options.add(question.option4);
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.option_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        String selected = options.get(position);
        //Log.i("TAG", "onBindViewHolder: position----> " +position+ "  option----> "+options.get(0));
        holder.optionText.setText(selected);
        holder.optionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("btnTAG", "onClick: "+selected);
                question.userAnswer = selected;
                notifyDataSetChanged();
            }
        });
        if(question.userAnswer == options.get(position)){
            holder.optionText.setBackgroundResource(R.drawable.option_item_selected_bg);
        }
        else{
            holder.optionText.setBackgroundResource(R.drawable.option_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionText;
        public OptionViewHolder(View itemView) {
            super(itemView);
            optionText = itemView.findViewById(R.id.quiz_option);
        }
    }
}
