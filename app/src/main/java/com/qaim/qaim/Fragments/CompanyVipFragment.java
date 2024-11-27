package com.qaim.qaim.Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.maps.model.LatLng;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CompanyFragmentRecyceViewAdapter;
import com.qaim.qaim.Classes.CustomAttributed;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserCompaniesResponse.UserCompaniesResponse;
import com.qaim.qaim.Models.UserCompaniesResponse.UserCompaniesResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyVipFragment extends Fragment {

    RecyclerView recyclerView;
    CompanyFragmentRecyceViewAdapter adapter;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_company_vip, container, false);

        recyclerView = v.findViewById(R.id.recycleView);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = this.getResources();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return v;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.dialog.show();
        Call<UserCompaniesResponse> call = jsonApi.userCompanies("Bearer " + MainActivity.token);
        call.enqueue(new Callback<UserCompaniesResponse>() {
            @Override
            public void onResponse(Call<UserCompaniesResponse> call, Response<UserCompaniesResponse> response) {
                MainActivity.dialog.dismiss();
                UserCompaniesResponse UserCompaniesResponse = response.body();
                if (UserCompaniesResponse.getCode()== 200){
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (!UserCompaniesResponse.getData().getRows().isEmpty()){
                        adapter = new CompanyFragmentRecyceViewAdapter(UserCompaniesResponse.getData().getRows());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                } else {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                }
            }

            @Override
            public void onFailure(Call<UserCompaniesResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


    }
}