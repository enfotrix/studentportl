package com.enfotrix.studentportal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Models.Model_DateSheet;
import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_DateSheet extends RecyclerView.Adapter<Adapter_DateSheet.ViewHolders> {
    Context context;
    ArrayList<Model_DateSheet> dateSheetArrayList;

    public Adapter_DateSheet(Context context, ArrayList<Model_DateSheet> dateSheetArrayList) {
        this.context = context;
        this.dateSheetArrayList = dateSheetArrayList;
    }

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
        final Model_DateSheet model_dateSheet = dateSheetArrayList.get(position);

        holder.tv_subName.setText(model_dateSheet.getSubName());
//        holder.tv_day.setText(model_dateSheet.getDay());
//        holder.tv_month.setText(model_dateSheet.getMonth());
        holder.tv_classSection.setText(model_dateSheet.getClasssectin());
        holder.tv_classsession.setText(model_dateSheet.getClasssession());
    }

    @Override
    public int getItemCount() {
        return dateSheetArrayList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView tv_day, tv_month, tv_subName, tv_classSection, tv_classsession;

        public ViewHolders(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_day = itemView.findViewById(R.id.tv_date);
            tv_month = itemView.findViewById(R.id.tv_month);
            tv_subName = itemView.findViewById(R.id.tv_subName);
            tv_classsession = itemView.findViewById(R.id.tv_classsession);
            tv_classSection = itemView.findViewById(R.id.tv_classSection);

        }
    }
}
