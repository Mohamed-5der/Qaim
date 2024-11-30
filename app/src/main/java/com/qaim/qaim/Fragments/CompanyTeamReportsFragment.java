package com.qaim.qaim.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.MyListTeamReports.MyListTeamReportResponse;
import com.qaim.qaim.Models.MyListTeamReports.Painter;
import com.qaim.qaim.Models.MyListTeamReports.Previewer;
import com.qaim.qaim.Models.MyListTeamReports.Reviewer;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyTeamReportsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private int id;

    private String mParam1;
    private String mParam2;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    CardView previewerCard ,painterCard , reviwerCard ;
    ImageView prevImage , painterImage , reviwerImage ;
    TextView prevName , prevDescrition ,painterName , painterDescrition ,reviewerName , reviewerDescrition ,
    tittle1 , tittle2, tittle3;
    Button prevDownBtn , prevShowBtn ,painterDownBtn , painterShowBtn ,reviewerDownBtn , reviewerShowBtn ;
    TextView noData ;
    public CompanyTeamReportsFragment() {
        // Required empty public constructor
    }


    public static CompanyTeamReportsFragment newInstance(int id) {
        CompanyTeamReportsFragment fragment = new CompanyTeamReportsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_team_reports, container, false);
        
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            RealStateDetailsFragment chooseTeamFragment = RealStateDetailsFragment.newInstance(id);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout , chooseTeamFragment).commit();
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        inflateViews(v);
        noData = v.findViewById(R.id.noData);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CompanyActivity.dialog.show();
        Call<MyListTeamReportResponse> call = jsonApi.myListTeamReports("Bearer " + CompanyActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<MyListTeamReportResponse>() {
            @Override
            public void onResponse(Call<MyListTeamReportResponse> call, Response<MyListTeamReportResponse> response) {
                CompanyActivity.dialog.dismiss();
                MyListTeamReportResponse reportsResponse = response.body();
                if (reportsResponse.getCode() == 200) {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    previewerCard    painterCard   reviwerCard
//
//                    if (reportsResponse.getData().getRow().getPreviewer() == null && reportsResponse.getData().getRow().getReviewer() == null && reportsResponse.getData().getRow().getPainter() == null){
//                        noData.setVisibility(View.VISIBLE);
//                    }else {
//                        noData.setVisibility(View.GONE);
//                    }

                    if (reportsResponse.getData().getRow().getPreviewer() == null || reportsResponse.getData().getRow().getPreviewerFile() == null){
                        previewerCard.setVisibility(View.GONE);
                        tittle1.setVisibility(View.GONE);
                    }
                    else {
                        previewerCard.setVisibility(View.VISIBLE);
                        tittle1.setVisibility(View.VISIBLE);
                        if (reportsResponse.getData().getRow().getPreviewerHasReport() == 0){
                            prevShowBtn.setVisibility(View.GONE);
                        }
                        Previewer previewer = reportsResponse.getData().getRow().getPreviewer();
                        Picasso.get().load(previewer.getImage()).into(prevImage);
                        prevName.setText(previewer.getName());
                        prevDescrition.setText(previewer.getNotes());
                        prevShowBtn.setOnClickListener(view1 -> {
                            ShowPreviewerReportFragment fragment =ShowPreviewerReportFragment.newInstance(id);
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout ,fragment).commit();
                        });
                        prevDownBtn.setOnClickListener(view12 -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reportsResponse.getData().getRow().getPreviewerFile()));
                            startActivity(browserIntent);
                        });
                    }

                    if (reportsResponse.getData().getRow().getPainter() == null || reportsResponse.getData().getRow().getPainterFile() == null){
                        painterCard.setVisibility(View.GONE);
                        tittle2.setVisibility(View.GONE);
                    }
                    else {
                        painterCard.setVisibility(View.VISIBLE);
                        tittle2.setVisibility(View.VISIBLE);
                        Painter painter = reportsResponse.getData().getRow().getPainter();
                        Picasso.get().load(painter.getImage()).into(painterImage);
                        painterName.setText(painter.getName());
                        painterDescrition.setText(painter.getNotes());
                        painterShowBtn.setOnClickListener(view13 -> {

                        });
                        painterDownBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse((String) reportsResponse.getData().getRow().getPainterFile()));
                                startActivity(browserIntent);
                            }
                        });
                    }

                    if (reportsResponse.getData().getRow().getReviewer() == null || reportsResponse.getData().getRow().getReviewerFile() == null){
                        reviwerCard.setVisibility(View.GONE);
                        tittle3.setVisibility(View.GONE);
                    }
                    else {
                        reviwerCard.setVisibility(View.VISIBLE);
                        tittle3.setVisibility(View.VISIBLE);
                        Reviewer reviewer = reportsResponse.getData().getRow().getReviewer();
                        Picasso.get().load(reviewer.getImage()).into(reviwerImage);
                        reviewerName.setText(reviewer.getName());
                        reviewerDescrition.setText(reviewer.getNotes());
                        reviewerShowBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                        reviewerDownBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reportsResponse.getData().getRow().getReviewerFile()));
                                startActivity(browserIntent);
                            }
                        });
                    }
                    if (previewerCard.getVisibility() == View.GONE && painterCard.getVisibility() == View.GONE && reviwerCard.getVisibility() == View.GONE ){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }

                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<MyListTeamReportResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void inflateViews(View v ){
        previewerCard = v.findViewById(R.id.previewerCardView) ;
        painterCard = v.findViewById(R.id.artistCardView) ;
        reviwerCard = v.findViewById(R.id.cheakerCardView) ;
        prevImage = v.findViewById(R.id.previewerImage) ;
        painterImage = v.findViewById(R.id.artistImage) ;
        reviwerImage = v.findViewById(R.id.cheakerImage) ;
        prevName = v.findViewById(R.id.previewerName) ;
        prevDescrition = v.findViewById(R.id.previewerDescription) ;
        painterName = v.findViewById(R.id.artistName) ;
        painterDescrition = v.findViewById(R.id.artistDescription) ;
        reviewerName = v.findViewById(R.id.cheakerName) ;
        reviewerDescrition = v.findViewById(R.id.cheakerDescription) ;
        tittle1 = v.findViewById(R.id.tittle1) ;
        tittle2 = v.findViewById(R.id.tittle2) ;
        tittle3 = v.findViewById(R.id.tittle3) ;

        prevDownBtn = v.findViewById(R.id.previwerDownloadBtn);
        prevShowBtn = v.findViewById(R.id.previwerShowBtn);
        painterDownBtn = v.findViewById(R.id.artistDownloadBtn);
        painterShowBtn = v.findViewById(R.id.artistShowBtn);
        reviewerDownBtn = v.findViewById(R.id.cheakerDownloadBtn);
        reviewerShowBtn = v.findViewById(R.id.cheakerRateBtn);

    }
}