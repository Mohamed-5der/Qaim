package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.CompanyPaymentRecycleViewAdapter;
import com.qaim.qaim.Models.CompanyPaymentsResponse.CompanyPaymentsResponse;
import com.qaim.qaim.Models.CompanyPaymentsResponse.DataItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyPaymentsFragment extends Fragment {

    RecyclerView recyclerView ;
    CompanyPaymentRecycleViewAdapter adapter ;

    Retrofit retrofit ;
    JsonApi jsonApi ;

    String token ;

    public CompanyPaymentsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_payments, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyListOfRealstateFragment mainFragment = new CompanyListOfRealstateFragment ();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
            }
        });

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
        CompanyActivity.dialog.show();
        Call<CompanyPaymentsResponse> listUserResponseCall = jsonApi.companyPayments("Bearer " + CompanyActivity.token);
        listUserResponseCall.enqueue(new Callback<CompanyPaymentsResponse>() {
            @Override
            public void onResponse(Call<CompanyPaymentsResponse> call, Response<CompanyPaymentsResponse> response) {
                CompanyActivity.dialog.dismiss();
                CompanyPaymentsResponse companyPaymentsResponse = response.body();
                if (response.code() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (companyPaymentsResponse.getData().getRows() != null) {
                        List<DataItem> dataItems = companyPaymentsResponse.getData().getRows().getData();
                        adapter = new CompanyPaymentRecycleViewAdapter(dataItems);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                    }
                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<CompanyPaymentsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                CompanyActivity.dialog.dismiss();
            }
        });
    }
}