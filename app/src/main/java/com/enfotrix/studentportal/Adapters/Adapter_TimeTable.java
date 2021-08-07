package com.enfotrix.studentportal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

public class Adapter_TimeTable extends RecyclerView.Adapter<Adapter_TimeTable.ViewHolder> {

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_timetable, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_TimeTable.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_slotNumber, tv_subName, tv_teacherName, tv_startTime, tv_endTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_slotNumber = itemView.findViewById(R.id.tv_slotNumber);
            tv_subName = itemView.findViewById(R.id.tv_subName);
            tv_teacherName = itemView.findViewById(R.id.tv_teacherName);
            tv_startTime = itemView.findViewById(R.id.tv_startTime);
            tv_endTime = itemView.findViewById(R.id.tv_endTime);

        }
    }
}
