package com.qaim.qaim.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Classes.EmpAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.MyListEmployee.MyListEmployeeResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeMainFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    RecyclerView recyclerView ;
    EmpAdapter adapter ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView noData ;
    public EmployeeMainFragment() {
        // Required empty public constructor
    }


    public static EmployeeMainFragment newInstance(String param1, String param2) {
        EmployeeMainFragment fragment = new EmployeeMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View v = inflater.inflate(R.layout.fragment_employee_main, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.offers_recycleView);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        noData = v.findViewById(R.id.noData);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
        EmployeeActivity.dialog.show();
        Call<MyListEmployeeResponse> call = jsonApi.myListEmployee(LocaleHelper.getLanguage(getContext()), "Bearer " + EmployeeActivity.token);
        call.enqueue(new Callback<MyListEmployeeResponse>() {
            @Override
            public void onResponse(Call<MyListEmployeeResponse> call, Response<MyListEmployeeResponse> response) {
                EmployeeActivity.dialog.dismiss();
                MyListEmployeeResponse empolyeeResponse = response.body();
                if (empolyeeResponse.getCode() == 200){
                    if (empolyeeResponse.getData().getRows() == null || empolyeeResponse.getData().getRows().getData().isEmpty() || empolyeeResponse.getData().getRows().getData() == null){
                        noData.setVisibility(View.VISIBLE);
                    }else {
                        noData.setVisibility(View.GONE);
                    }
                    if (empolyeeResponse.getData().getRows() != null) {
                        adapter = new EmpAdapter(empolyeeResponse.getData().getRows().getData());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyListEmployeeResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                CompanyActivity.dialog.dismiss();
            }
        });
    }
}