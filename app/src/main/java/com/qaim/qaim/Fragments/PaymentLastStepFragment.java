package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentLastStepFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private int id;
    private static final String ARG_PARAM2 = "param2";
    private String cost;
    private static final String ARG_PARAM3 = "param3";
    private String companyName;
    Button payBtn ;
    Retrofit retrofit ;
    JsonApi jsonApi ;

    public PaymentLastStepFragment() {
        // Required empty public constructor
    }


    public static PaymentLastStepFragment newInstance(int id , String cost ,String companyName) {
        PaymentLastStepFragment fragment = new PaymentLastStepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putString(ARG_PARAM2, cost);
        args.putString(ARG_PARAM3, companyName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            cost = getArguments().getString(ARG_PARAM2);
            companyName = getArguments().getString(ARG_PARAM3);
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://qaimha.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonApi = retrofit.create(JsonApi.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment_last_step, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            PaymentFragment fragment = PaymentFragment.newInstance(cost , id , companyName);
            getActivity().getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.frameLayout , fragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        payBtn = v.findViewById(R.id.endOrderBtn);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        payBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onAcceptedBtnPressed(id);
//            }
//        });
    }

}