package com.qaim.qaim.Classes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.R;

import java.util.List;
@SuppressLint("NotifyDataSetChanged")
public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    List<Bitmap> bitmaps ;
    AppCompatActivity activity ;


    public ImageAdapter(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycle ,null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = bitmaps.get(position);
        holder.onBind(bitmap);

    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView , cancel ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            cancel = itemView.findViewById(R.id.cancel);
        }

        public void onBind( Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
            cancel.setOnClickListener(view -> {
                bitmaps.remove(getPosition());
                notifyItemRemoved(getPosition());
            });
        }
    }
}
