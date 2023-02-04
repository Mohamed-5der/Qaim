package com.Abdo.qaim.Classes;

import static android.view.View.TEXT_DIRECTION_LTR;
import static android.view.View.TEXT_DIRECTION_RTL;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Models.AllCommentsResponse.CommentsItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsCompanyAdapter extends RecyclerView.Adapter<CommentsCompanyAdapter.ViewHolder> {

    List<CommentsItem> detalis ;
    AppCompatActivity activity ;
    ImageView imageView ;
    TextView commentTEXT;

    public CommentsCompanyAdapter(List<CommentsItem> detalis) {
        this.detalis = detalis;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout ,null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentsItem comments =detalis.get(position);

        if (comments.getCompany() != null){

            holder.commentTEXT.setText(comments.getComment());
            try{
                Picasso.with(activity).load(comments.getCompany().getImage()).fit().error(activity.getDrawable(R.drawable.as0)).into(holder.imageView);
            }catch(NullPointerException e){
                Log.e(TAG, e.toString());
            }
            holder.imageView2.setVisibility(View.GONE);
            holder.commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
        }
        else if (comments.getEmployee() != null){
            try{
                Picasso.with(activity).load(comments.getEmployee().getImage()).fit().error(activity.getDrawable(R.drawable.rectangle_3)).into(holder.imageView2);
            }catch(NullPointerException e){
                Log.e(TAG, e.toString());
            }
            holder.commentTEXT.setText(comments.getComment());
            holder.imageView.setVisibility(View.GONE);
            holder.commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
        }
        else if (comments.getPreviewer() != null){
            try{
                Picasso.with(activity).load(comments.getPreviewer().getImage()).fit().error(activity.getDrawable(R.drawable.path21712)).into(holder.imageView2);
            }catch(NullPointerException e){
                Log.e(TAG, e.toString());
            }
            holder.commentTEXT.setText(comments.getComment());
            holder.commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
            holder.imageView.setVisibility(View.GONE);


        }

    }

    @Override
    public int getItemCount() {
        return detalis.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView , imageView2 ;
        TextView commentTEXT;
        RelativeLayout rowItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyView1);
            imageView2 = itemView.findViewById(R.id.companyView2);
            commentTEXT = itemView.findViewById(R.id.commentText);
            rowItem = itemView.findViewById(R.id.rel);
        }
    }
}
