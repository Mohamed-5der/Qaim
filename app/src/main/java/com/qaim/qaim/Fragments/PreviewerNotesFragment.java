package com.qaim.qaim.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CustomAttributed;
import com.qaim.qaim.Classes.CustomNote;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerNotesFragment extends Fragment {
    Retrofit retrofit;
    JsonApi jsonApi;
    RecyclerView recyclerView;
    CustomNote adapter;
    String companyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            companyId = getArguments().getString("company_id");
            // Use the companyId as needed
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previewer_notes, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        // inflate
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton imageBtn =view.findViewById(R.id.imageBtn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        MainActivity.dialog.show();
        Call<RealstateShowUserResponse> call = jsonApi.ShowRealstate("Bearer " + MainActivity.token, new ShowRealstateUserParams(Integer.parseInt(companyId)));
        call.enqueue(new Callback<RealstateShowUserResponse>() {
            @Override
            public void onResponse(Call<RealstateShowUserResponse> call, Response<RealstateShowUserResponse> response) {
                MainActivity.dialog.dismiss();
                RealstateShowUserResponse realstateShowUserResponse = response.body();
                if (response.code() == 200) {
                    if (realstateShowUserResponse.getData().getRow().getAttributes() != null && !realstateShowUserResponse.getData().getRow().getAttributes().isEmpty()) {
                        adapter = new CustomNote(realstateShowUserResponse.getData().getRow().getNotesList());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RealstateShowUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}