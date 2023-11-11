package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.qaim.qaim.Classes.PreviewerOrdersDetailsRecycleViewAdapter;
import com.qaim.qaim.Models.GetOrdersPreviewer.OrderPrevResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerOrdersFragment extends Fragment {

    RecyclerView recyclerView ;
    PreviewerOrdersDetailsRecycleViewAdapter adapter ;

    private String mParam1;
    private String mParam2;

    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView noData , tx ;
    public PreviewerOrdersFragment() {
        // Required empty public constructor
    }


    public static PreviewerOrdersFragment newInstance() {
        PreviewerOrdersFragment fragment = new PreviewerOrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previewer_orders, container, false);
//        ImageButton imageButton = v.findViewById(R.id.imageBtn);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment ();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
//            }
//        });
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.PreviewerRecycleView);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        noData = v.findViewById(R.id.noData);
        tx = v.findViewById(R.id.tx);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreviewerActivity.dialog.show();
        Call<OrderPrevResponse> call = jsonApi.getOrdersPreviewer("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<OrderPrevResponse>() {
            @Override
            public void onResponse(Call<OrderPrevResponse> call, Response<OrderPrevResponse> response) {
                PreviewerActivity.dialog.dismiss();
                OrderPrevResponse prevResponse = response.body();
                if (prevResponse.getCode() == 200){
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (prevResponse.getData().getRows().getData().isEmpty() || prevResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                        tx.setVisibility(View.VISIBLE);
                    }
                    if (prevResponse.getData().getRows() != null) {
                        adapter = new PreviewerOrdersDetailsRecycleViewAdapter(prevResponse.getData().getRows().getData());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }else {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }

            }

            @Override
            public void onFailure(Call<OrderPrevResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                PreviewerActivity.dialog.dismiss();
            }
        });



    }
}