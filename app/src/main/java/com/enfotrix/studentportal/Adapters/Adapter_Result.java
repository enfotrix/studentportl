package com.enfotrix.studentportal.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Models.Model_Result;
import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Adapter_Result extends RecyclerView.Adapter<Adapter_Result.ViewHolder> {
    ArrayList<Model_Result> resultArrayList;
    Context context;

    public Adapter_Result(Context context, ArrayList<Model_Result> resultArrayList) {
        this.context = context;
        this.resultArrayList = resultArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_Result.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Result.ViewHolder holder, int position) {
        final Model_Result model_result = resultArrayList.get(position);

        holder.tv_sub.setText(model_result.getSub_name());
        holder.tv_marks.setText(model_result.getSub_marks());
        holder.tv_grade.setText(model_result.getSub_grade());
    }

    @Override
    public int getItemCount() {
        return resultArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sub, tv_marks, tv_grade;
        CardView cv_result;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_sub = itemView.findViewById(R.id.tv_sub);
            tv_marks = itemView.findViewById(R.id.tv_marks);
            tv_grade = itemView.findViewById(R.id.tv_grade);
            cv_result = itemView.findViewById(R.id.cv_result);

            cv_result.setCardBackgroundColor(getcolore());


        }
    }

    private static int getcolore() {

        int[] color = new int[]{Color.parseColor("#ffeb99"), Color.parseColor("#b3ffb3")};
        int lenghth = color.length;

        Random random = new Random();
        int rand = random.nextInt(lenghth);
        return color[rand];
        // return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }
}
