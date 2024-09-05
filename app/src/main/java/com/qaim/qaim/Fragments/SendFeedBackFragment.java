package com.qaim.qaim.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.moyasar.android.sdk.generated.callback.OnClickListener;
import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Activities.OTPActivity;
import com.qaim.qaim.Activities.SplashScreen;
import com.qaim.qaim.Classes.OTPParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.OtpResponse.OtpResponse;
import com.qaim.qaim.Models.ReportCompleted.sendFeedBack.FeedBackResponse;
import com.qaim.qaim.Models.ReportCompleted.sendFeedBack.FeedbackRequest;
import com.qaim.qaim.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendFeedBackFragment extends Fragment {

 EditText et_subject,et_content;
 Spinner sp_txtChoose;
 Button btn_send;
 String selectItem;
 Retrofit retrofit ;
 JsonApi jsonApi;
 String companyId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            companyId = getArguments().getString("company_id");
            // Use the companyId as needed
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_send_feed_back, container, false);
        et_content = view.findViewById(R.id.et_content);
        et_subject = view.findViewById(R.id.et_sendFeed);
        sp_txtChoose = view.findViewById(R.id.my_spinner);
        btn_send = view.findViewById(R.id.btn_sendFeedBackBtn);
        ImageButton imageBtn =view.findViewById(R.id.imageBtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp_txtChoose.setAdapter(adapter);

        sp_txtChoose.setSelection(0);

        sp_txtChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sp_txtChoose.setSelection(0);
                selectItem = parent.getItemAtPosition(0).toString();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_content.getText().toString();
                String subject = et_subject.getText().toString();
                String selectItem = sp_txtChoose.getSelectedItem().toString().equals("قيم") ? "" : companyId;
                sendFeedBack(content,subject,selectItem);

            }
        });
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        return view ;
    }


    public void sendFeedBack(String content, String subject, String selectItem) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);
        FeedbackRequest feedbackRequest = new FeedbackRequest(content, subject, selectItem);

        // Make the API call
        Call<FeedBackResponse> call = jsonApi.sendFeedBack("Bearer " + SplashScreen.sToken.getString("token_key", ""), feedbackRequest);
        call.enqueue(new Callback<FeedBackResponse>() {
            @Override
            public void onResponse(Call<FeedBackResponse> call, Response<FeedBackResponse> response) {
                if (response.isSuccessful()) {
                    FeedBackResponse feedBackResponse = response.body();
                    if (feedBackResponse != null) {
                        Toast.makeText(getContext(), feedBackResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getContext(), "Response body is null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Server Error: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeedBackResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Request Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}


