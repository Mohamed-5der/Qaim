package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.TeamAddParams;
import com.qaim.qaim.Models.AddTeamResponse.AddTeamResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
import com.hbb20.CountryCodePicker;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AddArtistOrCheakerFragment extends Fragment {

    EditText useName , email , password , addData , phoneEditText ;
    CountryCodePicker countryCode;
    Button addBtn ;
    TeamAddParams params ;
    String type ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    String nameV  , emailV , passwordV ;


    public AddArtistOrCheakerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_artist_or_cheaker, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanySetTeamFragment fragment = new  CompanySetTeamFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , fragment).commit();
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
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        useName = v.findViewById(R.id.addNameEditText);
        email = v.findViewById(R.id.addEmailEditText);
        password = v.findViewById(R.id.EditProfilePasswordEditText);
        addData = v.findViewById(R.id.addAdttionalDataEditText);
        addBtn = v.findViewById(R.id.confirm);
        phoneEditText = v.findViewById(R.id.phoneEditText);
        countryCode = v.findViewById(R.id.countryCode);
        countryCode.registerCarrierNumberEditText(phoneEditText);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "painter";
                params = new TeamAddParams(String.valueOf(useName.getText()),
                        String.valueOf(email.getText()),
                        String.valueOf(password.getText()),
                        type, countryCode.getSelectedCountryNameCode(), phoneEditText.getText().toString()
                );

                if (countryCode.isValidFullNumber() == false) {
                    Toast.makeText(getContext() , "رقم الجوال غير صحيح"  ,Toast.LENGTH_SHORT).show();
                } else {
                    CompanyActivity.dialog.show();
                    Call<AddTeamResponse> call = jsonApi.addTeam("Bearer " + CompanyActivity.token, params);
                    call.enqueue(new Callback<AddTeamResponse>() {
                        @Override
                        public void onResponse(Call<AddTeamResponse> call, Response<AddTeamResponse> response) {
                            CompanyActivity.dialog.dismiss();
                            AddTeamResponse addTeamResponse = response.body();
                            if (addTeamResponse.getCode() == 200) {
                                CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                                Toast.makeText(getContext(), addTeamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                CompanySetTeamFragment fragment = new CompanySetTeamFragment();

                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frameLayout, fragment)
                                        .commit();
                            } else {
//                                Toast.makeText(getContext(), addTeamResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                            }
                        }

                        @Override
                        public void onFailure(Call<AddTeamResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}