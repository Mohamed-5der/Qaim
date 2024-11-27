package com.qaim.qaim.Classes;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.Delete.DeleteFileResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateShowUserResponse.FilesItem;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageLoadAdapter extends RecyclerView.Adapter<ImageLoadAdapter.ViewHolder> {
    List<FilesItem> imageUrls ;
    List<Bitmap> bitmaps ;
    AppCompatActivity activity ;
    Retrofit retrofit;
    JsonApi jsonApi;
    public ImageLoadAdapter(List<FilesItem> imageUrls, List<Bitmap> bitmaps) {
        this.imageUrls = imageUrls;
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycle ,null , false);
        activity =(AppCompatActivity) parent.getContext();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilesItem url =  imageUrls.get(position);
        holder.onBind(url);

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView , cancel ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            cancel = itemView.findViewById(R.id.cancel);
        }

        public void onBind(FilesItem filesItem){
            Picasso.get().load(filesItem.getFile()).into(imageView);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<DeleteFileResponse> call = jsonApi.deleteFile(new FileIdParams(filesItem.getId()));
                    call.enqueue(new Callback<DeleteFileResponse>() {
                        @Override
                        public void onResponse(Call<DeleteFileResponse> call, Response<DeleteFileResponse> response) {
                            if (response.body().getCode() == 200){
                                Toast.makeText(activity , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(activity , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteFileResponse> call, Throwable t) {
                            Toast.makeText(activity , t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                    imageUrls.remove(getPosition());
                    notifyItemRemoved(getPosition());
                }
            });
        }


    }
}
