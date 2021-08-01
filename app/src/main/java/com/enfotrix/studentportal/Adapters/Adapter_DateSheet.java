package com.enfotrix.studentportal.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        public ViewHolders(@NonNull @NotNull View itemView) {
            super(itemView);

        }
    }
}
