package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.PullBalance.PullBalanceResponse;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyBalance extends Fragment {
    TextView getMyBalance , myBalance ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    Button pullBalance ;
    AppCompatButton previousTransactionsBtn ;

    public CompanyBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_company_balance, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyListOfRealstateFragment mainFragment = new CompanyListOfRealstateFragment ();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
            }
        });
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

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

        getMyBalance = v.findViewById(R.id.balnce);
        myBalance =  v.findViewById(R.id.balnceDue);
        pullBalance =  v.findViewById(R.id.endOrderBtn);
        previousTransactionsBtn =  v.findViewById(R.id.previousTransactionsBtn);
        CompanyActivity.dialog.show();
        Call<GetBalanceResponse> call = jsonApi.getBalance("Bearer " + CompanyActivity.token);
        call.enqueue(new Callback<GetBalanceResponse>() {
            @Override
            public void onResponse(Call<GetBalanceResponse> call, Response<GetBalanceResponse> response) {
                CompanyActivity.dialog.dismiss();
                GetBalanceResponse getBalanceResponse = response.body();
                if (getBalanceResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    getMyBalance.setText(getBalanceResponse.getData().getAvailable()+"");
                    myBalance.setText(getBalanceResponse.getData().getBalance()+"");
                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , getBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBalanceResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPullBalanceAPI();
            }
        });

        previousTransactionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyPreviousTransactionsFragment companyTransactions = new CompanyPreviousTransactionsFragment();
                loadFragment(companyTransactions);
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction().disallowAddToBackStack()
                .replace(R.id.frameLayout , fragment)
                .commit();
    }

    public void callPullBalanceAPI(){
        CompanyActivity.dialog.show();
        Call<PullBalanceResponse> call = jsonApi.pullBalance("Bearer " + CompanyActivity.token);
        call.enqueue(new Callback<PullBalanceResponse>() {
            @Override
            public void onResponse(Call<PullBalanceResponse> call, Response<PullBalanceResponse> response) {
                CompanyActivity.dialog.dismiss();
                PullBalanceResponse getPreviewerBalanceResponse = response.body();
                if (getPreviewerBalanceResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , getPreviewerBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }
                else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , getPreviewerBalanceResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PullBalanceResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}