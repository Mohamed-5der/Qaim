package com.qaim.qaim.Fragments;

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
import com.qaim.qaim.Classes.PrevListParams;
import com.qaim.qaim.Classes.PreviwerListAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.TeamPreviewerList.TeamPreviewerListResponse;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviwerListFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int realStateID;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    RecyclerView recyclerView ;
    PreviwerListAdapter adapter ;
    public PreviwerListFragment() {
        // Required empty public constructor
    }


    public static PreviwerListFragment newInstance(int param1 , int realStateID) {
        PreviwerListFragment fragment = new PreviwerListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, realStateID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            realStateID = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_previwer_list, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(mParam1);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , chooseTeamFragment).commit();
        });

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

        Call<TeamPreviewerListResponse> call = jsonApi.getTeamPreviewersList(LocaleHelper.getLanguage(getContext()), "Bearer "+ CompanyActivity.token , new PrevListParams(realStateID));
        call.enqueue(new Callback<TeamPreviewerListResponse>() {
            @Override
            public void onResponse(Call<TeamPreviewerListResponse> call, Response<TeamPreviewerListResponse> response) {
                TeamPreviewerListResponse teamPreviwerListResponse = response.body();
                if (teamPreviwerListResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    adapter = new PreviwerListAdapter(teamPreviwerListResponse.getData().getPreviewers().getData() , mParam1 , realStateID);
                    RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<TeamPreviewerListResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}