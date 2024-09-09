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
import com.qaim.qaim.Models.UserCompaniesResponse.RowsItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyFragmentRecyceViewAdapter extends RecyclerView.Adapter<CompanyFragmentRecyceViewAdapter.ViewHolder> {

    List<RowsItem> orderDetails ;
    AppCompatActivity activity ;

    public CompanyFragmentRecyceViewAdapter(List<RowsItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_custom_adapter , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RowsItem details = orderDetails.get(position);
        holder.bind(details);

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNameTv , orderDiscriptionTv ;
        ImageView imageView ;
        RowsItem dataItem ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNameTv = itemView.findViewById(R.id.nameOforder);
            orderDiscriptionTv = itemView.findViewById(R.id.descriptionOforder);
            imageView = itemView.findViewById(R.id.image);
            }
            public void bind(RowsItem dataItem){
                this.dataItem = dataItem ;
                orderNameTv.setText(dataItem.getName());
                orderDiscriptionTv.setText(dataItem.getAbout());
                Picasso.get().load(dataItem.getImage()).fit().into(imageView);
            }
    }
}
