//package com.qaim.qaim.Fragments;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.qaim.qaim.Activities.CompanyActivity;
//import com.qaim.qaim.Classes.OrderListItemParams;
//import com.qaim.qaim.Classes.TeamReporysAdapter;
//import com.qaim.qaim.Models.MyListTeamReports.MyListTeamReportsResponse;
//import com.qaim.qaim.Models.Networks.JsonApi;
//import com.qaim.qaim.R;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class TeamReportsFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private int id;
//    RecyclerView recyclerView ;
//    TeamReporysAdapter adapter ;
//    Retrofit retrofit ;
//    JsonApi jsonApi ;
//
//
//
//    public TeamReportsFragment() {
//        // Required empty public constructor
//    }
//
//    public static TeamReportsFragment newInstance(int param1) {
//        TeamReportsFragment fragment = new TeamReportsFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM1, param1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            id = getArguments().getInt(ARG_PARAM1);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_team_reports, container, false);
//        recyclerView = v.findViewById(R.id.PreviewerRecycleView);
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://qaim.app")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        jsonApi = retrofit.create(JsonApi.class);
//        return v;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Call<MyListTeamReportsResponse> call = jsonApi.myListTeamReports("Bearer " + CompanyActivity.token , new OrderListItemParams(id));
//        call.enqueue(new Callback<MyListTeamReportsResponse>() {
//            @Override
//            public void onResponse(Call<MyListTeamReportsResponse> call, Response<MyListTeamReportsResponse> response) {
//                MyListTeamReportsResponse reportsResponse = response.body();
//                if (reportsResponse.getCode() == 200) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyListTeamReportsResponse> call, Throwable t) {
//
//            }
//        });
//    }
//}