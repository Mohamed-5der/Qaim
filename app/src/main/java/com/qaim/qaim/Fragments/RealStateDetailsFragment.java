package com.qaim.qaim.Fragments;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.AddNotesAndReportsParams;
import com.qaim.qaim.Classes.CustomComAttributesAdapter;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.AddNotesCompany.AddNotesAndReportsCompanyResponse;
import com.qaim.qaim.Models.CompanyFinishOrder.CompanyFinishOrderResponse;
import com.qaim.qaim.Models.MyListDetails.FilesItem;
import com.qaim.qaim.Models.MyListDetails.MyListDetailsResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
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

public class RealStateDetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "id";
    private int id , companyId , previwerId;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView tittle , cost , description , realStateType , realStateArea ,neighborhood , city
            ,additionalDetalis , address ;
    Button chooseTeamBtn ;
    RelativeLayout openFile ;
    String fileURL , previewerDoc ;
    Button commentsBtn, teamReportsBtn ,teamRateBtn , endBtn , addNotesBtn ;

    int hasCompleted , hasreport , hideAllBtn ;

    CustomComAttributesAdapter adapter ;
    RecyclerView recyclerView ;
    ArrayList<SlideModel> arrayList ;
    ImageSlider imageSlider ;
    TextView lblFileName ;
    MapView mapView ;
    LatLng latLng ;
    public RealStateDetailsFragment() {
        // Required empty public constructor
    }


    public static RealStateDetailsFragment newInstance(int id) {
        RealStateDetailsFragment fragment = new RealStateDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_real_state_details, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyProjectsFragment fragment = new  CompanyProjectsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        tittle = v.findViewById(R.id.tittle);
//        cost = v.findViewById(R.id.cost);
        description = v.findViewById(R.id.description);
//        realStateType = v.findViewById(R.id.realStateType);
//        realStateArea = v.findViewById(R.id.realStateArea);
//        neighborhood = v.findViewById(R.id.neighborhood);
        address = v.findViewById(R.id.addnotestxt);
//        city = v.findViewById(R.id.city);
//        additionalDetalis = v.findViewById(R.id.additionalDetalis);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();
        openFile = v.findViewById(R.id.openFile);
        chooseTeamBtn =v.findViewById(R.id.chooseTeamBtn);
        commentsBtn =v.findViewById(R.id.commentsBtn);
        teamReportsBtn =v.findViewById(R.id.teamReportsBtn);
        teamRateBtn =v.findViewById(R.id.writeRatesBtn);
        endBtn =v.findViewById(R.id.endProjectBtn);
        addNotesBtn =v.findViewById(R.id.addNotesBtn);
        recyclerView = v.findViewById(R.id.recycleView);
        lblFileName = v.findViewById(R.id.lblFileName);
        mapView =  v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        return v ;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 12));
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CompanyActivity.dialog.show();
        Call<MyListDetailsResponse> call = jsonApi.myListDetails("Bearer " + CompanyActivity.token ,new OrderListItemParams(id));
        call.enqueue(new Callback<MyListDetailsResponse>() {
            @Override
            public void onResponse(Call<MyListDetailsResponse> call, Response<MyListDetailsResponse> response) {
                CompanyActivity.dialog.dismiss();
                MyListDetailsResponse realstateShowUserResponse = response.body();
                if (realstateShowUserResponse.getCode()== 200){
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (realstateShowUserResponse.getData().getRow().getRealEstate() != null) {
                        if (!realstateShowUserResponse.getData().getRow().getRealEstate().getAttributes().isEmpty() && realstateShowUserResponse.getData().getRow().getRealEstate().getAttributes() != null){
                            adapter = new CustomComAttributesAdapter(realstateShowUserResponse.getData().getRow().getRealEstate().getAttributes());
                            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                        tittle.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getRealEstate().getTitle()));
                        description.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getRealEstate().getDescription()));
                        latLng = new LatLng(Double.parseDouble(realstateShowUserResponse.getData().getRow().getRealEstate().getLatitude()) , Double.parseDouble(realstateShowUserResponse.getData().getRow().getRealEstate().getLongitude()));
                        if (!realstateShowUserResponse.getData().getRow().getRealEstate().getFiles().isEmpty() && realstateShowUserResponse.getData().getRow().getRealEstate().getFiles() != null ){
                            List<FilesItem> files = realstateShowUserResponse.getData().getRow().getRealEstate().getFiles();
                            for (int i = 0 ; i<files.size() ; i++){
                                arrayList.add(new SlideModel(files.get(i).getFile() , ScaleTypes.FIT));
                            }
                            imageSlider.setImageList(arrayList);
                            imageSlider.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onItemSelected(int i) {
                                    String url = arrayList.get(i).getImageUrl();
                                    Dialog dialog = new Dialog(getActivity());
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.image_bottom_sheet_dialog);
                                    ImageView dialog_image = dialog.findViewById(R.id.dialog_image);
                                    Picasso.get().load(url).fit().into(dialog_image);
                                    dialog.show();
                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dialog.getWindow().getAttributes().windowAnimations = R.style.ImageDialogAnimation ;
                                    dialog.getWindow().setGravity(Gravity.BOTTOM);

                                }
                            });
                        }
                    }else {
                        tittle.setText("شركة");
                        latLng = new LatLng(0,0);
                    }

                    hideAllBtn = realstateShowUserResponse.getData().getRow().getHideAllBtns();
                    hasCompleted = realstateShowUserResponse.getData().getRow().getHasCompleted();
                    hasreport = realstateShowUserResponse.getData().getRow().getHasReport();
                    mapView.getMapAsync(RealStateDetailsFragment.this);
                    // set image view

                    if (hasCompleted == 0 && hasreport == 1){
                        endBtn.setVisibility(View.VISIBLE);
                    }else {
                        endBtn.setVisibility(View.INVISIBLE);
                    }

                    if (hideAllBtn == 1){
                        chooseTeamBtn.setVisibility(View.GONE);
                        addNotesBtn.setVisibility(View.GONE);
                        teamRateBtn.setVisibility(View.GONE);
                    }
                    fileURL = realstateShowUserResponse.getData().getRow().getDoc();
