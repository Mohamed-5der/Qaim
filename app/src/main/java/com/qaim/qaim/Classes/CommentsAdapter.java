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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.MyListCommentsPreviewer.CommentsItem;
import com.qaim.qaim.R;
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
        holder.onBind(comments);
    }

    @Override
    public int getItemCount() {
        return commentsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView , imageView2 ;
        TextView commentTEXT;
        RelativeLayout rowItem;
        CardView companyViewRight , companyViewLeft ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.companyView1);
            imageView2 = itemView.findViewById(R.id.companyView2);
            commentTEXT = itemView.findViewById(R.id.commentText);
            rowItem = itemView.findViewById(R.id.rel);
            companyViewRight = itemView.findViewById(R.id.companyViewRight);
            companyViewLeft = itemView.findViewById(R.id.companyViewLeft);
        }

        public void onBind(CommentsItem comments){
            if (comments.getCompany() != null){
                commentTEXT.setText(comments.getComment());
                String imag = comments.getCompany().getImage();
                Picasso.get().load(imag).fit().into(imageView2);
                imageView.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
                companyViewLeft.setVisibility(View.VISIBLE);
            }
            else if (comments.getEmployee() != null){
                String imag = comments.getEmployee().getImage();
                Picasso.get().load(imag).fit().into(imageView2);
                commentTEXT.setText(comments.getComment());
                imageView.setVisibility(View.GONE);
                commentTEXT.setTextDirection(TEXT_DIRECTION_LTR);
                companyViewLeft.setVisibility(View.VISIBLE);
            }
            else if (comments.getPreviewer() != null){
                String imag = comments.getPreviewer().getImage();
                Picasso.get().load(imag).fit().into(imageView);
                commentTEXT.setText(comments.getComment());
                commentTEXT.setTextDirection(TEXT_DIRECTION_RTL);
                imageView2.setVisibility(View.GONE);


            }
        }
    }
}
