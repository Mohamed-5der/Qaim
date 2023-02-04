package com.Abdo.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Fragments.CompanyChooseTeamFragment;
import com.Abdo.qaim.Models.Networks.JsonApi;
import com.Abdo.qaim.Models.TeamPreviewerList.DataItem;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviwerListAdapter extends RecyclerView.Adapter<PreviwerListAdapter.ViewHolder> {

    List<DataItem> previewers ;
    AppCompatActivity activity ;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    public PreviwerListAdapter(List<DataItem> previewers) {
        this.previewers = previewers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_previewer_list , null , false);
        activity = (AppCompatActivity) parent.getContext();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem previewer = previewers.get(position);
        holder.name.setText(previewer.getName());
        holder.description.setText(previewer.getNotes());
        holder.cost.setText(previewer.getCost());
        Picasso.with(activity).load(previewer.getImage()).fit().error(activity.getDrawable(R.drawable.rectangle_4491)).into(holder.imageView);

        holder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(previewer.getId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , chooseTeamFragment).commit();
            }
        });
        holder.showProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileURL));
//                startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return previewers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        Button choose , showProfile ;
        TextView name , description , cost ;
        ImageView imageView ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            choose = itemView.findViewById(R.id.choose);
            showProfile = itemView.findViewById(R.id.showProfile);
            name = itemView.findViewById(R.id.tittle);
            description = itemView.findViewById(R.id.description);
            cost = itemView.findViewById(R.id.cost);
            imageView = itemView.findViewById(R.id.image);

        }
    }
}
