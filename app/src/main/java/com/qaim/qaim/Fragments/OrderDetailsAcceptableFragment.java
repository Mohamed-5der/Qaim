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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CustomAttributed;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateShowUserResponse.FilesItem;
import com.qaim.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.qaim.qaim.Models.ReportCompleted.ReportCompletedtResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailsAcceptableFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private int id , hasReprot , hasCompleted;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    List<FilesItem> fileURL ;
    String cost ;
    TextView name , description , clientCode, clientName ;
    Button fileBtn , showReportBtn , projectCompletedBtn ;
    AppCompatButton sendFeedBackBtn , previewerNotesBtn ;
    ImageView clientImage ;
    String files ;
    RecyclerView recyclerView ;
    CustomAttributed adapter ;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;
    ImageView dialog_image ;
    LinearLayout previewerView;
    ScrollView parentRel;
    public OrderDetailsAcceptableFragment() {
        // Required empty public constructor
    }


    public static OrderDetailsAcceptableFragment newInstance(int id , int hasReprot , int hasCompleted) {
        OrderDetailsAcceptableFragment fragment = new OrderDetailsAcceptableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, hasReprot);
        args.putInt(ARG_PARAM3, hasCompleted);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            hasReprot = getArguments().getInt(ARG_PARAM2);
            hasCompleted = getArguments().getInt(ARG_PARAM3);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_details_acceptable, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersFragment fragment = new  OrdersFragment();
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
        // inflate
        name = v.findViewById(R.id.nameOfOrderDetails1);
        description = v.findViewById(R.id.descriptionOfOrderDetails1);
        recyclerView = v.findViewById(R.id.attributes);
        fileBtn = v.findViewById(R.id.endOrderBtn1) ;
        clientImage = v.findViewById(R.id.clientImage);
        clientCode = v.findViewById(R.id.clientCode);
        clientName = v.findViewById(R.id.clientName);
        showReportBtn = v.findViewById(R.id.showReport);
        projectCompletedBtn = v.findViewById(R.id.projectCompletBtn) ;
        imageSlider = v.findViewById(R.id.image_slider);
        previewerView = v.findViewById(R.id.previewerView);
        arrayList = new ArrayList<>();
        parentRel = v.findViewById(R.id.parentRel);
        sendFeedBackBtn = v.findViewById(R.id.sendFeedBackBtn);
        previewerNotesBtn = v.findViewById(R.id.previewerNotesBtn);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.dialog.show();
        Call<RealstateShowUserResponse> call = jsonApi.ShowRealstate("Bearer " + MainActivity.token  , new ShowRealstateUserParams(id));
        call.enqueue(new Callback<RealstateShowUserResponse>() {
            @Override
            public void onResponse(Call<RealstateShowUserResponse> call, Response<RealstateShowUserResponse> response) {
                MainActivity.dialog.dismiss();
                parentRel.setVisibility(View.VISIBLE);
                RealstateShowUserResponse realstateShowUserResponse = response.body();
                if (response.code() == 200) {
                    if ( realstateShowUserResponse.getData().getRow().getAttributes() != null &&  !realstateShowUserResponse.getData().getRow().getAttributes().isEmpty()){
                        adapter = new CustomAttributed(realstateShowUserResponse.getData().getRow().getAttributes());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                    sendFeedBackBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Create the fragment instance
                            SendFeedBackFragment sendFeedBackFragment = new SendFeedBackFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("company_id", realstateShowUserResponse.getData().getRow().getCompany().getId() + ""); // Replace with your actual company ID
                            sendFeedBackFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout, sendFeedBackFragment)
                                    .addToBackStack(null)
                                    .commit();
                        }
                    });

                    previewerNotesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PreviewerNotesFragment previewerNotesFragment =new PreviewerNotesFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("company_id", "" + id); // Replace with your actual company ID
                            previewerNotesFragment.setArguments(bundle);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout, previewerNotesFragment)
                                    .addToBackStack(null)
                                    .commit();



                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.frameLayout , previewerNotesFragment).addToBackStack(null).commit();                        }
                    });

                    if (!realstateShowUserResponse.getData().getRow().getFiles().isEmpty() && realstateShowUserResponse.getData().getRow().getFiles() != null){

                        for (int i = 0 ; i<realstateShowUserResponse.getData().getRow().getFiles().size() ; i++){
                            String  file = realstateShowUserResponse.getData().getRow().getFiles().get(i).getFile();
                            arrayList.add(new SlideModel(file , ScaleTypes.FIT));
                        }
                        imageSlider.setImageList(arrayList);
                        imageSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                String url = arrayList.get(i).getImageUrl();
                                Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.image_bottom_sheet_dialog);
                                dialog_image = dialog.findViewById(R.id.dialog_image);
                                Picasso.get().load(url).fit().into(dialog_image);
                                dialog.show();
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().getAttributes().windowAnimations = R.style.ImageDialogAnimation ;
                                dialog.getWindow().setGravity(Gravity.BOTTOM);

                            }
                        });
                    }


                    cost = realstateShowUserResponse.getData().getRow().getCost();
                    name.setText(realstateShowUserResponse.getData().getRow().getTitle());

                    description.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getDescription()));
                    if (realstateShowUserResponse.getData().getRow().getPreviewer() != null) {
                        String im = realstateShowUserResponse.getData().getRow().getPreviewer().getImage();
                        clientCode.setText(realstateShowUserResponse.getData().getRow().getPreviewer().getEmail());
                        clientName.setText(realstateShowUserResponse.getData().getRow().getPreviewer().getName());
                        previewerView.setVisibility(realstateShowUserResponse.getData().getRow().getShow_previewer() == 1 ? View.VISIBLE : View.GONE);
                        Picasso.get().load(im).fit().into(clientImage);

                    }
                    files = realstateShowUserResponse.getData().getRow().getPreviewer().getPrevDoc();

                }
            }

            @Override
            public void onFailure(Call<RealstateShowUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (files != null){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(files));
                    startActivity(browserIntent);
                }
            }
        });
        if (hasReprot == 1){
            showReportBtn.setVisibility(View.VISIBLE);
        }else {
            showReportBtn.setVisibility(View.GONE);
        }
        if (hasCompleted == 1){
            projectCompletedBtn.setVisibility(View.INVISIBLE);
        }else {
            projectCompletedBtn.setVisibility(View.VISIBLE);
        }



        showReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportsFragment reportsFragment = ReportsFragment.newInstance(id , hasReprot , hasCompleted);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , reportsFragment).addToBackStack(null).commit();
            }
        });

        projectCompletedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCompletedProjectAPI();
            }
        });

    }


    private void callCompletedProjectAPI(){
        Call<ReportCompletedtResponse> projectCompletedCall = jsonApi.completeProject("Bearer " + MainActivity.token  , new ShowRealstateUserParams(id));
        projectCompletedCall.enqueue(new Callback<ReportCompletedtResponse>() {
            @Override
            public void onResponse(Call<ReportCompletedtResponse> call, Response<ReportCompletedtResponse> response) {
                ReportCompletedtResponse reportCompletedtResponse = response.body();
                if (reportCompletedtResponse.getCode() == 200 && reportCompletedtResponse.getData().getRow() != null ) {
                    Toast.makeText(getContext() , reportCompletedtResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }else {
                    Toast.makeText(getContext() , reportCompletedtResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReportCompletedtResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}