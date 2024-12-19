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
import com.qaim.qaim.Classes.CompanyTransactionRecycleViewAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.GetBalance.GetBalanceResponse;
import com.qaim.qaim.Models.GetBalance.RowsItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyPreviousTransactionsFragment extends Fragment {

    RecyclerView recyclerView;
    CompanyTransactionRecycleViewAdapter adapter;

    Retrofit retrofit;
    JsonApi jsonApi;

    String token;

    public CompanyPreviousTransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_previous_transactions, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.order_recycleView);
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


        CompanyActivity.dialog.show();
        Call<GetBalanceResponse> call = jsonApi.getBalance(LocaleHelper.getLanguage(getContext()), "Bearer " + CompanyActivity.token);
        call.enqueue(new Callback<GetBalanceResponse>() {
            @Override
            public void onResponse(Call<GetBalanceResponse> call, Response<GetBalanceResponse> response) {
                CompanyActivity.dialog.dismiss();
                GetBalanceResponse getBalanceResponse = response.body();
                if (getBalanceResponse.getCode() == 200) {
                    if (getBalanceResponse.getData().getRows() != null) {
                        List<RowsItem> dataItems = getBalanceResponse.getData().getRows();
                        adapter = new CompanyTransactionRecycleViewAdapter(dataItems);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage(), getContext());
//                    Toast.makeText(getContext() , getBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBalanceResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}