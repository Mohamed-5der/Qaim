package com.Abdo.qaim.Classes;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Models.OrderListUserResponse.DataItem;
import com.Abdo.qaim.Models.RefusedOrderUserResponse.RefusedOrderUserResponse;
import com.Abdo.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferFragmentRecyceViewAdapter extends RecyclerView.Adapter<OfferFragmentRecyceViewAdapter.ViewHolder> {

    List<DataItem> offerDetails ;
AppCompatActivity activity ;
int cost ;
int id ;

    public OfferFragmentRecyceViewAdapter(List<DataItem> offerDetails) {
        this.offerDetails = offerDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycleview_offers_adapter , null , false);
         return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem details = offerDetails.get(position);
        holder.offerCompanyName.setText(details.getCompany().getName());
        cost =Integer.parseInt(details.getCost());
        id = details.getId();
        holder.offerPrice.setText(cost + " جنية ");
        holder.offerDescription.setText(details.getNotes());
        holder.acceptedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAcceptedBtnPressed(details.getId());
            }
        });
        holder.rejectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onRejectedPressed(details.getId());
            }
        });
        holder.showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.onShowBtnPressed();
                holder.onShowBtnPressed(details.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return offerDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView offerCompanyName , offerPrice , offerDescription ;
        Button showBtn , rejectedBtn , acceptedBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerCompanyName = itemView.findViewById(R.id.offerCompanyName);
            offerPrice = itemView.findViewById(R.id.offerPrice);
            offerDescription = itemView.findViewById(R.id.descriptionOfOffer);
            showBtn = itemView.findViewById(R.id.showBtn);
            rejectedBtn = itemView.findViewById(R.id.rejectOfferBtn);
            acceptedBtn = itemView.findViewById(R.id.acceptOfferBtn);
        }

        public void onShowBtnPressed(int id){
            OrderDetailsFragment orderDetailsFragment = OrderDetailsFragment.newInstance(id);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout , orderDetailsFragment)
                    .commit();

        }
        public void onRejectedPressed(int id){
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.canceled_bottom_sheet_dialog);
            Button cancel = dialog.findViewById(R.id.rejectBtn);
            Button rejected = dialog.findViewById(R.id.sendNotes);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });
            rejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    onRejectedBtnPressed(id);
                }
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
            dialog.getWindow().setGravity(Gravity.BOTTOM);

        }
        public void onAcceptedBtnPressed(int id){
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.accepted_bottom_sheet_dialog);
            Button cancel = dialog.findViewById(R.id.rejectBtn);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            Button confirm = dialog.findViewById(R.id.sendNotes);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    PaymentFragment paymentFragment = PaymentFragment.newInstance(cost , id);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout , paymentFragment)
                            .commit();
                }
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        public void onRejectedBtnPressed(int id){
            Call<RefusedOrderUserResponse> call = jsonApi.getRefusedOrders("Bearer " + MainActivity.token, new OrderListItemParams(id));
            call.enqueue(new Callback<RefusedOrderUserResponse>() {
                @Override
                public void onResponse(Call<RefusedOrderUserResponse> call, Response<RefusedOrderUserResponse> response) {
                    RefusedOrderUserResponse refusedOrderUserResponse = response.body();
                    if (response.code() == 200) {
                        Toast.makeText(activity, refusedOrderUserResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<RefusedOrderUserResponse> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
