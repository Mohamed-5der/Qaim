package com.Abdo.qaim.Classes;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Models.DataItem;
import com.Abdo.qaim.R;

import java.util.List;

public class CompanyPaymentRecycleViewAdapter extends RecyclerView.Adapter<CompanyPaymentRecycleViewAdapter.ViewHolder> {

    List<DataItem> orderDetails ;
    AppCompatActivity activity ;

    public CompanyPaymentRecycleViewAdapter( List<DataItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_company_payments_adapter,null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DataItem details = orderDetails.get(position);
        holder.name.setText(details.getRealEstate().getTitle());
        holder.price.setText(details.getRealEstate().getCost());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name , price ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }
}
