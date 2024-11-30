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
import com.qaim.qaim.Fragments.ShowReviewerProfileFragment;
import com.qaim.qaim.Models.AssignTeam.AssingTeamResponse;
import com.qaim.qaim.Models.MyListTeamReports.Reviewer;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewerListAdapter extends RecyclerView.Adapter<ReviewerListAdapter.ViewHolder> {

    List<Reviewer> reviewers ;
    AppCompatActivity activity ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    int id ;

    public ReviewerListAdapter(List<Reviewer> reviewers , int id) {
        this.reviewers = reviewers;
        this.id = id ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_painter_list , null , false);
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
        Reviewer reviewer = reviewers.get(position);
        holder.name.setText(reviewer.getName());
        holder.description.setText(reviewer.getNotes());
//        holder.cost.setText(painter.getcost);
        Picasso.get().load(reviewer.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(holder.imageView);

        holder.choose.setOnClickListener(view -> {

            Call<AssingTeamResponse> call = jsonApi.assignTeam("Bearer " + CompanyActivity.token ,new AssignTeamParamsRev(
                    id ,reviewer.getId()));
            call.enqueue(new Callback<AssingTeamResponse>() {
                @Override
                public void onResponse(Call<AssingTeamResponse> call, Response<AssingTeamResponse> response) {
                    AssingTeamResponse assignTeamResponse = response.body();
                    if (assignTeamResponse.getCode() == 200) {
                        CompanyActivity.alert.crateMsg(response.body().getMessage() , activity);
//                            Toast.makeText(activity ,assignTeamResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(id);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , chooseTeamFragment).commit();

                    }else {
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
            ShowReviewerProfileFragment showReviewerProfileFragment = ShowReviewerProfileFragment.newInstance(reviewer.getName() , reviewer.getEmail() , reviewer.getImage() ,id);
            activity.getSupportFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frameLayout ,showReviewerProfileFragment)
                            .commit();
        });
    }

    @Override
    public int getItemCount() {
        return reviewers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
