package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

public class ShowPreviewerProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM20 = "param20";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int order_id ;
    private String region ;
    private String year ;
    private String filed ;
    private String extra ;
    private String rate ;
    private int real_State_ID ;
    ImageView imageView ;
    TextView userName1 , userName2 , cost , tvRegions , tvYears , tvFiled , tvExtra, lblRate ;

    public ShowPreviewerProfileFragment() {
        // Required empty public constructor
    }

    public static ShowPreviewerProfileFragment newInstance(String param1, String param2 , String param3 , int order_id , String region , String years , String filed , String extra , int real_State_ID, String rate) {
        ShowPreviewerProfileFragment fragment = new ShowPreviewerProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, order_id);
        args.putString(ARG_PARAM5, region);
        args.putString(ARG_PARAM6, years);
        args.putString(ARG_PARAM7, filed);
        args.putString(ARG_PARAM8, extra);
        args.putInt(ARG_PARAM9, real_State_ID);
        args.putString(ARG_PARAM20, rate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            order_id = getArguments().getInt(ARG_PARAM4);
            region = getArguments().getString(ARG_PARAM5);
            year = getArguments().getString(ARG_PARAM6);
            filed = getArguments().getString(ARG_PARAM7);
            extra = getArguments().getString(ARG_PARAM8);
            rate = getArguments().getString(ARG_PARAM20);
            real_State_ID = getArguments().getInt(ARG_PARAM9);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_previewer_profile, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviwerListFragment painterListFragment = PreviwerListFragment.newInstance(order_id ,real_State_ID);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,painterListFragment).commit();

            }
        });
        imageView = v.findViewById(R.id.image);
        userName1 = v.findViewById(R.id.firstUserName);
        userName2 = v.findViewById(R.id.secondUserName);
        cost = v.findViewById(R.id.cost);
        tvYears = v.findViewById(R.id.experiance);
        tvFiled = v.findViewById(R.id.experienceField);
        tvRegions = v.findViewById(R.id.region1);
        tvExtra = v.findViewById(R.id.info);
        lblRate = v.findViewById(R.id.lbl_rate);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(mParam3).fit().error(getActivity().getDrawable(R.drawable.icon)).into(imageView);
        userName1.setText(mParam1);
        userName2.setText(mParam1);
        cost.setText(mParam2 + getString(R.string.ryal));
        tvYears.setText(year + getString(R.string.year));
        tvFiled.setText(filed);
        tvRegions.setText(region);
        tvExtra.setText(extra);
        lblRate.setText(rate);
    }
}