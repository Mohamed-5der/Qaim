package com.qaim.qaim.Fragments;

import static com.qaim.qaim.Activities.SplashScreen.sToken;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CompanyProjectRecycleViewAdapter;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealsteatListUserResponse.DataItem;
import com.qaim.qaim.Models.RealsteatListUserResponse.RealstateListUserResponse;
import com.qaim.qaim.R;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainFragment extends Fragment {

    RecyclerView recyclerView ;
    CompanyProjectRecycleViewAdapter adapter ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    String token ;
    AddRealstateFragmentListner listner ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ActivityCompat activity ;
    TextView noData ;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listner = (AddRealstateFragmentListner) context ;
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =inflater.inflate(R.layout.fragment_main, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.offers_recycleView) ;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        token = sToken.getString("token_key" , "");
        noData = v.findViewById(R.id.noData);

        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calListRealestate();
    }
    public interface AddRealstateFragmentListner {
        void onCardClick(int id );
    }
    public void calListRealestate(){
        MainActivity.dialog.show();
        Call<RealstateListUserResponse> listUserResponseCall = jsonApi.getAllRealstateList("Bearer " + token);
        listUserResponseCall.enqueue(new Callback<RealstateListUserResponse>() {
            @Override
            public void onResponse(Call<RealstateListUserResponse> call, Response<RealstateListUserResponse> response) {
                RealstateListUserResponse realstateListUserResponse = response.body();
                MainActivity.dialog.dismiss();
                if (response.code() == 200) {
                    if (realstateListUserResponse.getData().getRows().getData().isEmpty() || realstateListUserResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }

                    if (realstateListUserResponse.getData().getRows() != null) {

                        List<DataItem> dataItems = realstateListUserResponse.getData().getRows().getData();
                        adapter = new CompanyProjectRecycleViewAdapter(dataItems, new AddRealstateFragmentListner() {
                            @Override
                            public void onCardClick(int id) {
                                listner.onCardClick(id);
                            }
                        });
                        MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);
                    }

                }else {
                    MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<RealstateListUserResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                MainActivity.dialog.dismiss();
            }
        });
    }
}