package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.CompanyListRealstateRecycleViewAdapter;
import com.qaim.qaim.Models.CompanyRealstate.CompanyRealstateResponse;
import com.qaim.qaim.Models.CompanyRealstate.DataItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CompanyListOfRealstateFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    RecyclerView recyclerView ;
    CompanyListRealstateRecycleViewAdapter adapter ;
    ArrayList<DataItem> arrayList = new ArrayList<com.qaim.qaim.Models.CompanyRealstate.DataItem>();
    TextView noData ;
    public CompanyListOfRealstateFragment() {
        // Required empty public constructor
    }


    public static CompanyListOfRealstateFragment newInstance(String param1, String param2) {
        CompanyListOfRealstateFragment fragment = new CompanyListOfRealstateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_list_of_realstate, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.order_recycleView);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        noData = v.findViewById(R.id.noData);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CompanyActivity.dialog.show();
        Call<CompanyRealstateResponse> call = jsonApi.getAllCompanyRealstateList("Bearer " + CompanyActivity.token);
        call.enqueue(new Callback<CompanyRealstateResponse>() {
            @Override
            public void onResponse(Call<CompanyRealstateResponse> call, Response<CompanyRealstateResponse> response) {
                CompanyActivity.dialog.dismiss();
                CompanyRealstateResponse companyRealstateResponse = response.body();
                if (response.code() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (companyRealstateResponse.getData().getRows().getData().isEmpty() || companyRealstateResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }
                    if (companyRealstateResponse.getData().getRows() != null) {
                        List<DataItem> dataItems = companyRealstateResponse.getData().getRows().getData();
                        adapter = new CompanyListRealstateRecycleViewAdapter(dataItems);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }
                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }
            @Override
            public void onFailure(Call<CompanyRealstateResponse> call, Throwable t) {
                CompanyActivity.dialog.dismiss();
            }
        });


    }
}