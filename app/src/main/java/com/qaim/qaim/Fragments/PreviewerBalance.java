package com.qaim.qaim.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Models.GetPreviewerBalance.GetPreviewerBalanceResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.PullPreviewerBalance.PullPreviewerBalanceResponse;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PreviewerBalance extends Fragment {

    TextView getMyBalance , myBalance ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    Button pullBalance ;
    public PreviewerBalance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_previewer_balance, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);

        getMyBalance = v.findViewById(R.id.balnce);
        myBalance =  v.findViewById(R.id.balnceDue);
        pullBalance =  v.findViewById(R.id.endOrderBtn);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<GetPreviewerBalanceResponse> call = jsonApi.getBalancePreviewer("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<GetPreviewerBalanceResponse>() {
            @Override
            public void onResponse(Call<GetPreviewerBalanceResponse> call, Response<GetPreviewerBalanceResponse> response) {
                GetPreviewerBalanceResponse getBalanceResponse = response.body();
                if (getBalanceResponse.getCode() == 200) {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    getMyBalance.setText(getBalanceResponse.getData().getAvailable()+"");
                    myBalance.setText(getBalanceResponse.getData().getBalance()+"");
//                    Toast.makeText(getActivity(), getBalanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getActivity(), getBalanceResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetPreviewerBalanceResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        pullBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPullBalanceAPI();
            }
        });
    }
    public void callPullBalanceAPI(){
        PreviewerActivity.dialog.show();
        Call<PullPreviewerBalanceResponse> call = jsonApi.pullBalancePreviewer("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<PullPreviewerBalanceResponse>() {
            @Override
            public void onResponse(Call<PullPreviewerBalanceResponse> call, Response<PullPreviewerBalanceResponse> response) {
                PreviewerActivity.dialog.dismiss();
                PullPreviewerBalanceResponse getPreviewerBalanceResponse = response.body();
                if (getPreviewerBalanceResponse.getCode() == 200) {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getActivity() , getPreviewerBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getActivity() , PreviewerActivity.class));
                }
                else {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , getPreviewerBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PullPreviewerBalanceResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}