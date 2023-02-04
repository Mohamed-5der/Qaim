package com.Abdo.qaim.Classes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Models.RealstateDeleteUserResponse.RealstateDeleteUserResponse;
import com.Abdo.qaim.Models.RealsteatListUserResponse.DataItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyProjectRecycleViewAdapter extends RecyclerView.Adapter<CompanyProjectRecycleViewAdapter.ViewHolder> {

    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    MainFragment.AddRealstateFragmentListner listner ;
    List<DataItem> details ;
    int id ;
    AppCompatActivity activity ;
    public CompanyProjectRecycleViewAdapter(List<DataItem> details ,MainFragment.AddRealstateFragmentListner listner ) {
        this.details = details;
        this.listner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycleview_orders_adapter, null , false);
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
        DataItem projectDetails = details.get(position);

        if (!projectDetails.getStatus().isEmpty() || !projectDetails.getStatus().equals(""))
        {
            holder.editBtn.setVisibility(View.GONE);
            holder.endBtn.setVisibility(View.GONE);
            if (projectDetails.getStatus().equals("refused") || projectDetails.getStatus().equals("finished")){
                holder.status.setText(projectDetails.getStatus());
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setBackground(ContextCompat.getDrawable(activity , R.drawable.custom_text_view_refused_status));
            }else
            {
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText(projectDetails.getStatus());
                holder.status.setBackground(ContextCompat.getDrawable(activity , R.drawable.custom_text_view_status));
            }
        }

        try
        {
            Picasso.with(activity).load(projectDetails.getImage()).fit().error(activity.getDrawable(R.drawable.group9819)).into(holder.imageView);
        }
        catch(NullPointerException e){
            Log.e(TAG, e.toString());
        }
        holder.name.setText(projectDetails.getTitle());
        holder.description.setText(projectDetails.getDescription());
        holder.endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onEndBtnPressed(projectDetails.getId());
            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onEditBtnPressed(projectDetails.getId());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onCardPressed(projectDetails.getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name , description , status ;
        Button endBtn , editBtn ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOforder);
            description = itemView.findViewById(R.id.descriptionOforder);
            status = itemView.findViewById(R.id.status);
            endBtn = itemView.findViewById(R.id.endOrderBtn);
            editBtn = itemView.findViewById(R.id.editOrderBtn);
            imageView = itemView.findViewById(R.id.image);

        }
        public void onEndBtnPressed(int id){
            Call<RealstateDeleteUserResponse> realstateDeleteUserResponseCall = jsonApi.deleteRealstate("Bearer " +MainActivity.token , new ShowRealstateUserParams(id) );
            realstateDeleteUserResponseCall.enqueue(new Callback<RealstateDeleteUserResponse>() {
                @Override
                public void onResponse(Call<RealstateDeleteUserResponse> call, Response<RealstateDeleteUserResponse> response) {
                    RealstateDeleteUserResponse deleteUserResponse = response.body();
                    if (deleteUserResponse.getCode() == 200) {
                        Toast.makeText(itemView.getContext(), "success"  , Toast.LENGTH_SHORT).show();
                        activity.recreate();
                    }
                }

                @Override
                public void onFailure(Call<RealstateDeleteUserResponse> call, Throwable t) {
                    Toast.makeText(itemView.getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void onEditBtnPressed(int id){
            UpdateUserRealstateFragment updateUserRealstateFragment = UpdateUserRealstateFragment.newInstance(id);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout , updateUserRealstateFragment)
                    .commit();
        }
        public void onCardPressed(int id){
            ShowRealstateUserFragment fragment = ShowRealstateUserFragment.newInstance(id);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout , fragment)
                    .commit();
        }
    }


}
