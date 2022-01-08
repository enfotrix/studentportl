package com.cgs.anfotrix.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cgs.anfotrix.Models.Model_Attendance;
import com.cgs.anfotrix.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Adapter_attendance extends RecyclerView.Adapter<Adapter_attendance.ViewHolder> {
    Context context;
    ArrayList<Model_Attendance> attendanceArrayList;

    public Adapter_attendance(Context context, ArrayList<Model_Attendance> attendanceArrayList) {

        this.context = context;
        this.attendanceArrayList = attendanceArrayList;

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_attendance, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_attendance.ViewHolder holder, int position) {

        final Model_Attendance model_attendance = attendanceArrayList.get(position);

        holder.tv_date.setText(model_attendance.getDate());
        holder.tv_status.setText(model_attendance.getStatus());
    }

    @Override
    public int getItemCount() {
        return attendanceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date, tv_status;
        CardView cv_result;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_status = itemView.findViewById(R.id.tv_status);

            cv_result = itemView.findViewById(R.id.cv_result);

//            cv_result.setCardBackgroundColor(getcolore());

        }
    }

    private static int getcolore() {

        int[] color = new int[]{Color.parseColor("#ffeb99"), Color.parseColor("#b3ffb3"), Color.parseColor("#62C2CC"), Color.parseColor("#9568D3"), Color.parseColor("#6675CF"), Color.parseColor("#D16596"), Color.parseColor("#CF6565")};
        int lenghth = color.length;

        Random random = new Random();
        int rand = random.nextInt(lenghth);
        return color[rand];
        // return Color.argb(255, random.nextInt(256), random.nextInt(256),     random.nextInt(256));

    }
}
