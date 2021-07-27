package com.enfotrix.studentportal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enfotrix.studentportal.Activities.Activity_full_image_container;
import com.enfotrix.studentportal.Models.Model_Image;
import com.enfotrix.studentportal.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Adapter_Image extends RecyclerView.Adapter<Adapter_Image.ImageViewHolder>{


    private List<Model_Image> list;
    private Context context;
    public Adapter_Image(Context context, List<Model_Image> list) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_custom_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImageViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(list.get(position).getPath())
                .fitCenter().into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , Activity_full_image_container.class);
                intent.putExtra("path",list.get(position).getPath());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
