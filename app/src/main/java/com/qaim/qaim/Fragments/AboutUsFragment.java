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

import com.qaim.qaim.Models.AppInfo.AppInfoResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AboutUsFragment extends Fragment {

    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView aboutUs , serviceUs ;
    public AboutUsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_about_us, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        aboutUs = v.findViewById(R.id.whoUsTxt);
        serviceUs = v.findViewById(R.id.serviceTxt);
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

        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Call<AppInfoResponse> call = jsonApi.getAppInfo();
        call.enqueue(new Callback<AppInfoResponse>() {
            @Override
            public void onResponse(Call<AppInfoResponse> call, Response<AppInfoResponse> response) {
                AppInfoResponse appInfoResponse = response.body();
                if (response.code() == 200) {
                    appInfoResponse.getData().getInfo().getAbout();
                    appInfoResponse.getData().getInfo().getEmail();
                    appInfoResponse.getData().getInfo().getPhone();

                    aboutUs.setText("" +appInfoResponse.getData().getInfo().getEmail() + "\n" + appInfoResponse.getData().getInfo().getPhone() );
                    serviceUs.setText(""+appInfoResponse.getData().getInfo().getAbout());

                }else {
                    Toast.makeText(getContext() , appInfoResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AppInfoResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();

            }
        });
    }
}