package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Fragments.MainFragment;
import com.Abdo.qaim.Fragments.SendRateDisplayFragment;
import com.Abdo.qaim.Models.CompanyRealstate.DataItem;
import com.Abdo.qaim.Models.Networks.JsonApi;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyListRealstateRecycleViewAdapter extends RecyclerView.Adapter<CompanyListRealstateRecycleViewAdapter.ViewHolder> {

    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    MainFragment.AddRealstateFragmentListner listner ;
    List<DataItem> details ;
    int id ;
    AppCompatActivity activity ;

    public CompanyListRealstateRecycleViewAdapter(List<com.Abdo.qaim.Models.CompanyRealstate.DataItem> details ) {
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
        com.Abdo.qaim.Models.CompanyRealstate.DataItem projectDetails = details.get(position);
        id = projectDetails.getId();
        holder.name.setText(projectDetails.getTitle());
        holder.description.setText(projectDetails.getDescription());
        Picasso.with(activity).load(projectDetails.getImage()).fit().into(holder.imageView);

        if (!projectDetails.getStatus().isEmpty() || !projectDetails.getStatus().equals(""))
        {
            if (projectDetails.getStatus().equals("refused") || projectDetails.getStatus().equals("finished")){
                holder.status.setText(projectDetails.getStatus());
                holder.status.setBackground(ContextCompat.getDrawable(activity , R.drawable.custom_text_view_refused_status));
            }else
            {
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText(projectDetails.getStatus());
                holder.status.setBackground(ContextCompat.getDrawable(activity , R.drawable.custom_text_view_status));
            }
        }

        holder.sendRatingOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onSendRateOfferBtnPressed(projectDetails.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , description  , status;
        Button sendRatingOffer ;
        ImageView imageView ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOforder) ;
            description = itemView.findViewById(R.id.descriptionOforder) ;
            status = itemView.findViewById(R.id.status) ;
            imageView = itemView.findViewById(R.id.image) ;
            sendRatingOffer = itemView.findViewById(R.id.editOrderBtn) ;
        }

        public void onSendRateOfferBtnPressed(int id){
            SendRateDisplayFragment fragment = SendRateDisplayFragment.newInstance(id);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
        }


    }
}
