package com.enfotrix.cgs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.cgs.Models.Model_TimeTable;
import com.enfotrix.cgs.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_TimeTable extends RecyclerView.Adapter<Adapter_TimeTable.ViewHolder> {
    Context context;
    ArrayList<Model_TimeTable> timeTableArrayList;

    public Adapter_TimeTable(Context context, ArrayList<Model_TimeTable> timeTableArrayList) {
        this.context = context;
        this.timeTableArrayList = timeTableArrayList;
    }

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
        final Model_TimeTable model_timeTable = timeTableArrayList.get(position);

        holder.tv_slotNumber.setText(model_timeTable.getTv_slotNumber());
        holder.tv_subName.setText(model_timeTable.getTv_subName());
        holder.tv_startTime.setText(model_timeTable.getTv_startTime());

    }

    @Override
    public int getItemCount() {
        return timeTableArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_slotNumber, tv_subName, tv_teacherName, tv_startTime, tv_endTime;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_slotNumber = itemView.findViewById(R.id.tv_slotNumber);
            tv_subName = itemView.findViewById(R.id.tv_subName);
//            tv_teacherName = itemView.findViewById(R.id.tv_teacherName);
            tv_startTime = itemView.findViewById(R.id.tv_startTime);
//            tv_endTime = itemView.findViewById(R.id.tv_endTime);

        }
    }
}
