package com.qaim.qaim.Classes;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Fragments.OrderDetailsAcceptableFragment;
import com.qaim.qaim.Models.AprovedList.DataItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderFragmentRecyceViewAdapter extends RecyclerView.Adapter<OrderFragmentRecyceViewAdapter.ViewHolder> {

    List<com.qaim.qaim.Models.AprovedList.DataItem> orderDetails ;
    AppCompatActivity activity ;

    public OrderFragmentRecyceViewAdapter(List<com.qaim.qaim.Models.AprovedList.DataItem> orderDetails) {
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
        com.qaim.qaim.Models.AprovedList.DataItem details = orderDetails.get(position);
        holder.bind(details);

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNameTv , orderDiscriptionTv , status ;
        ImageView imageView ;
        DataItem dataItem ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNameTv = itemView.findViewById(R.id.nameOforder);
            orderDiscriptionTv = itemView.findViewById(R.id.descriptionOforder);
            imageView = itemView.findViewById(R.id.image);
            status = itemView.findViewById(R.id.status);
            }
            public void bind(com.qaim.qaim.Models.AprovedList.DataItem dataItem){
                this.dataItem = dataItem ;
                orderNameTv.setText(dataItem.getTitle());
                orderDiscriptionTv.setText(dataItem.getDescription());
                status.setText(dataItem.getStatusTxt());
                GradientDrawable gb = (GradientDrawable) status.getBackground();
                gb.setColor(Color.parseColor(dataItem.getColor()));
                Picasso.get().load(dataItem.getImage()).fit().into(imageView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OrderDetailsAcceptableFragment fragment = OrderDetailsAcceptableFragment.newInstance(dataItem.getId() ,dataItem.getHasReport() , dataItem.getHasCompleted() );
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).addToBackStack(null).commit();
                    }
                });
            }
    }
}
