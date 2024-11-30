package com.qaim.qaim.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CustomNote;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Models.MyListDetails.MyListDetailsResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateShowUserResponse.NoteItem;
import com.qaim.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.qaim.qaim.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerNotesFragment extends Fragment {
    Retrofit retrofit;
    JsonApi jsonApi;
    RecyclerView recyclerView;
    TextView text;
    CustomNote adapter;
    String companyId;
    String previewerId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            companyId = getArguments().getString("company_id");
            previewerId = getArguments().getString("previewer_id");
            // Use the companyId as needed
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_previewer_notes, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {

        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        // inflate
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        text = view.findViewById(R.id.text);
        ImageButton imageBtn = view.findViewById(R.id.imageBtn);
        imageBtn.setOnClickListener(v -> getActivity().onBackPressed());

        if (companyId == null || companyId.isEmpty()) {
            text.setText(getString(R.string.previewer_notes));
            getPreviewerNotes();
        } else {
            text.setText(getString(R.string.company_notes));
            getCompanyNotes();
        }
    }

    private void getCompanyNotes() {
        MainActivity.dialog.show();
        Call<RealstateShowUserResponse> call = jsonApi.ShowRealstate("Bearer " + MainActivity.token, new ShowRealstateUserParams(Integer.parseInt(companyId)));
        call.enqueue(new Callback<RealstateShowUserResponse>() {
            @Override
            public void onResponse(Call<RealstateShowUserResponse> call, Response<RealstateShowUserResponse> response) {
                MainActivity.dialog.dismiss();
                RealstateShowUserResponse realstateShowUserResponse = response.body();
                if (response.code() == 200) {
                    if (realstateShowUserResponse.getData().getRow().getAttributes() != null && !realstateShowUserResponse.getData().getRow().getAttributes().isEmpty()) {
                        adapter = new CustomNote(realstateShowUserResponse.getData().getRow().getNotesList());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RealstateShowUserResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPreviewerNotes() {
        CompanyActivity.dialog.show();
        Call<MyListDetailsResponse> call = jsonApi.myListDetails("Bearer " + CompanyActivity.token ,new OrderListItemParams(Integer.parseInt(previewerId)));
        call.enqueue(new Callback<MyListDetailsResponse>() {
            @Override
            public void onResponse(Call<MyListDetailsResponse> call, Response<MyListDetailsResponse> response) {
                CompanyActivity.dialog.dismiss();
                MyListDetailsResponse realstateShowUserResponse = response.body();
                if (realstateShowUserResponse.getCode()== 200){
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    if (realstateShowUserResponse.getData().getRow().getRealEstate() != null) {
                        if (!realstateShowUserResponse.getData().getRow().getRealEstate().getAttributes().isEmpty() && realstateShowUserResponse.getData().getRow().getRealEstate().getAttributes() != null) {
                            List<NoteItem> reviewerNotesList = realstateShowUserResponse.getData().getRow().getPreviewerNotesList();
                            adapter = new CustomNote(reviewerNotesList == null ? new ArrayList<>() : reviewerNotesList);
                            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }else {
                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                }
            }

            @Override
            public void onFailure(Call<MyListDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}