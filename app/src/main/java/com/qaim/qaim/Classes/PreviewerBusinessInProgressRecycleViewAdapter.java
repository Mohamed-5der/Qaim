package com.qaim.qaim.Classes;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Fragments.PreviewerCommentsFragment;
import com.qaim.qaim.Fragments.PreviewerListDetailsFragment;
import com.qaim.qaim.Fragments.PreviewerWriteReportsFragment;
import com.qaim.qaim.Models.MtListPreviewer.DataItem;
import com.qaim.qaim.R;
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
        holder.onBind(businessInProgressDetails);


    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView realStateName , realStateDescription , companyName , status;
        ImageView imageView ;
        RelativeLayout phoneBtn , emailBtn , whatsAppBtn , comments ;
        Button sendReportBtn , showDetailsBtn ;
        DataItem businessInProgressDetails  ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            realStateName = itemView.findViewById(R.id.nameOforder);
            realStateDescription = itemView.findViewById(R.id.descriptionOforder) ;
            companyName = itemView.findViewById(R.id.companyName) ;
            status = itemView.findViewById(R.id.status) ;
            imageView = itemView.findViewById(R.id.image) ;
            phoneBtn = itemView.findViewById(R.id.phoneBtn) ;
            emailBtn = itemView.findViewById(R.id.emailBtn) ;
            whatsAppBtn = itemView.findViewById(R.id.whatsAppBtn) ;
            sendReportBtn = itemView.findViewById(R.id.sendReport) ;
            showDetailsBtn = itemView.findViewById(R.id.showDetailsBtn) ;
            comments = itemView.findViewById(R.id.comments) ;
        }

        public void onBind(DataItem businessInProgressDetails){
            realStateName.setText(businessInProgressDetails.getInfo().getRealEstate().getTitle());
            realStateDescription.setText(businessInProgressDetails.getInfo().getNotes());
            companyName.setText(activity.getString(R.string.evaluation_company_name) + businessInProgressDetails.getCompany().getName());

            status.setText(businessInProgressDetails.getInfo().getRealEstate().getStatusTxt());
            GradientDrawable gb = (GradientDrawable) status.getBackground();
            gb.setColor(Color.parseColor(businessInProgressDetails.getInfo().getRealEstate().getColor()));

            phoneNumber = businessInProgressDetails.getCompany().getPhone();
            email = businessInProgressDetails.getCompany().getEmail();
            whatsAPP = businessInProgressDetails.getCompany().getPhone();
            if (!businessInProgressDetails.getInfo().getRealEstate().getFiles().isEmpty() && businessInProgressDetails.getInfo().getRealEstate().getFiles()!= null){
                imageURL = businessInProgressDetails.getInfo().getRealEstate().getFiles().get(0).getFile();
                Picasso.get().load(imageURL).fit().error(R.drawable.group9821).into(imageView);
            }
            if (businessInProgressDetails.getCanAddReport() == 1){
                sendReportBtn.setVisibility(View.VISIBLE);
            }


            phoneBtn.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                activity.startActivity(intent);
            });
            emailBtn.setOnClickListener(view -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                activity.startActivity(emailIntent);
            });
            whatsAppBtn.setOnClickListener(view -> onClickWhatsApp());
            sendReportBtn.setOnClickListener(view -> {
                PreviewerWriteReportsFragment previewerWriteReportsFragment = PreviewerWriteReportsFragment.newInstance(businessInProgressDetails.getInfo().getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,previewerWriteReportsFragment).addToBackStack(null).commit();
            });

            showDetailsBtn.setOnClickListener(view -> {
                PreviewerListDetailsFragment fragment = PreviewerListDetailsFragment.newInstance(businessInProgressDetails.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,fragment).addToBackStack(null).commit();
            });
            itemView.setOnClickListener(view -> {
                PreviewerListDetailsFragment fragment = PreviewerListDetailsFragment.newInstance(businessInProgressDetails.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,fragment).addToBackStack(null).commit();
            });
            comments.setOnClickListener(view -> onCommentsClick(businessInProgressDetails.getInfo().getId()));
        }

        public void onClickWhatsApp() {

            String phoneNumberWithCountryCode = whatsAPP;
            String message = "";

            activity.startActivity(
                    new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)
                            )
                    )
            );

        }
        public void onCommentsClick(int info_id){
            PreviewerCommentsFragment fragment = PreviewerCommentsFragment.newInstance(info_id);
            activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
        }
    }




}
