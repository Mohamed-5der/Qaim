package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Models.AppInfo.AppInfoResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TermsAndConditionsFragment extends Fragment {

    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView terms ;

    public TermsAndConditionsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
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
        terms = v.findViewById(R.id.terms);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<AppInfoResponse> call = jsonApi.getAppInfo();
        call.enqueue(new Callback<AppInfoResponse>() {
            @Override
            public void onResponse(Call<AppInfoResponse> call, Response<AppInfoResponse> response) {
                AppInfoResponse appInfoResponse = response.body();
                if (response.code() == 200) {
                    terms.setText(Html.fromHtml("" +appInfoResponse.getData().getInfo().getTerms()));
                }else {
                    Toast.makeText(getContext() , appInfoResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AppInfoResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}