package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.UserPaymentsResponse.DataItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserPaymentAdapter extends RecyclerView.Adapter<UserPaymentAdapter.ViewHolder> {

    List<DataItem> orderDetails ;
    AppCompatActivity activity ;

    public UserPaymentAdapter(List<DataItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_custom_adapter , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem details = orderDetails.get(position);
        Picasso.get().load(details.getImage()).fit().into(holder.imageView);
        holder.status.setText(details.getStatusTxt());
        holder.orderNameTv.setText(details.getTitle());
        holder.lblPaymentDescription.setText(details.getPaymentDescription());
        holder.orderDiscriptionTv.setText(details.getCost());
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderNameTv , orderDiscriptionTv , status , lblPaymentDescription ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNameTv = itemView.findViewById(R.id.nameOforder);
            orderDiscriptionTv = itemView.findViewById(R.id.descriptionOforder);
            imageView = itemView.findViewById(R.id.image);
            status = itemView.findViewById(R.id.status);
            lblPaymentDescription = itemView.findViewById(R.id.lblPaymentDescription);
        }

    }
}
