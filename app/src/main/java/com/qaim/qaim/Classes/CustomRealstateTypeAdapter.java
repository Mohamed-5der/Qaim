package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qaim.qaim.Models.TypesResponse.TypesItem;
import com.qaim.qaim.R;

import java.util.List;

public class CustomRealstateTypeAdapter extends BaseAdapter {
    List<TypesItem> typesItems ;

    public CustomRealstateTypeAdapter(List<TypesItem> typesItems) {
        this.typesItems = typesItems;
    }

    @Override
    public int getCount() {
        return typesItems.size();
    }

    @Override
    public TypesItem getItem(int i) {
        return typesItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return typesItems.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_city_adapter ,null , false );
        TextView typeName = v.findViewById(R.id.cityName);
        typeName.setText(getItem(i).getName());
        return v;
    }

    public int getIndex(int id) {
        for (int i = 0; i < typesItems.size(); i++) {
            if (typesItems.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}