//                  lblFileName
                    if (realstateShowUserResponse.getData().getRow().getDocName().isEmpty() ||realstateShowUserResponse.getData().getRow().getDocName().equals("")||realstateShowUserResponse.getData().getRow().getDocName() == null ){
                        lblFileName.setText("عرض الملف");
                    }else {
                        lblFileName.setText(realstateShowUserResponse.getData().getRow().getDocName());
                    }
                    previewerDoc = realstateShowUserResponse.getData().getRow().getPreviewrDoc();

                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<MyListDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileURL));
                startActivity(browserIntent);
            }
        });
        teamRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyWriterReportsFragment writerReportsFragment = CompanyWriterReportsFragment.newInstance(id);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , writerReportsFragment).commit();
            }
        });
        teamReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTeamReportsBrnClick();
            }
        });
        chooseTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyChooseTeamFragment chooseTeamFragment = CompanyChooseTeamFragment.newInstance(id);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , chooseTeamFragment).commit();
            }
        });
        commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCommentBtnPressed();
            }
        });
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyActivity.dialog.show();
                Call<CompanyFinishOrderResponse> call2 = jsonApi.finishOrder("Bearer " + CompanyActivity.token , new OrderListItemParams(id));
                call2.enqueue(new Callback<CompanyFinishOrderResponse>() {
                    @Override
                    public void onResponse(Call<CompanyFinishOrderResponse> call, Response<CompanyFinishOrderResponse> response) {
                        CompanyActivity.dialog.dismiss();
                        CompanyFinishOrderResponse companyFinishOrderResponse = response.body();
                        if (companyFinishOrderResponse.getCode() == 200){
                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() , companyFinishOrderResponse.getMessage() , Toast.LENGTH_SHORT).show();
                            RealStateDetailsFragment fragment = newInstance(id);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
                        }else {
                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyFinishOrderResponse> call, Throwable t) {
                        Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        addNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyActivity.dialog.show();
                Call<AddNotesAndReportsCompanyResponse> call1 = jsonApi.addNotesAndReports("Bearer " + CompanyActivity.token ,
                        new AddNotesAndReportsParams(id , String.valueOf(address.getText()) , fileURL)
                        );
                call1.enqueue(new Callback<AddNotesAndReportsCompanyResponse>() {
                    @Override
                    public void onResponse(Call<AddNotesAndReportsCompanyResponse> call, Response<AddNotesAndReportsCompanyResponse> response) {
                        CompanyActivity.dialog.dismiss();
                        AddNotesAndReportsCompanyResponse reportsCompanyResponse = response.body();
                        if (reportsCompanyResponse.getCode() == 200) {
                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() , reportsCompanyResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }else {
                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() , reportsCompanyResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddNotesAndReportsCompanyResponse> call, Throwable t) {
                        Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void onCommentBtnPressed(){
        CompanyCommentsFragment fragment = CompanyCommentsFragment.newInstance(id);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
    }

    public void onTeamReportsBrnClick(){
        CompanyTeamReportsFragment fragment = CompanyTeamReportsFragment.newInstance(id);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , fragment).commit();
    }


}
