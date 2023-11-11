package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.AssignTeamParamsAll;
import com.qaim.qaim.Models.AssignTeam.AssingTeamResponse;
import com.qaim.qaim.Models.AssignTeam.Painter;
import com.qaim.qaim.Models.AssignTeam.Previewer;
import com.qaim.qaim.Models.AssignTeam.RealEstate;
import com.qaim.qaim.Models.AssignTeam.Reviewer;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyChooseTeamFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int preId;
    private String mParam2;


    RelativeLayout chooseArtist , chooseCheaker , choosePreviwer ;
    RelativeLayout prevRel , painterRel , revRel ;
    ImageView prevImage , painterImage , revImage ;
    ImageView addPrevImage , addArtistImage ,addReviwerImage ;
    TextView prevName ,prevDescription , painterName ,painterDescription , revName ,revDescription ;

    RecyclerView painterRecycle , reviewerRecycle , previwerRecycle ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    Previewer previewer ;
    Reviewer reviewer ;
    Painter painter;
    int realStateID;
    RealEstate realState;

    public CompanyChooseTeamFragment() {
        // Required empty public constructor
    }


    public static CompanyChooseTeamFragment newInstance(int preId) {
        CompanyChooseTeamFragment fragment = new CompanyChooseTeamFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, preId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            preId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_choose_team, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealStateDetailsFragment chooseTeamFragment = RealStateDetailsFragment.newInstance(preId);
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , chooseTeamFragment).commit();
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
        chooseArtist = v.findViewById(R.id.chooseArtist);
        chooseCheaker = v.findViewById(R.id.chooseReviwer);
        choosePreviwer = v.findViewById(R.id.choosePre);

        // inflate cards
        prevRel = v.findViewById(R.id.relPrevDetails);
        prevImage = v.findViewById(R.id.preivImage);
        prevName = v.findViewById(R.id.prevName);
        prevDescription = v.findViewById(R.id.prevDescription);

        painterRel = v.findViewById(R.id.relArtisiDetails);
        painterImage = v.findViewById(R.id.artistImage);
        painterName = v.findViewById(R.id.artistName);
        painterDescription = v.findViewById(R.id.artistDescription);

        revRel = v.findViewById(R.id.relrevDetails);
        revImage = v.findViewById(R.id.revImage);
        revName = v.findViewById(R.id.revName);
        revDescription = v.findViewById(R.id.revDescription);


        addPrevImage = v.findViewById(R.id.addPrevImage);
        addArtistImage = v.findViewById(R.id.addArtistImage);
        addReviwerImage = v.findViewById(R.id.addReviwerImage);


        chooseArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PainterListFragment fragment = PainterListFragment.newInstance(preId);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout , fragment)
                        .commit();
            }
        });
        chooseCheaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewerFragment fragment = ReviewerFragment.newInstance(preId);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout , fragment)
                        .commit();
            }
        });

        choosePreviwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (realState != null) {
                    PreviwerListFragment fragment = PreviwerListFragment.newInstance(preId , realStateID);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout , fragment)
                            .commit();
                }
            }
        });

        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        teamListAPICal();

    }

    public void teamListAPICal(){
        CompanyActivity.dialog.show();
        Call<AssingTeamResponse> call = jsonApi.assignTeam("Bearer " + CompanyActivity.token ,new AssignTeamParamsAll(preId));
        call.enqueue(new Callback<AssingTeamResponse>() {
            @Override
            public void onResponse(Call<AssingTeamResponse> call, Response<AssingTeamResponse> response) {
                CompanyActivity.dialog.dismiss();
                AssingTeamResponse assingTeamResponse = response.body();
                if (assingTeamResponse.getCode() == 200){
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (assingTeamResponse.getData().getRow().getRealEstate() != null) {
                        realState = assingTeamResponse.getData().getRow().getRealEstate() ;
                        realStateID = assingTeamResponse.getData().getRow().getRealEstate().getId();
                    }
                    previewer = assingTeamResponse.getData().getRow().getPreviewer();
                    painter =assingTeamResponse.getData().getRow().getPainter();
                    reviewer = assingTeamResponse.getData().getRow().getReviewer();
                    onPrevCall();
                    onRevCall();
                    onPainterCall();
                }else {
                    Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AssingTeamResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPrevCall(){
        if (previewer != null){
            prevRel.setVisibility(View.VISIBLE);
            addPrevImage.setVisibility(View.GONE);
            Picasso.get().load(previewer.getImage()).fit().error(getActivity().getDrawable(R.drawable.icon)).into(prevImage);
            prevName.setText(String.valueOf(previewer.getName()));
            prevDescription.setText(String.valueOf(previewer.getNotes()));
        }else {
            prevRel.setVisibility(View.GONE);
            addPrevImage.setVisibility(View.VISIBLE);
        }
    }
    public void onPainterCall(){
        if (painter != null){
            painterRel.setVisibility(View.VISIBLE);
            addArtistImage.setVisibility(View.GONE);
            Picasso.get().load(painter.getImage()).fit().error(getActivity().getDrawable(R.drawable.icon)).into(painterImage);
            painterName.setText(String.valueOf(painter.getName()));
            painterDescription.setText(String.valueOf(painter.getNotes()));
        }else {
            painterRel.setVisibility(View.GONE);
            addArtistImage.setVisibility(View.VISIBLE);
        }
    }
    public void onRevCall(){
        if (reviewer != null){
            revRel.setVisibility(View.VISIBLE);
            addReviwerImage.setVisibility(View.GONE);
            Picasso.get().load(reviewer.getImage()).fit().error(getActivity().getDrawable(R.drawable.icon)).into(revImage);
            revName.setText(String.valueOf(reviewer.getName()));
            revDescription.setText(String.valueOf(reviewer.getNotes()));
        }else {
            revRel.setVisibility(View.GONE);
            addReviwerImage.setVisibility(View.VISIBLE);
        }
    }

}