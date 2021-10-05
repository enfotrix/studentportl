package com.enfotrix.cgs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OnBoardAdapter extends RecyclerView.Adapter<OnBoardAdapter.ViewHolder> {
    Context context;
    ArrayList<OnBoardModel> boardModelArrayList;

    public OnBoardAdapter(Context context, ArrayList<OnBoardModel> boardModelArrayList) {
        this.context = context;
        this.boardModelArrayList = boardModelArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.slides_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        final OnBoardModel onBoardModel = boardModelArrayList.get(position);

        holder.txt_designation.setText(onBoardModel.getTitle());
        holder.txt_name.setText(onBoardModel.getName());
        holder.txt_msg.setText(onBoardModel.getMsg());

        Glide.with(holder.img)
                .load(onBoardModel.getImg())
                .fitCenter().into(holder.img);

    }

    @Override
    public int getItemCount() {
        return boardModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_designation, txt_name, txt_msg;
        CircleImageView img;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            txt_msg = itemView.findViewById(R.id.txt_msg);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_designation = itemView.findViewById(R.id.txt_designation);

            img = itemView.findViewById(R.id.img);

        }
    }
}
