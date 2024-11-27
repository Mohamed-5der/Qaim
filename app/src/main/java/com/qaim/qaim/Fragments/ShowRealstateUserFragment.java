package com.qaim.qaim.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CustomAttributed;
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

public class ShowRealstateUserFragment extends Fragment implements OnMapReadyCallback{
    private static final String ARG_PARAM1 = "param1";

    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    ImageSlider imageSlider ;
    ArrayList<SlideModel>arrayList ;
    CustomAttributed adapter ;
    TextView tittle , cost , description , realStateType , realStateArea ,neighborhood , city ,additionalDetalis , address ;
    RecyclerView recyclerView ;
    ImageView dialog_image ;

    MapView mapView ;
    LatLng latLng ;
    boolean isPermissionGranted;
    public ShowRealstateUserFragment() {
    }
    public static ShowRealstateUserFragment newInstance(int id) {
        ShowRealstateUserFragment fragment = new ShowRealstateUserFragment();
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
        View v = inflater.inflate(R.layout.fragment_show_realstate_user, container, false);

//        checkPermissions();
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment fragment = new  MainFragment();
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
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);

        description = v.findViewById(R.id.description);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycleView);

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
        MainActivity.dialog.show();
        Call<RealstateShowUserResponse> call = jsonApi.ShowRealstate("Bearer " + MainActivity.token , new ShowRealstateUserParams(id));
        call.enqueue(new Callback<RealstateShowUserResponse>() {
            @Override
            public void onResponse(Call<RealstateShowUserResponse> call, Response<RealstateShowUserResponse> response) {
                MainActivity.dialog.dismiss();
                RealstateShowUserResponse realstateShowUserResponse = response.body();
                if (realstateShowUserResponse.getCode()== 200){
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
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
                    realstateShowUserResponse.getData().getRow().getAttributes();
                    if (!realstateShowUserResponse.getData().getRow().getAttributes().isEmpty() && realstateShowUserResponse.getData().getRow().getAttributes() != null){
                        adapter = new CustomAttributed(realstateShowUserResponse.getData().getRow().getAttributes());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    description.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getDescription()));
                    latLng = new LatLng(Double.parseDouble(realstateShowUserResponse.getData().getRow().getLatitude()) , Double.parseDouble(realstateShowUserResponse.getData().getRow().getLongitude()));
                    mapView.getMapAsync(ShowRealstateUserFragment.this);

                }else {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                }
            }

            @Override
            public void onFailure(Call<RealstateShowUserResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });


    }


}