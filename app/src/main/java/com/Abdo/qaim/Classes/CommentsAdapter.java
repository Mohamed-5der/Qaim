package com.Abdo.qaim.Classes;

import static android.view.View.TEXT_DIRECTION_LTR;
import static android.view.View.TEXT_DIRECTION_RTL;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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

import com.Abdo.qaim.Models.MyListCommentsPreviewer.CommentsItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    List<CommentsItem> commentsItems ;
    AppCompatActivity activity ;

    public CommentsAdapter(List<CommentsItem> commentsItems) {
        this.commentsItems = commentsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout ,null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentsItem comments = commentsItems.get(position);
        if (comments.getCompany() != null){

            holder.commentTEXT.setText(comments.getComment());
            try{
                Picasso.with(activity).load(comments.getCompany().getImage()).fit().error(activity.getDrawable(R.drawable.as0)).into(holder.imageView);
            }catch(NullPointerException e){
                Log.e(TAG, e.toString());
            }
            holder.imageView.setVisibility(View.GONE);
            holder.commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
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
            holder.commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
            holder.imageView2.setVisibility(View.GONE);


        }
    }

    @Override
    public int getItemCount() {
        return commentsItems.size();
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
