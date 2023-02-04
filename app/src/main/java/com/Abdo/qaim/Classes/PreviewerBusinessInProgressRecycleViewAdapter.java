package com.Abdo.qaim.Classes;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.Abdo.qaim.Fragments.PreviewerCommentsFragment;
import com.Abdo.qaim.Fragments.PreviewerListDetailsFragment;
import com.Abdo.qaim.Fragments.PreviewerWriteReportsFragment;
import com.Abdo.qaim.Models.MtListPreviewer.DataItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreviewerBusinessInProgressRecycleViewAdapter extends RecyclerView.Adapter <PreviewerBusinessInProgressRecycleViewAdapter.ViewHolder> {


    List<DataItem> details ;
    String phoneNumber , email , whatsAPP , imageURL ;

    AppCompatActivity activity ;

    public PreviewerBusinessInProgressRecycleViewAdapter(List<DataItem> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_previewer_business_in_progress, null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem businessInProgressDetails = details.get(position);
        holder.realStateName.setText(businessInProgressDetails.getInfo().getRealEstate().getTitle());
        holder.realStateDescription.setText(businessInProgressDetails.getInfo().getRealEstate().getDescription());
        holder.companyName.setText(businessInProgressDetails.getCompany().getName());
        phoneNumber = businessInProgressDetails.getCompany().getPhone();
        email = businessInProgressDetails.getCompany().getEmail();
        whatsAPP = businessInProgressDetails.getCompany().getPhone();
        imageURL = businessInProgressDetails.getCompany().getImage();
        Picasso.with(activity).load(imageURL).fit().error(R.drawable.group9821).into(holder.imageView);
        holder.phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                activity.startActivity(intent);
            }
        });
        holder.emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                activity.startActivity(emailIntent);
            }
        });
        holder.whatsAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              holder.onClickWhatsApp();
            }
        });
        holder.sendReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerWriteReportsFragment previewerWriteReportsFragment = PreviewerWriteReportsFragment.newInstance(businessInProgressDetails.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,previewerWriteReportsFragment).commit();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerListDetailsFragment fragment = PreviewerListDetailsFragment.newInstance(businessInProgressDetails.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,fragment).commit();
            }
        });
        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onCommentsClick(businessInProgressDetails.getInfo().getId());
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
        RelativeLayout phoneBtn , emailBtn , whatsAppBtn , comments ;
        Button sendReportBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            realStateName = itemView.findViewById(R.id.nameOforder);
            realStateDescription = itemView.findViewById(R.id.descriptionOforder) ;
            companyName = itemView.findViewById(R.id.companyName) ;
            imageView = itemView.findViewById(R.id.image) ;
            phoneBtn = itemView.findViewById(R.id.phoneBtn) ;
            emailBtn = itemView.findViewById(R.id.emailBtn) ;
            whatsAppBtn = itemView.findViewById(R.id.whatsAppBtn) ;
            sendReportBtn = itemView.findViewById(R.id.sendReport) ;
            comments = itemView.findViewById(R.id.comments) ;
        }
        public void onClickWhatsApp() {

            PackageManager pm=activity.getPackageManager();
            try {

                Uri uri = Uri.parse("smsto:" + whatsAPP);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                i.setPackage("com.whatsapp");
                activity.startActivity(Intent.createChooser(i, "Share with"));

            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }

        }
        public void onCommentsClick(int info_id){
            PreviewerCommentsFragment fragment = PreviewerCommentsFragment.newInstance(info_id);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
        }
    }




}
