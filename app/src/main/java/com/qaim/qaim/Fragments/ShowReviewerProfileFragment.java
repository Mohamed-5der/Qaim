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


public class ShowReviewerProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;


    ImageView imageView ;
    TextView userName1 , userName2 , cost , phone ;

    public ShowReviewerProfileFragment() {
        // Required empty public constructor
    }


    public static ShowReviewerProfileFragment newInstance(String param1, String param2 , String param3 , int param4) {
        ShowReviewerProfileFragment fragment = new ShowReviewerProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putInt(ARG_PARAM4, param4);
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
            mParam4 = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_show_reviewer_profile, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewerFragment painterListFragment = ReviewerFragment.newInstance(mParam4);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout ,painterListFragment).commit();

            }
        });

        imageView = v.findViewById(R.id.image);
        userName1 = v.findViewById(R.id.firstUserName);
        userName2 = v.findViewById(R.id.secondUserName);
        cost = v.findViewById(R.id.cost);
        phone = v.findViewById(R.id.phone);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.get().load(mParam3).fit().error(getActivity().getDrawable(R.drawable.icon)).into(imageView);
        userName1.setText(mParam1);
        userName2.setText(mParam1);
        cost.setText(mParam2);
    }
}