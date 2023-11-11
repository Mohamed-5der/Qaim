package com.qaim.qaim.Fragments;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Classes.AddListEmployeeCommentsParams;
import com.qaim.qaim.Classes.CommentsEmployeeAdapter;
import com.qaim.qaim.Classes.InfoParams;
import com.qaim.qaim.Models.EmployeeAddComments.EmployeeAddCommentsResponse;
import com.qaim.qaim.Models.EmployeeComments.EmployeeCommentsResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeCommentsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private int id;
    RecyclerView recyclerView ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CommentsEmployeeAdapter adapter ;
    ImageButton btn ;
    EditText comment ;
    public EmployeeCommentsFragment() {
        // Required empty public constructor
    }


    public static EmployeeCommentsFragment newInstance(int id) {
        EmployeeCommentsFragment fragment = new EmployeeCommentsFragment();
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
        View v = inflater.inflate(R.layout.fragment_employee_comments, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeMainFragment fragment = new EmployeeMainFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout , fragment).commit();
            }
        });
        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.offers_recycleView) ;
        btn = v.findViewById(R.id.sendBtn);
        comment = v.findViewById(R.id.addComment);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getComments();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });

    }

    public void addComment(){
        EmployeeActivity.dialog.show();
        Call<EmployeeAddCommentsResponse> call = jsonApi.myListEmployeeAddComments("Bearer " + EmployeeActivity.token , new AddListEmployeeCommentsParams(id ,
                String.valueOf(comment.getText())
        ));
        call.enqueue(new Callback<EmployeeAddCommentsResponse>() {
            @Override
            public void onResponse(Call<EmployeeAddCommentsResponse> call, Response<EmployeeAddCommentsResponse> response) {
                EmployeeActivity.dialog.dismiss();
                EmployeeAddCommentsResponse myListPrevCommentsResponse = response.body();
                if (myListPrevCommentsResponse != null){
                    if (myListPrevCommentsResponse.getCode() == 200) {
                        adapter.notifyDataSetChanged();
                    }
                    comment.setText("");
                    getComments();
                }

            }
            @Override
            public void onFailure(Call<EmployeeAddCommentsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getComments(){
        EmployeeActivity.dialog.show();
        Call<EmployeeCommentsResponse> call = jsonApi.myListEmployeeComments("Bearer " + EmployeeActivity.token , new InfoParams(id));
        call.enqueue(new Callback<EmployeeCommentsResponse>() {
            @Override
            public void onResponse(Call<EmployeeCommentsResponse> call, Response<EmployeeCommentsResponse> response) {
                EmployeeActivity.dialog.dismiss();
                EmployeeCommentsResponse myListPrevCommentsResponse = response.body();
                if (myListPrevCommentsResponse.getCode() == 200) {
                    adapter = new CommentsEmployeeAdapter(myListPrevCommentsResponse.getData().getComments());
                    LinearLayoutManager lm = new LinearLayoutManager(getContext());
                    lm.setReverseLayout(true);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<EmployeeCommentsResponse> call, Throwable t) {

            }
        });
    }
}