package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
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
import com.qaim.qaim.Classes.ReviewerListAdapter;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.TeamList.GetTeamListResponse;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewerFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private int mParam1;


    Retrofit retrofit ;
    JsonApi jsonApi ;
    RecyclerView recyclerView ;
    ReviewerListAdapter adapter ;

    public ReviewerFragment() {
        // Required empty public constructor
    }

    public static ReviewerFragment newInstance(int param1) {
        ReviewerFragment fragment = new ReviewerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reviewer, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(mParam1);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , chooseTeamFragment).commit();
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
        recyclerView = v.findViewById(R.id.PreviewerRecycleView);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CompanyActivity.dialog.show();
        Call<GetTeamListResponse> call = jsonApi.getTeamList("Bearer "+ CompanyActivity.token);
        call.enqueue(new Callback<GetTeamListResponse>() {
            @Override
            public void onResponse(Call<GetTeamListResponse> call, Response<GetTeamListResponse> response) {
                CompanyActivity.dialog.dismiss();
                GetTeamListResponse teamPreviwerListResponse = response.body();
                if (teamPreviwerListResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    adapter = new ReviewerListAdapter(teamPreviwerListResponse.getData().getReviewers() ,mParam1);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);

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