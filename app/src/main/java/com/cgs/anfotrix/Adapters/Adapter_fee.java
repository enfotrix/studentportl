package com.cgs.anfotrix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgs.anfotrix.Models.Model_Fee;
import com.cgs.anfotrix.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_fee extends RecyclerView.Adapter<Adapter_fee.ViewHolder> {
    Context context;
    List<Model_Fee> feeArrayList;

    public Adapter_fee(Context context, List<Model_Fee> feeArrayList) {
        this.context = context;
        this.feeArrayList = feeArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_fee, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final Model_Fee model_fee = feeArrayList.get(position);

        holder.txt_fee.setText(model_fee.getFee());
        holder.txt_month.setText(model_fee.getMonth());
        holder.txt_status.setText(model_fee.getStatus());
    }

    @Override
    public int getItemCount() {
        return feeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_status, txt_month, txt_fee;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txt_fee = itemView.findViewById(R.id.txt_fee);
            txt_month = itemView.findViewById(R.id.txt_month);
            txt_status = itemView.findViewById(R.id.txt_status);

        }
    }
}
