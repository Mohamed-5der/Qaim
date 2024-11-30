package com.qaim.qaim.Classes;

import static android.view.View.TEXT_DIRECTION_LTR;
import static android.view.View.TEXT_DIRECTION_RTL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.EmployeeComments.CommentsItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentsEmployeeAdapter extends RecyclerView.Adapter<CommentsEmployeeAdapter.ViewHolder> {
    List<CommentsItem> commentsItems;
    AppCompatActivity activity;

    public CommentsEmployeeAdapter(List<CommentsItem> commentsItems) {
        this.commentsItems = commentsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_layout, null, false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentsItem comments = commentsItems.get(position);
        holder.onBind(comments);

//        com.qaim.qaim.Models.AllCommentsResponse.CommentsItem comments =commentsItems.get(position);

    }

    @Override
    public int getItemCount() {
        return commentsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imageView2, iv_image;
        TextView commentTEXT, senderText;
        RelativeLayout rowItem;

        CommentsItem comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyView1);
            imageView2 = itemView.findViewById(R.id.companyView2);
            iv_image = itemView.findViewById(R.id.iv_image);
            commentTEXT = itemView.findViewById(R.id.commentText);
            senderText = itemView.findViewById(R.id.senderText);
            rowItem = itemView.findViewById(R.id.rel);
        }

        public void onBind(CommentsItem comments) {
            if (comments.getCompany() != null) {
                Picasso.get().load(comments.getCompany().getImage()).fit().into(imageView);
                imageView.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
            } else if (comments.getEmployee() != null) {
                Picasso.get().load(comments.getEmployee().getImage()).fit().into(imageView2);
                commentTEXT.setText(comments.getComment());
                imageView2.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
            } else if (comments.getPreviewer() != null) {
                Picasso.get().load(comments.getPreviewer().getImage()).fit().into(imageView2);
                commentTEXT.setText(comments.getComment());
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
                imageView.setVisibility(View.GONE);
            }
            // Set Image
            if (comments.getType().equals("text")) {
                commentTEXT.setVisibility(View.VISIBLE);
                iv_image.setVisibility(View.GONE);
                commentTEXT.setText(comments.getComment());
                commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
            } else {
                commentTEXT.setVisibility(View.GONE);
                iv_image.setVisibility(View.VISIBLE);
                Picasso.get().load(comments.getComment()).fit().into(iv_image);
            }
        }

    }
}
