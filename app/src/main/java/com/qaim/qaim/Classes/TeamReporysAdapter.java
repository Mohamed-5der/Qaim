package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.MyListTeamReports.Painter;
import com.qaim.qaim.Models.MyListTeamReports.Previewer;
import com.qaim.qaim.Models.MyListTeamReports.Reviewer;
import com.qaim.qaim.Models.MyListTeamReports.Row;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamReporysAdapter extends RecyclerView.Adapter<TeamReporysAdapter.ViewHolder> {

    List<Row> details ;

    AppCompatActivity activity ;
    public TeamReporysAdapter(List<Row> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_team_report , null , false);
        activity = (AppCompatActivity) parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Row data = details.get(position);
        Previewer previewer = data.getPreviewer();
        Painter painter = data.getPainter();
        Reviewer reviewer = data.getReviewer();

        if (previewer != null){
            holder.tittle.setText(R.string.Property_Inspector_Report);
            holder.name.setText(previewer.getName());
            Picasso.get().load(previewer.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(holder.imageView);
//            holder.description.setText(previewer.getEmail());
        }
        if (painter != null){
            holder.tittle.setText(R.string.Property_Inspector_Report);
            holder.name.setText(painter.getName());
            Picasso.get().load(painter.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(holder.imageView);
//            holder.description.setText(previewer.getEmail());
        }
        if (reviewer != null){
            holder.tittle.setText(R.string.Property_Inspector_Report);
            holder.name.setText(reviewer.getName());
            Picasso.get().load(reviewer.getImage()).fit().error(activity.getDrawable(R.drawable.icon)).into(holder.imageView);
//            holder.description.setText(previewer.getEmail());
        }


    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tittle , name , description ;
        ImageView imageView ;
        Button downLoadBtn , showBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.tit);
            name = itemView.findViewById(R.id.tittle);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
            downLoadBtn = itemView.findViewById(R.id.choose);
            showBtn = itemView.findViewById(R.id.showProfile);
        }
    }
}
