package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Fragments.EmployeeCommentsFragment;
import com.qaim.qaim.Fragments.EmployeeListDetailsFragment;
import com.qaim.qaim.Fragments.ShowPreviewerReportEmployeeFragment;
import com.qaim.qaim.Models.MyListEmployee.DataItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.ViewHolder> {

    List<DataItem> details ;
    AppCompatActivity activity ;
    String  imageURL ;
    int hasReport ;
    int id ;
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
        holder.onBind(dataItem);

       holder.showPrevReport.setOnClickListener(view -> {
           Toast.makeText(activity, "" +  dataItem.getId(), Toast.LENGTH_LONG).show();
           ShowPreviewerReportEmployeeFragment fragment = ShowPreviewerReportEmployeeFragment.newInstance( dataItem.getId());
           activity.getSupportFragmentManager().beginTransaction().addToBackStack(null)
                   .replace(R.id.frameLayout , fragment)
                   .commit();
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
        Button showPrevReport , showdetails ;
        DataItem dataItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            realStateName = itemView.findViewById(R.id.nameOforder);
            realStateDescription = itemView.findViewById(R.id.descriptionOforder) ;
            companyName = itemView.findViewById(R.id.companyName) ;
            imageView = itemView.findViewById(R.id.image) ;
            comments = itemView.findViewById(R.id.comments) ;
            showPrevReport = itemView.findViewById(R.id.showPrevReport);
            showdetails = itemView.findViewById(R.id.showdetails);
        }

        public void onBind(DataItem dataItem){
            realStateName.setText(dataItem.getRealEstate().getTitle());
            realStateDescription.setText(dataItem.getRealEstate().getDescription());
            companyName.setText(dataItem.getRealEstate().getAddress());
            hasReport =dataItem.getPrevHasReport();
            if (!dataItem.getRealEstate().getFiles().isEmpty() && dataItem.getRealEstate().getFiles() != null){
                String file = dataItem.getRealEstate().getFiles().get(0).getFile();
                Picasso.get().load(file).fit().error(R.drawable.group9821).into(imageView);
            }
            showdetails.setOnClickListener(view -> {
                EmployeeListDetailsFragment fragment = EmployeeListDetailsFragment.newInstance(dataItem.getId());
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout ,fragment).commit();
            });
            itemView.setOnClickListener(view -> {
                EmployeeListDetailsFragment fragment = EmployeeListDetailsFragment.newInstance(dataItem.getId());
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout ,fragment).commit();
            });
            comments.setOnClickListener(view -> onCommentsClick(dataItem.getRealEstate().getId()));
            if (hasReport == 1){
                showPrevReport.setVisibility(View.VISIBLE);
            }

        }


        public void onCommentsClick(int info_id){
            EmployeeCommentsFragment fragment = EmployeeCommentsFragment.newInstance(info_id);
            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
        }
    }
}
