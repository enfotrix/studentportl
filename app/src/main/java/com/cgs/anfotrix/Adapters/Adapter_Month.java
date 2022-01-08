package com.cgs.anfotrix.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgs.anfotrix.Models.Model_Month;
import com.cgs.anfotrix.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_Month extends RecyclerView.Adapter<Adapter_Month.ViewHolder> {

    private ArrayList<Model_Month> monthArrayList;
    Context context;

    private int selectedItem;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClicks(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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
        Drawable drawable = context.getResources().getDrawable(R.drawable.month_selector);

        holder.tv_monthname.setTextColor(context.getResources().getColor(R.color.black_m));

        if (selectedItem == position) {
            holder.tv_monthname.setTextColor(context.getResources().getColor(R.color.black));
//            holder.lay_select.setBackground(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return monthArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_monthname;
        LinearLayout lay_select;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_monthname = itemView.findViewById(R.id.tv_monthname);
            lay_select = itemView.findViewById(R.id.lay_select);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClicks(position);


                            int previousItem = selectedItem;
                            selectedItem = position;

                            notifyItemChanged(previousItem);
                            notifyItemChanged(position);

                        }
                    }
                }
            });

        }
    }
}
