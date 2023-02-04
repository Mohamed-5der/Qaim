package com.Abdo.qaim.Classes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Models.EmployeeComments.CommentsItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsEmployeeAdapter extends RecyclerView.Adapter<CommentsEmployeeAdapter.ViewHolder> {
    List<CommentsItem> commentsItems ;
    AppCompatActivity activity ;

    public CommentsEmployeeAdapter(List<CommentsItem> commentsItems) {
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

        try{
            Picasso.with(activity).load(comments.getEmployee().getImage()).fit().error(activity.getDrawable(R.drawable.as1)).into(holder.imageView);
        }catch(NullPointerException e){
            Log.e(TAG, e.toString());
        }

        holder.commentTEXT.setText(comments.getComment());
    }

    @Override
    public int getItemCount() {
        return commentsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView commentTEXT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyView1);
            commentTEXT = itemView.findViewById(R.id.commentText);
        }

    }

}
