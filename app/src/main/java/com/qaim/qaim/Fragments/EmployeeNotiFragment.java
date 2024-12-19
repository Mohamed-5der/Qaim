package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.EmployeeActivity;
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

public class EmployeeNotiFragment extends Fragment {


    Retrofit retrofit ;
    JsonApi jsonApi ;

    RecyclerView recyclerView ;
    NotificationAdapter adapter ;
    public EmployeeNotiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_employee_noti, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            EmployeeMainFragment mainFragment = new EmployeeMainFragment ();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.order_recycleView);
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
        EmployeeActivity.dialog.show();
        Call<NotiResponse> call = jsonApi.notifications(LocaleHelper.getLanguage(getContext()), "Bearer " + EmployeeActivity.token);
        call.enqueue(new Callback<NotiResponse>() {
            @Override
            public void onResponse(Call<NotiResponse> call, Response<NotiResponse> response) {
                EmployeeActivity.dialog.dismiss();
                NotiResponse notiResponse = response.body();
                if (response.code() == 200) {
                    List<NotificationsItem> dataItems = notiResponse.getData().getNotifications();
                    adapter = new NotificationAdapter(dataItems);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }
            }
            @Override
            public void onFailure(Call<NotiResponse> call, Throwable t) {

            }
        });

    }
}