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

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.PaymentRecycleViewAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.PreviwerPayments.DataItem;
import com.qaim.qaim.Models.PreviwerPayments.PreviewerPaymentsResponse;
import com.qaim.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PreviewerPaymentsFragment extends Fragment {
    RecyclerView recyclerView ;
    PaymentRecycleViewAdapter adapter ;

    Retrofit retrofit ;
    JsonApi jsonApi ;

    public PreviewerPaymentsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previewer_payments, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.order_recycleView) ;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<PreviewerPaymentsResponse> listUserResponseCall = jsonApi.previwerPayments(LocaleHelper.getLanguage(getContext()), "Bearer " + PreviewerActivity.token);
        listUserResponseCall.enqueue(new Callback<PreviewerPaymentsResponse>() {
            @Override
            public void onResponse(Call<PreviewerPaymentsResponse> call, Response<PreviewerPaymentsResponse> response) {
                PreviewerPaymentsResponse previewerPaymentsResponse = response.body();
                if (response.code() == 200) {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (previewerPaymentsResponse.getData().getRows() != null) {
                        List<DataItem> dataItems = previewerPaymentsResponse.getData().getRows().getData();
                        adapter = new PaymentRecycleViewAdapter(dataItems);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                    }
                }else {
                    PreviewerActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<PreviewerPaymentsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}