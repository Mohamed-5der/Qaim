package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.PreviewerBusinessInProgressRecycleViewAdapter;
import com.qaim.qaim.Models.MtListPreviewer.MyListPrevResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerBusinessInProgressFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView ;
    PreviewerBusinessInProgressRecycleViewAdapter adapter ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView noData ;
    public PreviewerBusinessInProgressFragment() {
        // Required empty public constructor
    }



    public static PreviewerBusinessInProgressFragment newInstance(String param1, String param2) {
        PreviewerBusinessInProgressFragment fragment = new PreviewerBusinessInProgressFragment();
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
        View v = inflater.inflate(R.layout.fragment_previewer_business_in_progress, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.offers_recycleView);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        noData = v.findViewById(R.id.noData);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        PreviewerActivity.dialog.show();
        Call<MyListPrevResponse> call = jsonApi.myListPreviewer("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<MyListPrevResponse>() {
            @Override
            public void onResponse(Call<MyListPrevResponse> call, Response<MyListPrevResponse> response) {
                PreviewerActivity.dialog.dismiss();
                MyListPrevResponse prevResponse = response.body();
                if (prevResponse.getCode() == 200){
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
                    if (prevResponse.getData().getRows().getData().isEmpty() || prevResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }
                    if (prevResponse.getData().getRows() != null) {
                        adapter = new PreviewerBusinessInProgressRecycleViewAdapter(prevResponse.getData().getRows().getData());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                    }
                }else {
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , prevResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyListPrevResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                PreviewerActivity.dialog.dismiss();
            }
        });

    }
}