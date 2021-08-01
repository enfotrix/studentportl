package com.enfotrix.studentportal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Models.Model_Month;
import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Month extends RecyclerView.Adapter<Adapter_Month.ViewHolder> {

    private ArrayList<Model_Month> monthArrayList;
    Context context;

    private int selectedItem;

    public Adapter_Month(Context context, ArrayList<Model_Month> monthArrayList) {
        this.context = context;
        this.monthArrayList = monthArrayList;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_month, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter_Month.ViewHolder holder, int position) {
        final Model_Month model_month = monthArrayList.get(position);

        holder.tv_monthname.setText(model_month.getMonth_name());

        holder.tv_monthname.setTextColor(context.getResources().getColor(R.color.black_m));

        if (selectedItem == position) {
            holder.tv_monthname.setTextColor(context.getResources().getColor(R.color.blue_dark));
        }
    }

    @Override
    public int getItemCount() {
        return monthArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_monthname;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_monthname = itemView.findViewById(R.id.tv_monthname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();

                    int previousItem = selectedItem;
                    selectedItem = position;

                    notifyItemChanged(previousItem);
                    notifyItemChanged(position);

                }
            });

        }
    }
}
