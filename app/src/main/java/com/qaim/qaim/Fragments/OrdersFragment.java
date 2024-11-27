package com.qaim.qaim.Fragments;

import static com.qaim.qaim.Activities.SplashScreen.sToken;

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
import com.qaim.qaim.Classes.OrderFragmentRecyceViewAdapter;
import com.qaim.qaim.Models.AprovedList.ApprovedListResponse;
import com.qaim.qaim.Models.AprovedList.DataItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {

    RecyclerView recyclerView ;
    OrderFragmentRecyceViewAdapter adapter ;
    private orderFragmentListner listner ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    String token ;
    TextView noData ;
    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_orders, container, false);
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
        recyclerView = v.findViewById(R.id.order_recycleView) ;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        token = sToken.getString("token_key" , "");
        noData = v.findViewById(R.id.noData);

        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.dialog.show();
        Call<ApprovedListResponse> listUserResponseCall = jsonApi.aprovdedList("Bearer " + token);
        listUserResponseCall.enqueue(new Callback<ApprovedListResponse>() {
            @Override
            public void onResponse(Call<ApprovedListResponse> call, Response<ApprovedListResponse> response) {
                MainActivity.dialog.dismiss();
                ApprovedListResponse listUserResponse = response.body();
                if (listUserResponse.getCode() == 200) {
                    if (listUserResponse.getData().getRows().getData().isEmpty() || listUserResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }

                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (listUserResponse.getData().getRows() != null) {
                        List<DataItem> dataItems = listUserResponse.getData().getRows().getData();
                        adapter = new OrderFragmentRecyceViewAdapter(dataItems);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }else {
                        MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                    }
                }else {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                }
            }

            @Override
            public void onFailure(Call<ApprovedListResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public interface orderFragmentListner {
        void onCardClick(int  id);
    }

}