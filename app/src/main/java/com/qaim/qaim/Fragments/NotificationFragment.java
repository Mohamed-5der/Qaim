package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.NotificationAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.Notification.NotiResponse;
import com.qaim.qaim.Models.Notification.NotificationsItem;
import com.qaim.qaim.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NotificationFragment extends Fragment {
    Retrofit retrofit ;
    JsonApi jsonApi ;

    RecyclerView recyclerView ;
    NotificationAdapter adapter ;
    TextView noData ;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_notification, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            MainFragment fragment = new  MainFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.frameLayout , fragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.order_recycleView);
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
        MainActivity.dialog.show();
        Call<NotiResponse> call = jsonApi.notifications(LocaleHelper.getLanguage(getContext()), "Bearer " + MainActivity.token);
        call.enqueue(new Callback<NotiResponse>() {
            @Override
            public void onResponse(Call<NotiResponse> call, Response<NotiResponse> response) {
                MainActivity.dialog.dismiss();
                NotiResponse notiResponse = response.body();
                if (response.code() == 200) {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (notiResponse.getData().getNotifications().isEmpty() || notiResponse.getData().getNotifications() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }

                    List<NotificationsItem> dataItems = notiResponse.getData().getNotifications();
                    adapter = new NotificationAdapter(dataItems);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }else {
                    if (response.body() != null) {
                        MainActivity.alert.crateMsg(response.body().getMessage(), getContext());
                    }
                }
            }
            @Override
            public void onFailure(Call<NotiResponse> call, Throwable t) {

            }
        });
    }
}