package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Fragments.RealStateDetailsFragment;
import com.Abdo.qaim.Models.MyRealstateCompanyList.DataItem;
import com.Abdo.qaim.Models.Networks.JsonApi;
import com.Abdo.qaim.R;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyMyListRealstateRecycleViewAdapter extends RecyclerView.Adapter<CompanyMyListRealstateRecycleViewAdapter.ViewHolder> {
    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    List<DataItem> details ;
    int id ;
    AppCompatActivity activity ;

    public CompanyMyListRealstateRecycleViewAdapter(List<DataItem> details ) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_of_realstate_adapter , null , false);
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
        holder.name.setText(projectDetails.getRealEstate().getTitle());
        holder.description.setText(projectDetails.getRealEstate().getDescription());
//        Picasso.with(activity).load(projectDetails).fit().into(holder.imageView);

        if (projectDetails.getStatus().isEmpty())
        {
            holder.status.setVisibility(View.GONE);
        }else {
            if(projectDetails.getStatus() == "accepted" ||projectDetails.getStatus() == "new" ){
                holder.status.setText(projectDetails.getStatusText());
            }else {
                holder.status.setText(projectDetails.getStatusText());
            }
        }
        holder.showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealStateDetailsFragment fragment = RealStateDetailsFragment.newInstance(projectDetails.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , description , status ;
        Button showBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOforder) ;
            description = itemView.findViewById(R.id.descriptionOforder) ;
            status = itemView.findViewById(R.id.status) ;
            showBtn = itemView.findViewById(R.id.editOrderBtn) ;
        }
    }

}
