package com.enfotrix.studentportal.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Models.Model_Feedback;
import com.enfotrix.studentportal.R;

import java.util.List;

public class Adapter_Feedback extends RecyclerView.Adapter<Adapter_Feedback.ViewHolder> {
    private List<Model_Feedback> model_Feedback;


    // RecyclerView recyclerView;
    public Adapter_Feedback(List<Model_Feedback> model_Feedback) {
        this.model_Feedback = model_Feedback;
    }
    @NonNull
    @Override
    public Adapter_Feedback.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_announce, parent, false);
        Adapter_Feedback.ViewHolder viewHolder = new Adapter_Feedback.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Feedback.ViewHolder holder, int position) {
        final Model_Feedback current_Feedback = model_Feedback.get(position);
        holder.txt_date.setText(model_Feedback.get(position).getDate());
        holder.txt_name.setText(model_Feedback.get(position).getHeading());
        holder.txt_notifi.setText(model_Feedback.get(position).getData());

        //holder.layout_Feedback.setBackgroundColor(Color.TRANSPARENT);// setBackgroundColor(Color.parseColor("#9F000000"));

//        holder.layout_Withdraw_req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               /* Intent intent = new Intent(view.getContext(), ActivityNotificationDetails.class);
//                intent.putExtra("id", current_Feedback.getNotificatioID().toString().trim());
//                view.getContext().startActivity(intent);*/
//                //Toast.makeText(view.getContext(),"click on item: "+current_Feedback.getAmount().toString().trim(), Toast.LENGTH_LONG).show();
//                //Toast.makeText(view.getContext(),"click on item: "+current_investor.get_name_Investor(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return model_Feedback.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public TextView txt_date,txt_name,txt_notifi;
        public LinearLayout layout_Feedback;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            this.txt_name = (TextView) itemView.findViewById(R.id.txt_header_notify);
            this.txt_notifi = (TextView) itemView.findViewById(R.id.txt_notifi);
            //layout_Feedback = (LinearLayout)itemView.findViewById(R.id.layout_Notify);
        }

    }
}


