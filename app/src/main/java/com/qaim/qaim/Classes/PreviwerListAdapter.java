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

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Fragments.CompanyChooseTeamFragment;
import com.qaim.qaim.Fragments.ShowPreviewerProfileFragment;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.AssignTeam.AssingTeamResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.TeamPreviewerList.DataItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviwerListAdapter extends RecyclerView.Adapter<PreviwerListAdapter.ViewHolder> {

    List<DataItem> previewers ;
    AppCompatActivity activity ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    int order_id ;
    int realStateID ;
    public PreviwerListAdapter(List<DataItem> previewers , int order_id ,int realStateID ) {
        this.previewers = previewers;
        this.order_id = order_id;
        this.realStateID = realStateID ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_previewer_list , null , false);
        activity = (AppCompatActivity) parent.getContext();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem previewer = previewers.get(position);
        holder.name.setText(previewer.getName());
        holder.description.setText(previewer.getNotes());
        holder.cost.setText(previewer.getCost());
        previewer.getExperience() ;
        previewer.getYears();
        previewer.getFieldTxt();


        Picasso.get().load(previewer.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(holder.imageView);

        holder.choose.setOnClickListener(view -> {
            CompanyActivity.dialog.show();
            Call<AssingTeamResponse> call = jsonApi.assignTeam(LocaleHelper.getLanguage(activity), "Bearer " + CompanyActivity.token ,new AssignTeamParamsPrev(
                    order_id ,previewer.getId()));
            call.enqueue(new Callback<AssingTeamResponse>() {
                @Override
                public void onResponse(Call<AssingTeamResponse> call, Response<AssingTeamResponse> response) {
                    CompanyActivity.dialog.dismiss();
                    AssingTeamResponse assignTeamResponse = response.body();
                    if (assignTeamResponse.getCode() == 200) {
                        CompanyActivity.alert.crateMsg(response.body().getMessage() ,activity);
//                            Toast.makeText(activity ,assignTeamResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(order_id);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , chooseTeamFragment).commit();
                    }
                    else {
                        CompanyActivity.alert.crateMsg(response.body().getMessage() , activity);
//                            Toast.makeText(activity ,assignTeamResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<AssingTeamResponse> call, Throwable t) {
                    Toast.makeText(activity ,t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });

        });
        holder.showProfile.setOnClickListener(view -> {
            // region
            ShowPreviewerProfileFragment showPreviewerProfileFragment =ShowPreviewerProfileFragment.newInstance(previewer.getName() ,previewer.getCost() , previewer.getImage() , order_id ,previewer.getArea() , previewer.getYears() , previewer.getFieldTxt() , previewer.getExtraAbout() , realStateID, previewer.getRate());
            activity.getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frameLayout ,showPreviewerProfileFragment)
                            .commit();
        });
    }

    @Override
    public int getItemCount() {
        return previewers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        Button choose , showProfile ;
        TextView name , description , cost ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            choose = itemView.findViewById(R.id.choose);
            showProfile = itemView.findViewById(R.id.showProfile);
            name = itemView.findViewById(R.id.tittle);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            imageView = itemView.findViewById(R.id.image);

        }
    }
}
