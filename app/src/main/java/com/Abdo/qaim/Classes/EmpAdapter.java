package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Fragments.EmployeeCommentsFragment;
import com.Abdo.qaim.Fragments.EmployeeListDetailsFragment;
import com.Abdo.qaim.Models.MyListEmployee.DataItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.ViewHolder> {

    List<DataItem> details ;
    AppCompatActivity activity ;
    String  imageURL ;
    public EmpAdapter(List<DataItem> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_adapter, null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dataItem = details.get(position);
        holder.realStateName.setText(dataItem.getRealEstate().getTitle());
        holder.realStateDescription.setText(dataItem.getRealEstate().getDescription());
        holder.companyName.setText(dataItem.getRealEstate().getAddress());
        Picasso.with(activity).load(imageURL).fit().error(R.drawable.group9821).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeListDetailsFragment fragment = EmployeeListDetailsFragment.newInstance(dataItem.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,fragment).commit();
            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onCommentsClick(dataItem.getRealEstate().getId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView realStateName , realStateDescription , companyName ;
        ImageView imageView ;
        RelativeLayout  comments ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            realStateName = itemView.findViewById(R.id.nameOforder);
            realStateDescription = itemView.findViewById(R.id.descriptionOforder) ;
            companyName = itemView.findViewById(R.id.companyName) ;
            imageView = itemView.findViewById(R.id.image) ;
            comments = itemView.findViewById(R.id.comments) ;
        }

        public void onCommentsClick(int info_id){
            EmployeeCommentsFragment fragment = EmployeeCommentsFragment.newInstance(info_id);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
        }
    }
}
