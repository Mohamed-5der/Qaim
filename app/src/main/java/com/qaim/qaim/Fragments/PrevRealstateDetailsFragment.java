package com.qaim.qaim.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.CustomPrevAttributed;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.GetAcceptedOrderPrev.AcceptedOrderPrevResponse;
import com.qaim.qaim.Models.GetRefusedOrders.RefusedOrderPrevResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.ShowPrevRealstate.ShowPrevRealstateResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PrevRealstateDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;
    CustomPrevAttributed adapter ;
    TextView tittle , cost , description , realStateType , realStateArea ,neighborhood , city ,additionalDetalis , address ;
    RecyclerView recyclerView ;
    public PrevRealstateDetailsFragment() {
        // Required empty public constructor
    }


    public static PrevRealstateDetailsFragment newInstance(int id) {
        PrevRealstateDetailsFragment fragment = new PrevRealstateDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_prev_realstate_details, container, false);

        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerOrdersFragment fragment = new  PreviewerOrdersFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , fragment).commit();
            }
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        tittle = v.findViewById(R.id.tittle);
        cost = v.findViewById(R.id.cost);
        description = v.findViewById(R.id.description);
        realStateType = v.findViewById(R.id.realStateType);
        realStateArea = v.findViewById(R.id.realStateArea);
        neighborhood = v.findViewById(R.id.neighborhood);
        city = v.findViewById(R.id.city);
//        additionalDetalis = v.findViewById(R.id.additionalDetalis);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycleView);
        Button accept = v.findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerActivity.dialog.show();
                Call<AcceptedOrderPrevResponse> call = jsonApi.accedptedOrdersPreviewer("Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
                call.enqueue(new Callback<AcceptedOrderPrevResponse>() {
                    @Override
                    public void onResponse(Call<AcceptedOrderPrevResponse> call, Response<AcceptedOrderPrevResponse> response) {
                        PreviewerActivity.dialog.dismiss();
                        AcceptedOrderPrevResponse prevResponse = response.body();
                        if (prevResponse.getCode() == 200){
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                            Toast.makeText(getActivity() , prevResponse.getMessage() , Toast.LENGTH_SHORT).show();
                            fragmentRecreate();
                        }else {
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
                        }
                    }

                    @Override
                    public void onFailure(Call<AcceptedOrderPrevResponse> call, Throwable t) {
                        Toast.makeText(getActivity() , t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Button rejectBtn = v.findViewById(R.id.rejectBtn);
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerActivity.dialog.show();
                Call<RefusedOrderPrevResponse> call = jsonApi.refusedOrdersPreviewer("Bearer " + PreviewerActivity.token ,new OrderListItemParams(id));
                call.enqueue(new Callback<RefusedOrderPrevResponse>() {
                    @Override
                    public void onResponse(Call<RefusedOrderPrevResponse> call, Response<RefusedOrderPrevResponse> response) {
                        PreviewerActivity.dialog.dismiss();
                        RefusedOrderPrevResponse prevResponse = response.body();
                        if (prevResponse.getCode() == 200){
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                            Toast.makeText(getActivity() ,prevResponse.getMessage() , Toast.LENGTH_SHORT).show();
                            fragmentRecreate();
                        }else {
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
                        }
                    }

                    @Override
                    public void onFailure(Call<RefusedOrderPrevResponse> call, Throwable t) {
                        Toast.makeText(getActivity() , t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return  v;
    }
    public void fragmentRecreate(){
        PreviewerActivity.dialog.show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,PreviewerOrdersFragment.newInstance())
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreviewerActivity.dialog.show();
        Call<ShowPrevRealstateResponse> call = jsonApi.showPreviewerRealstate("Bearer " + PreviewerActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<ShowPrevRealstateResponse>() {
            @Override
            public void onResponse(Call<ShowPrevRealstateResponse> call, Response<ShowPrevRealstateResponse> response) {
                PreviewerActivity.dialog.dismiss();
                ShowPrevRealstateResponse realstateShowUserResponse = response.body();
                if (realstateShowUserResponse.getCode()== 200){
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
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
                    realstateShowUserResponse.getData().getRow().getAttributes();
                    if (!realstateShowUserResponse.getData().getRow().getAttributes().isEmpty() && realstateShowUserResponse.getData().getRow().getAttributes() != null){
                        adapter = new CustomPrevAttributed(realstateShowUserResponse.getData().getRow().getAttributes());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    tittle.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getTitle()));
                    cost.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getCost()));
                    description.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getDescription()));
                    realStateType.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getType().getName()));
                    realStateArea.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getDistance()));
                    neighborhood.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getRegion().getName()));
                    city.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getCity().getName()));
//                    additionalDetalis.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getAddress()));
//                    address.setText(String.valueOf(realstateShowUserResponse.getData().getRow().getAddress()));

                }else {
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext() , realstateShowUserResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShowPrevRealstateResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }
}