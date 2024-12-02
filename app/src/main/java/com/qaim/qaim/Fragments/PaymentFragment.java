package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.R;

public class PaymentFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String mParam1;
    private int id;
    private String companyName;
    EditText costEditText ;
    Button payBtn ;
    TextView company ;

    public PaymentFragment() {
        // Required empty public constructor
    }


    public static PaymentFragment newInstance(String param1 , int id , String companyName) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, id);
        args.putString(ARG_PARAM3, companyName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            id = getArguments().getInt(ARG_PARAM2);
            companyName = getArguments().getString(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        company = v.findViewById(R.id.comp);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            OrderDetailsFragment fragment =OrderDetailsFragment.newInstance(id);
            getActivity().getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.frameLayout , fragment).commit();
        });

        company.setText( companyName+R.string.amoun_company_service);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        costEditText = v.findViewById(R.id.emailEditText);
        payBtn = v.findViewById(R.id.endOrderBtn);


        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        costEditText.setText(mParam1 + R.string.ryal);
        payBtn.setOnClickListener(view1 -> {
            PaymentLastStepFragment paymentLastStepFragment = PaymentLastStepFragment.newInstance(id ,mParam1 , companyName);
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frameLayout , paymentLastStepFragment).commit();
        });
    }
}