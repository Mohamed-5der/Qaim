package com.qaim.qaim.Classes;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Fragments.MainFragment;
import com.qaim.qaim.Fragments.ShowRealstateUserFragment;
import com.qaim.qaim.Fragments.UpdateUserRealstateFragment;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateDeleteUserResponse.RealstateDeleteUserResponse;
import com.qaim.qaim.Models.RealsteatListUserResponse.DataItem;
import com.qaim.qaim.R;
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
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem projectDetails = details.get(position);
        holder.onBind(projectDetails);


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

        public void onBind(DataItem projectDetails){
            if (!projectDetails.getStatus().isEmpty() || !projectDetails.getStatus().equals(""))
            {
                editBtn.setVisibility(View.GONE);
                endBtn.setVisibility(View.GONE);
            }
            status.setVisibility(View.VISIBLE);status.setText(projectDetails.getStatusTxt());
            if (projectDetails.getColor() != null && !projectDetails.getColor().isEmpty() &&!projectDetails.getColor().equals("") ){
                GradientDrawable gb = (GradientDrawable) status.getBackground();
                gb.setColor(Color.parseColor(projectDetails.getColor()));            }
            Picasso.get().load(projectDetails.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(imageView);
            name.setText(projectDetails.getTitle());
            description.setText(projectDetails.getDescription());
            endBtn.setOnClickListener(view -> onEndBtnPressed(projectDetails.getId()));
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditBtnPressed(projectDetails.getId());
                }
            });
            itemView.setOnClickListener(view -> onCardPressed(projectDetails.getId()));
        }




        public void onEndBtnPressed(int id){
            MainActivity.dialog.show();
            Call<RealstateDeleteUserResponse> realstateDeleteUserResponseCall = jsonApi.deleteRealstate("Bearer " +MainActivity.token , new DeleteRealstateUserParams(id) );
            realstateDeleteUserResponseCall.enqueue(new Callback<RealstateDeleteUserResponse>() {
                @Override
                public void onResponse(Call<RealstateDeleteUserResponse> call, Response<RealstateDeleteUserResponse> response) {
                    MainActivity.dialog.dismiss();
                    RealstateDeleteUserResponse deleteUserResponse = response.body();
                    if (deleteUserResponse.getCode() == 200) {
                        MainActivity.alert.crateMsg(response.body().getMessage() , activity);//                        Toast.makeText(itemView.getContext(), deleteUserResponse.getMessage()  , Toast.LENGTH_SHORT).show();
                        activity.recreate();
                    }else {
                        MainActivity.alert.crateMsg(response.body().getMessage() , activity);//                        Toast.makeText(itemView.getContext(), deleteUserResponse.getMessage()  , Toast.LENGTH_SHORT).show();
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
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.frameLayout , updateUserRealstateFragment)
                    .commit();
        }
        public void onCardPressed(int id){
            ShowRealstateUserFragment fragment = ShowRealstateUserFragment.newInstance(id);
            activity.getSupportFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.frameLayout , fragment)
                    .commit();
        }
    }


}
