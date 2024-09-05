package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Models.RealstateShowUserResponse.AttributesItem;
import com.qaim.qaim.Models.RealstateShowUserResponse.NoteItem;
import com.qaim.qaim.R;

import java.util.List;

public class CustomNote extends RecyclerView.Adapter<CustomNote.ViewHolder> {

    List<NoteItem> attributesItems ;

    public CustomNote(List<NoteItem> attributesItems) {
        this.attributesItems = attributesItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_attribues , null , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteItem attributesItem =attributesItems.get(position);
        holder.onBind(attributesItem);
    }

    @Override
    public int getItemCount() {
        return attributesItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title , des ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tittle);
            des= itemView.findViewById(R.id.description);
        }

        public void onBind(NoteItem attributesItem){
            title.setText("");
//            title.setTextColor(Color.parseColor(attributesItem.getColor()));
            des.setText(attributesItem.getNotes());
//            des.setTextColor(Color.parseColor(attributesItem.getColor()));
        }
    }
}
