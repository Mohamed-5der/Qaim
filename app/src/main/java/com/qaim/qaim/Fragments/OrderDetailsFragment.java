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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Activities.MoyasrPaymenyActivity;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RefusedOrderUserResponse.RefusedOrderUserResponse;
import com.qaim.qaim.Models.ShowOrderUserResponse.ShowOrderUserResponse;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OrderDetailsFragment extends Fragment {


    private static final String ARG_NAME_ORDER = "param1";
    Button accepted , rejected ;
    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    String companyName , compantDescription ;
    int orderId ;
    RelativeLayout openFile ;
    String fileURL ;
    String cost ;
    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public static OrderDetailsFragment newInstance(int  id) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NAME_ORDER, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_NAME_ORDER);
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://qaim.app")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonApi = retrofit.create(JsonApi.class);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_order_details, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OffersFragment fragment = new  OffersFragment();
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
        TextView name , description ;
        // inflate
        name = v.findViewById(R.id.nameOfOrderDetails);
        description = v.findViewById(R.id.descriptionOfOrderDetails);
        accepted = v.findViewById(R.id.endOrderBtn) ;
        openFile = v.findViewById(R.id.openFile) ;
        rejected = v.findViewById(R.id.editOrderBtn) ;
        MainActivity.dialog.show();
        Call<ShowOrderUserResponse> call = jsonApi.showOrders("Bearer " + MainActivity.token  , new OrderListItemParams(id));
        call.enqueue(new Callback<ShowOrderUserResponse>() {
            @Override
            public void onResponse(Call<ShowOrderUserResponse> call, Response<ShowOrderUserResponse> response) {
                MainActivity.dialog.dismiss();
                ShowOrderUserResponse showOrderUserResponse = response.body();
                if (response.code() == 200) {
                    orderId = showOrderUserResponse.getData().getRow().getId();
                    fileURL = showOrderUserResponse.getData().getRow().getDoc();
                    cost = showOrderUserResponse.getData().getRow().getCost();
                    companyName = showOrderUserResponse.getData().getRow().getCompany().getName();
                    compantDescription = showOrderUserResponse.getData().getRow().getRealEstate().getDescription();
                    name.setText(companyName);
                    description.setText(compantDescription);
                }
            }

            @Override
            public void onFailure(Call<ShowOrderUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.accepted_bottom_sheet_dialog);
                Button cancel = dialog.findViewById(R.id.rejectBtn);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                Button confirm = dialog.findViewById(R.id.sendNotes);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent i = new Intent(getActivity() , MoyasrPaymenyActivity.class);
                        i.putExtra("cost" , cost);
                        i.putExtra("id" , id);
                        i.putExtra("company" , companyName);
                        getActivity().startActivity(i);
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.canceled_bottom_sheet_dialog);
                Button cancel = dialog.findViewById(R.id.rejectBtn);
                Button rejected = dialog.findViewById(R.id.sendNotes);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                rejected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        onRejectedBtnPressed(orderId);
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fileURL));
                startActivity(browserIntent);
            }
        });
    }
    public void onRejectedBtnPressed(int id){
        MainActivity.dialog.show();
        Call<RefusedOrderUserResponse> call = jsonApi.getRefusedOrders("Bearer " +MainActivity.token, new OrderListItemParams(id));
        call.enqueue(new Callback<RefusedOrderUserResponse>() {
            @Override
            public void onResponse(Call<RefusedOrderUserResponse> call, Response<RefusedOrderUserResponse> response) {
                RefusedOrderUserResponse refusedOrderUserResponse = response.body();
                if (response.code() == 200) {
                    MainActivity.dialog.dismiss();
                    Toast.makeText(getContext(), "the offer is Rejected", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RefusedOrderUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}