package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qaim.qaim.Models.CitiesResponse.CitiesItem;
import com.qaim.qaim.R;

import java.util.List;

public class CustomCityAdapter extends BaseAdapter {
    List<CitiesItem> citiesItems ;

    public CustomCityAdapter(List<CitiesItem> citiesItems) {
        this.citiesItems = citiesItems;
    }

    @Override
    public int getCount() {
        return citiesItems.size();
    }

    @Override
    public CitiesItem getItem(int i) {
        return citiesItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return citiesItems.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_city_adapter ,null , false );
        TextView cityName = v.findViewById(R.id.cityName);
        cityName.setText(getItem(i).getName());
        return v;
    }

    public int getIndex(int id) {
        for (int i = 0; i < citiesItems.size(); i++) {
            if (citiesItems.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
