package com.qaim.qaim.Classes;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.GetBalance.RowsItem;
import com.qaim.qaim.R;

import java.util.List;

public class CompanyTransactionRecycleViewAdapter extends RecyclerView.Adapter<CompanyTransactionRecycleViewAdapter.ViewHolder> {

    List<RowsItem> orderDetails ;
    AppCompatActivity activity ;

    public CompanyTransactionRecycleViewAdapter(List<RowsItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_custom_adapter,null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RowsItem details = orderDetails.get(position);
        holder.orderNameTv.setText(details.getRealEstate().getTitle());
        holder.lblPaymentDescription.setText(details.getDescription());
        holder.orderDiscriptionTv.setText(details.getCost());
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
