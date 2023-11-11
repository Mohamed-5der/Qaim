package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.Notification.NotificationsItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<NotificationsItem> notificationsItems ;
    AppCompatActivity activity ;

    public NotificationAdapter(List<NotificationsItem> notificationsItems) {
        this.notificationsItems = notificationsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationsItem notification = notificationsItems.get(position);
        holder.onBind(notification);

    }

    @Override
    public int getItemCount() {
        return notificationsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView tittle , description ;
        NotificationsItem notification ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            tittle = itemView.findViewById(R.id.tittle);
            description = itemView.findViewById(R.id.description);
        }
        public void onBind(NotificationsItem notification){
            String imag = notification.getSender().getImage();
            Picasso.get().load(imag).fit().into(imageView);
            tittle.setText(notification.getTitle());
            description.setText(notification.getContent());
        }
    }
}
