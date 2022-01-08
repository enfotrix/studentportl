package com.cgs.anfotrix.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgs.anfotrix.Models.Model_Notifi;
import com.cgs.anfotrix.R;

import java.util.List;

public class Adapter_Notifi extends RecyclerView.Adapter<Adapter_Notifi.ViewHolder> {
    private List<Model_Notifi> model_Notifi;

    // RecyclerView recyclerView;
    public Adapter_Notifi(List<Model_Notifi> model_Notifi) {
        this.model_Notifi = model_Notifi;
    }

    @NonNull
    @Override
    public Adapter_Notifi.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_notifi, parent, false);
        Adapter_Notifi.ViewHolder viewHolder = new Adapter_Notifi.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Notifi.ViewHolder holder, int position) {
        final Model_Notifi current_Notifi = model_Notifi.get(position);
        holder.txt_date.setText(model_Notifi.get(position).getDate());
        holder.txt_name.setText(model_Notifi.get(position).getHeading());
        holder.txt_notifi.setText(model_Notifi.get(position).getData());

        //holder.layout_Notifi.setBackgroundColor(Color.TRANSPARENT);// setBackgroundColor(Color.parseColor("#9F000000"));

//        holder.layout_Withdraw_req.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               /* Intent intent = new Intent(view.getContext(), ActivityNotificationDetails.class);
//                intent.putExtra("id", current_Notifi.getNotificatioID().toString().trim());
//                view.getContext().startActivity(intent);*/
//                //Toast.makeText(view.getContext(),"click on item: "+current_Notifi.getAmount().toString().trim(), Toast.LENGTH_LONG).show();
//                //Toast.makeText(view.getContext(),"click on item: "+current_investor.get_name_Investor(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return model_Notifi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView txt_date, txt_name, txt_notifi;
        public LinearLayout layout_Notifi;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            this.txt_name = (TextView) itemView.findViewById(R.id.txt_header_notify);
            this.txt_notifi = (TextView) itemView.findViewById(R.id.txt_notifi);
            //layout_Notifi = (LinearLayout) itemView.findViewById(R.id.layout_Notify);
        }

    }
}