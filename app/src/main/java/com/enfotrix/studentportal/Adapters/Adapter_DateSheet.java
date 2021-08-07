package com.enfotrix.studentportal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

public class Adapter_DateSheet extends RecyclerView.Adapter<Adapter_DateSheet.ViewHolders> {

    @NonNull
    @NotNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_datesheet, parent, false);
        ViewHolders viewHolder = new ViewHolders(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_DateSheet.ViewHolders holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView tv_day, tv_month, tv_subName, tv_startTime, tv_endTime;

        public ViewHolders(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_day = itemView.findViewById(R.id.tv_date);
            tv_month = itemView.findViewById(R.id.tv_month);
            tv_subName = itemView.findViewById(R.id.tv_subName);
            tv_startTime = itemView.findViewById(R.id.tv_startTime);
            tv_endTime = itemView.findViewById(R.id.tv_endTime);
        }
    }
}
