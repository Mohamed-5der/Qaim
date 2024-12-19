package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Fragments.PrevRealstateDetailsFragment;
import com.qaim.qaim.Fragments.PreviewerOrdersFragment;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.GetAcceptedOrderPrev.AcceptedOrderPrevResponse;
import com.qaim.qaim.Models.GetOrdersPreviewer.DataItem;
import com.qaim.qaim.Models.GetRefusedOrders.RefusedOrderPrevResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

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
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        activity = (AppCompatActivity) parent.getContext();

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem details = orderDetails.get(position);
        holder.onBind(details);

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , description ;
        Button acceptBtn , rejectBtn , sendNotesBtn ;
        ImageView imageView ;
        DataItem details ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.companyName);
            description= itemView.findViewById(R.id.Comdescription);
            acceptBtn= itemView.findViewById(R.id.accept);
            rejectBtn= itemView.findViewById(R.id.rejectBtn);
            sendNotesBtn= itemView.findViewById(R.id.sendNotes);
            imageView = itemView.findViewById(R.id.companyView);
        }


        public void onBind(DataItem details){
            name.setText(details.getCompany().getName());
            description.setText(details.getInfo().getNotes());
            Picasso.get().load(details.getCompany().getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(imageView);
            acceptBtn.setOnClickListener(view -> callAcceptedOrderAPI(details.getId()));
            rejectBtn.setOnClickListener(view -> callRefusedOrderAPI(details.getId()));
            sendNotesBtn.setOnClickListener(view -> {
                PrevRealstateDetailsFragment fragment = PrevRealstateDetailsFragment.newInstance(details.getId());
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.frameLayout , fragment)
                        .commit();
            });
        }

       public void fragmentRecreate(){
            PreviewerActivity.dialog.show();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,PreviewerOrdersFragment.newInstance())
                    .commit();
            PreviewerActivity.dialog.dismiss();
       }
        public void callAcceptedOrderAPI(int id){
            PreviewerActivity.dialog.show();
            Call<AcceptedOrderPrevResponse> call = jsonApi.accedptedOrdersPreviewer(LocaleHelper.getLanguage(activity), "Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
            call.enqueue(new Callback<AcceptedOrderPrevResponse>() {
                @Override
                public void onResponse(Call<AcceptedOrderPrevResponse> call, Response<AcceptedOrderPrevResponse> response) {
                    PreviewerActivity.dialog.dismiss();
                    AcceptedOrderPrevResponse prevResponse = response.body();
                    if (prevResponse.getCode() == 200){
                        PreviewerActivity.alert.creatDialog(response.body().getMessage() , activity);
//                        Toast.makeText(activity.getBaseContext() , prevResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        fragmentRecreate();
                    }else {
                        PreviewerActivity.alert.creatDialog(response.body().getMessage() , activity);
                    }
                }

                @Override
                public void onFailure(Call<AcceptedOrderPrevResponse> call, Throwable t) {

                    Toast.makeText(activity , t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void callRefusedOrderAPI(int id){
            PreviewerActivity.dialog.show();
            Call<RefusedOrderPrevResponse> call = jsonApi.refusedOrdersPreviewer(LocaleHelper.getLanguage(activity), "Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
            call.enqueue(new Callback<RefusedOrderPrevResponse>() {
                @Override
                public void onResponse(Call<RefusedOrderPrevResponse> call, Response<RefusedOrderPrevResponse> response) {
                    PreviewerActivity.dialog.dismiss();
                    RefusedOrderPrevResponse prevResponse = response.body();
                    if (prevResponse.getCode() == 200){
                        Toast.makeText(activity.getBaseContext() ,prevResponse.getMessage() , Toast.LENGTH_SHORT).show();
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
