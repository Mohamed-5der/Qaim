package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CompanyFragmentRecyceViewAdapter;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserCompaniesResponse.UserCompaniesResponse;
import com.qaim.qaim.R;

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