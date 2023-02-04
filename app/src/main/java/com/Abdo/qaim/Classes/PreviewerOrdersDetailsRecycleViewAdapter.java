package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Activities.PreviewerActivity;
import com.Abdo.qaim.Fragments.PreviewerOrdersFragment;
import com.Abdo.qaim.Models.GetAcceptedOrderPrev.AcceptedOrderPrevResponse;
import com.Abdo.qaim.Models.GetOrdersPreviewer.DataItem;
import com.Abdo.qaim.Models.GetRefusedOrders.RefusedOrderPrevResponse;
import com.Abdo.qaim.Models.Networks.JsonApi;
import com.Abdo.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerOrdersDetailsRecycleViewAdapter extends RecyclerView.Adapter<PreviewerOrdersDetailsRecycleViewAdapter.ViewHolder> {

    List<DataItem> orderDetails ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    AppCompatActivity activity ;


    public PreviewerOrdersDetailsRecycleViewAdapter(List<DataItem> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_previewer_orders_adapter , null , false);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        activity = (AppCompatActivity) parent.getContext();

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem details = orderDetails.get(position);
        holder.name.setText(details.getCompany().getName());
        holder.description.setText(details.getInfo().getNotes());
        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.callAcceptedOrderAPI(details.getId());
            }
        });
        holder.rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.callRefusedOrderAPI(details.getId());
            }
        });
        holder.sendNotesBtn.setOnClickListener(new View.OnClickListener() {
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
        TextView name , description ;
        Button acceptBtn , rejectBtn , sendNotesBtn ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.companyName);
            description= itemView.findViewById(R.id.Comdescription);
            acceptBtn= itemView.findViewById(R.id.accept);
            rejectBtn= itemView.findViewById(R.id.rejectBtn);
            sendNotesBtn= itemView.findViewById(R.id.sendNotes);
        }
       public void fragmentRecreate(){
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,PreviewerOrdersFragment.newInstance())
                    .commit();
       }
        public void callAcceptedOrderAPI(int id){
            Call<AcceptedOrderPrevResponse> call = jsonApi.accedptedOrdersPreviewer("Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
            call.enqueue(new Callback<AcceptedOrderPrevResponse>() {
                @Override
                public void onResponse(Call<AcceptedOrderPrevResponse> call, Response<AcceptedOrderPrevResponse> response) {
                    AcceptedOrderPrevResponse prevResponse = response.body();
                    if (prevResponse.getCode() == 200){
                        Toast.makeText(activity.getBaseContext() , "Order Is Accepted" , Toast.LENGTH_SHORT).show();
                        fragmentRecreate();
                    }
                }

                @Override
                public void onFailure(Call<AcceptedOrderPrevResponse> call, Throwable t) {
                    Toast.makeText(activity , t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void callRefusedOrderAPI(int id){
            Call<RefusedOrderPrevResponse> call = jsonApi.refusedOrdersPreviewer("Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
            call.enqueue(new Callback<RefusedOrderPrevResponse>() {
                @Override
                public void onResponse(Call<RefusedOrderPrevResponse> call, Response<RefusedOrderPrevResponse> response) {
                    RefusedOrderPrevResponse prevResponse = response.body();
                    if (prevResponse.getCode() == 200){
                        Toast.makeText(activity.getBaseContext() , "Order Is Refused" , Toast.LENGTH_SHORT).show();
                        fragmentRecreate();
                    }
                }

                @Override
                public void onFailure(Call<RefusedOrderPrevResponse> call, Throwable t) {
                    Toast.makeText(activity , t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
}
