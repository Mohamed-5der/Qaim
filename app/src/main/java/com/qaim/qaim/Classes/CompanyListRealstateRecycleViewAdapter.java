package com.qaim.qaim.Classes;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Fragments.MainFragment;
import com.qaim.qaim.Fragments.SendRateDisplayFragment;
import com.qaim.qaim.Models.CompanyRealstate.DataItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
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

    public CompanyListRealstateRecycleViewAdapter(List<com.qaim.qaim.Models.CompanyRealstate.DataItem> details ) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_of_realstate_adapter , null , false);
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
        com.qaim.qaim.Models.CompanyRealstate.DataItem projectDetails = details.get(position);
        id = projectDetails.getId();
        holder.onBind(projectDetails);



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

        public void onBind(com.qaim.qaim.Models.CompanyRealstate.DataItem projectDetails){
            name.setText(projectDetails.getTitle());
            description.setText(projectDetails.getDescription());
            Picasso.get().load(projectDetails.getImage()).fit().into(imageView);

            if (!projectDetails.getStatus().isEmpty() || !projectDetails.getStatus().equals(""))
            {
                if (projectDetails.getStatus().equals("refused") || projectDetails.getStatus().equals("finished")){
                    status.setText(projectDetails.getStatusTxt());
                    GradientDrawable gb = (GradientDrawable) status.getBackground();
                    gb.setColor(Color.parseColor(projectDetails.getColor()));                }else
                {
                    status.setVisibility(View.VISIBLE);
                    status.setText(projectDetails.getStatusTxt());
                    GradientDrawable gb = (GradientDrawable) status.getBackground();
                    gb.setColor(Color.parseColor(projectDetails.getColor()));
                }
            }

            sendRatingOffer.setOnClickListener(view -> onSendRateOfferBtnPressed(projectDetails.getId()));
        }

        public void onSendRateOfferBtnPressed(int id){
            SendRateDisplayFragment fragment = SendRateDisplayFragment.newInstance(id);
            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
        }


    }
}
