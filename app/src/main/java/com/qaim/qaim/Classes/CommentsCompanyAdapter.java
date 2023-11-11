package com.qaim.qaim.Classes;

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

import com.qaim.qaim.Models.AllCommentsResponse.CommentsItem;
import com.qaim.qaim.R;
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
        holder.onBind(comments);
    }

    @Override
    public int getItemCount() {
        return detalis.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView , imageView2 ;
        TextView commentTEXT;
        RelativeLayout rowItem;
        CommentsItem comments ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyView1);
            imageView2 = itemView.findViewById(R.id.companyView2);
            commentTEXT = itemView.findViewById(R.id.commentText);
            rowItem = itemView.findViewById(R.id.rel);

        }
        void onBind(CommentsItem comments){
            if (comments.getCompany() != null){

               commentTEXT.setText(comments.getComment());
                try{
                    Picasso.get().load(comments.getCompany().getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(imageView);
                }catch(NullPointerException e){
                    Log.e(TAG, e.toString());
                }
                imageView2.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
            }
            else if (comments.getEmployee() != null){
                try{
                    Picasso.get().load(comments.getEmployee().getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(imageView2);
                }catch(NullPointerException e){
                    Log.e(TAG, e.toString());
                }
                commentTEXT.setText(comments.getComment());
                imageView.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
            }
            else if (comments.getPreviewer() != null){
                try{
                    Picasso.get().load(comments.getPreviewer().getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(imageView2);
                }catch(NullPointerException e){
                    Log.e(TAG, e.toString());
                }
                commentTEXT.setText(comments.getComment());
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
                imageView.setVisibility(View.GONE);

            }
        }
    }
}
