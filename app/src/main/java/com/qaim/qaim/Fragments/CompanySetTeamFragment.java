package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.AddPainterAdapter;
import com.qaim.qaim.Classes.AddReviwerAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.TeamList.GetTeamListResponse;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanySetTeamFragment extends Fragment {

    private static final String ARG_PARAM1 = "type";
    private static final String ARG_PARAM2 = "name";
    private static final String ARG_PARAM3 = "des";

    RelativeLayout chooseArtist , chooseCheaker ;

    RecyclerView painterRecycle , reviewerRecycle ;
    AddPainterAdapter painterAdapter ;
    AddReviwerAdapter reviwerAdapter ;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    public CompanySetTeamFragment() {
        // Required empty public constructor
    }


    public static CompanySetTeamFragment newInstance(String param1 , String name ,String description) {
        CompanySetTeamFragment fragment = new CompanySetTeamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,param1 );
        args.putString(ARG_PARAM2,name );
        args.putString(ARG_PARAM3,description );
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_company_set_team, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        chooseArtist = v.findViewById(R.id.chooseArtist);
        chooseCheaker = v.findViewById(R.id.chooseReviwer);
        painterRecycle = v.findViewById(R.id.painterRecycle);
        reviewerRecycle = v.findViewById(R.id.reviwerRecycle);
        chooseArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddArtistOrCheakerFragment fragment = new  AddArtistOrCheakerFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.frameLayout , fragment)
                        .commit();
            }
        });
        chooseCheaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCheakerFragment fragment = new AddCheakerFragment();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.frameLayout , fragment)
                        .commit();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teamListAPICal();
    }


    public void teamListAPICal(){
        CompanyActivity.dialog.show();
        Call<GetTeamListResponse> call = jsonApi.getTeamList(LocaleHelper.getLanguage(getContext()), "Bearer "+ CompanyActivity.token);
        call.enqueue(new Callback<GetTeamListResponse>() {
            @Override
            public void onResponse(Call<GetTeamListResponse> call, Response<GetTeamListResponse> response) {
                CompanyActivity.dialog.dismiss();
                GetTeamListResponse getTeamListResponse = response.body();
                if (getTeamListResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    painterRecycle.setVisibility(View.VISIBLE);
                    reviewerRecycle.setVisibility(View.VISIBLE);
                    painterAdapter = new AddPainterAdapter(getTeamListResponse.getData().getPainters());
                    reviwerAdapter = new AddReviwerAdapter(getTeamListResponse.getData().getReviewers());
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    RecyclerView.LayoutManager lm2 = new LinearLayoutManager(getContext());
                    painterRecycle.setLayoutManager(lm);
                    painterRecycle.setHasFixedSize(true);
                    painterRecycle.setAdapter(painterAdapter);
                    reviewerRecycle.setLayoutManager(lm2);
                    reviewerRecycle.setHasFixedSize(true);
                    reviewerRecycle.setAdapter(reviwerAdapter);

                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<GetTeamListResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}