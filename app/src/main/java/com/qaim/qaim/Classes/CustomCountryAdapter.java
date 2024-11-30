package com.qaim.qaim.Classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qaim.qaim.Models.CountriesResponse.CountriesItem;
import com.qaim.qaim.R;

import java.util.List;

public class CustomCountryAdapter extends BaseAdapter {
    List<CountriesItem> countriesItem ;

    public CustomCountryAdapter(List<CountriesItem> countriesItem) {
        this.countriesItem = countriesItem;
    }

    @Override
    public int getCount() {
        return countriesItem.size();
    }

    @Override
    public CountriesItem getItem(int i) {
        return countriesItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return countriesItem.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_country_adapter ,null , false );
        TextView countryName = v.findViewById(R.id.countryName);
        countryName.setText(getItem(i).getName());
        return v;
    }

    public int getIndex(int id) {
        for (int i = 0; i < countriesItem.size(); i++) {
            if (countriesItem.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
