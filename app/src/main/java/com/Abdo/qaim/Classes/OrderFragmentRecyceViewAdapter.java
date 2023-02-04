package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Fragments.OrderDetailsAcceptableFragment;
import com.Abdo.qaim.Models.AprovedList.DataItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderFragmentRecyceViewAdapter extends RecyclerView.Adapter<OrderFragmentRecyceViewAdapter.ViewHolder> {

    List<com.Abdo.qaim.Models.AprovedList.DataItem> orderDetails ;
    AppCompatActivity activity ;

    public OrderFragmentRecyceViewAdapter(List<com.Abdo.qaim.Models.AprovedList.DataItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.aproved_list_custom_adapter , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.Abdo.qaim.Models.AprovedList.DataItem details = orderDetails.get(position);
        holder.bind(details);
        Picasso.with(activity).load(details.getImage()).fit().into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailsAcceptableFragment fragment = OrderDetailsAcceptableFragment.newInstance(details.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNameTv , orderDiscriptionTv ;
        ImageView imageView ;
        DataItem dataItem ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNameTv = itemView.findViewById(R.id.nameOforder);
            orderDiscriptionTv = itemView.findViewById(R.id.descriptionOforder);
            imageView = itemView.findViewById(R.id.image);
            }
            public void bind(com.Abdo.qaim.Models.AprovedList.DataItem dataItem){
                this.dataItem = dataItem ;
                orderNameTv.setText(dataItem.getTitle());
                orderDiscriptionTv.setText(dataItem.getDescription());
            }
    }
}
