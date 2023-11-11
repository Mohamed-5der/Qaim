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

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.OfferFragmentRecyceViewAdapter;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.OrderListUserResponse.OrderListUserResponse;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OffersFragment extends Fragment {

    RecyclerView recyclerView ;
    OfferFragmentRecyceViewAdapter adapter ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView noData ;

    public OffersFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_offers, container, false);
//        ImageButton imageButton = v.findViewById(R.id.imageBtn);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MainFragment fragment = new  MainFragment();
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction().
//                        replace(R.id.frameLayout , fragment).commit();
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
        recyclerView = v.findViewById(R.id.offers_recycleView);
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
        MainActivity.dialog.show();
        Call<OrderListUserResponse> call = jsonApi.getAllOrdersList("Bearer " +MainActivity.token);
        call.enqueue(new Callback<OrderListUserResponse>() {
            @Override
            public void onResponse(Call<OrderListUserResponse> call, Response<OrderListUserResponse> response) {
                MainActivity.dialog.dismiss();
                OrderListUserResponse orderListUserResponse = response.body();
                if (response.code() == 200) {
                    if (orderListUserResponse.getData().getRows().getData().isEmpty() || orderListUserResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                    if (orderListUserResponse.getData().getRows() != null) {
                        adapter = new OfferFragmentRecyceViewAdapter(orderListUserResponse.getData().getRows().getData());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }else {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                }
            }

            @Override
            public void onFailure(Call<OrderListUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                MainActivity.dialog.dismiss();
            }
        });


    }
}