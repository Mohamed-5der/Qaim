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

import com.qaim.qaim.Fragments.RealStateDetailsFragment;
import com.qaim.qaim.Models.MyRealstateCompanyList.DataItem;
import com.qaim.qaim.Models.MyRealstateCompanyList.FilesItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompanyMyListRealstateRecycleViewAdapter extends RecyclerView.Adapter<CompanyMyListRealstateRecycleViewAdapter.ViewHolder> {
    public static String token ;
    List<DataItem> details ;
    List<FilesItem> filesItems ;
    int id ;
    AppCompatActivity activity ;

    public CompanyMyListRealstateRecycleViewAdapter(List<DataItem> details ) {
        this.details = details;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_of_realstate_adapter , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new CompanyMyListRealstateRecycleViewAdapter.ViewHolder(v);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name , description , status ;
        Button showBtn ; DataItem projectDetails;
        FilesItem filesItem1 ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameOforder) ;
            description = itemView.findViewById(R.id.descriptionOforder) ;
            status = itemView.findViewById(R.id.status) ;
            imageView = itemView.findViewById(R.id.image) ;
            showBtn = itemView.findViewById(R.id.editOrderBtn) ;
        }
        private void onBind( DataItem projectDetails){
            if (projectDetails.getRealEstate() != null) {
                name.setText(projectDetails.getRealEstate().getTitle());
                description.setText(projectDetails.getRealEstate().getDescription());
                List<FilesItem> filesItem = projectDetails.getRealEstate().getFiles();
                if (filesItem != null && !filesItem.isEmpty()){
                    Picasso.get().load(filesItem.get(0).getFile()).fit().into(imageView);
                }
            }else {
                name.setText("شركة");
            }

            if (projectDetails.getStatus().isEmpty())
            {
                status.setVisibility(View.GONE);
            }
            else
            {
                status.setText(projectDetails.getStatusText());
                GradientDrawable gb = (GradientDrawable) status.getBackground();
                gb.setColor(Color.parseColor(projectDetails.getColor()));            }
            showBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RealStateDetailsFragment fragment = RealStateDetailsFragment.newInstance(projectDetails.getId());
                    activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
                }
            });
        }
    }

}
