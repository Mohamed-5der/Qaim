package com.qaim.qaim.Classes;

import android.app.Dialog;
import android.content.Intent;
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

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Activities.MoyasrPaymenyActivity;
import com.qaim.qaim.Fragments.OrderDetailsFragment;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.OrderListUserResponse.DataItem;
import com.qaim.qaim.Models.RefusedOrderUserResponse.RefusedOrderUserResponse;
import com.qaim.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfferFragmentRecyceViewAdapter extends RecyclerView.Adapter<OfferFragmentRecyceViewAdapter.ViewHolder> {

    List<DataItem> offerDetails ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
//    String pound ;
    String companyName ;
AppCompatActivity activity ;
String cost ;
int id ;

    public OfferFragmentRecyceViewAdapter(List<DataItem> offerDetails) {
        this.offerDetails = offerDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycleview_offers_adapter , null , false);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        activity = (AppCompatActivity) parent.getContext();
//        pound = parent.getContext().getResources().getString(R.string.pound);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem details = offerDetails.get(position);

        cost =details.getCost();
        id = details.getId();
        holder.onBind(details);



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

        public void onBind(DataItem details){
            companyName = details.getCompany().getName() ;
            offerCompanyName.setText(companyName);
            offerPrice.setText( activity.getString(R.string.cost_)+cost);
            offerDescription.setText(details.getCompany().getAbout());
            acceptedBtn.setOnClickListener(view -> onAcceptedBtnPressed(details.getId()));
            rejectedBtn.setOnClickListener(view -> onRejectedPressed(details.getId()));
            showBtn.setOnClickListener(view -> onShowBtnPressed(details.getId()));
        }



        public void onShowBtnPressed(int id){
            OrderDetailsFragment orderDetailsFragment = OrderDetailsFragment.newInstance(id);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout , orderDetailsFragment).addToBackStack(null)
                    .commit();

        }
        public void onRejectedPressed(int id){
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.canceled_bottom_sheet_dialog);
            Button cancel = dialog.findViewById(R.id.rejectBtn);
            Button rejected = dialog.findViewById(R.id.sendNotes);
            cancel.setOnClickListener(view -> dialog.dismiss());
            rejected.setOnClickListener(view -> {
                dialog.dismiss();
                onRejectedBtnPressed(id);
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
            cancel.setOnClickListener(view -> dialog.dismiss());
            Button confirm = dialog.findViewById(R.id.sendNotes);
            confirm.setOnClickListener(view -> {
                dialog.dismiss();
                Intent i = new Intent(activity , MoyasrPaymenyActivity.class);
                i.putExtra("cost" , cost);
                i.putExtra("id" , id);
                i.putExtra("company" , companyName);
                activity.startActivity(i);
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
        public void onRejectedBtnPressed(int id){
            MainActivity.dialog.show();
            Call<RefusedOrderUserResponse> call = jsonApi.getRefusedOrders("Bearer " + MainActivity.token, new OrderListItemParams(id));
            call.enqueue(new Callback<RefusedOrderUserResponse>() {
                @Override
                public void onResponse(Call<RefusedOrderUserResponse> call, Response<RefusedOrderUserResponse> response) {
                    MainActivity.dialog.dismiss();
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
