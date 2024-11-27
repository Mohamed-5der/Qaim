package com.qaim.qaim.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.AddComentsPreviewerParams;
import com.qaim.qaim.Classes.CommentsAdapter;
import com.qaim.qaim.Classes.InfoParams;
import com.qaim.qaim.Models.AddCommentsPreviewer.MyListPrevCommentsAddResponse;
import com.qaim.qaim.Models.AddListComments.AddCommentsListResponse;
import com.qaim.qaim.Models.MyListCommentsPreviewer.MyListPrevCommentsResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PreviewerCommentsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private int id;
    RecyclerView recyclerView ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CommentsAdapter adapter ;
    ImageButton btn ;
    ImageButton sendFile ;
    Uri fileUri;

    EditText comment ;
    public PreviewerCommentsFragment() {
        // Required empty public constructor
    }


    public static PreviewerCommentsFragment newInstance(int id) {
        PreviewerCommentsFragment fragment = new PreviewerCommentsFragment();
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
        View v = inflater.inflate(R.layout.fragment_previewer_comments, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment ();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
            }
        });
        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        recyclerView = v.findViewById(R.id.offers_recycleView) ;
        btn = v.findViewById(R.id.sendBtn);
        sendFile = v.findViewById(R.id.sendFile);
        comment = v.findViewById(R.id.addComment);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
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

        sendFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize intent
                Intent intent=new Intent(Intent.ACTION_PICK);
                // set type
                intent.setType("image/*");
                // start activity result
                startActivityForResult(Intent.createChooser(intent,getString(R.string.select_image)),100);
            }
        });
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public void addFile(){
        MultipartBody.Part body = null;

        if (fileUri != null && getPath(fileUri) != null) {
            File file = new File(getPath(fileUri));
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getContext().getContentResolver().getType(fileUri)),
                            file

                    );
            body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("name", RequestBody.create(MultipartBody.FORM , "" + id));

        CompanyActivity.dialog.show();
        Call<MyListPrevCommentsAddResponse> call = jsonApi.myListCommentsAddPreviewer("Bearer " + PreviewerActivity.token , map, body);
        call.enqueue(new Callback<MyListPrevCommentsAddResponse>() {
            @Override
            public void onResponse(Call<MyListPrevCommentsAddResponse> call, Response<MyListPrevCommentsAddResponse> response) {
                PreviewerActivity.dialog.dismiss();
                MyListPrevCommentsAddResponse myListPrevCommentsResponse = response.body();
                if (myListPrevCommentsResponse != null){
                    if (myListPrevCommentsResponse.getCode() == 200) {
                        adapter.notifyDataSetChanged();
                    }
                    comment.setText("");
                    getComments();
                }

            }
            @Override
            public void onFailure(Call<MyListPrevCommentsAddResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addComment(){
        PreviewerActivity.dialog.show();
        Call<MyListPrevCommentsAddResponse> call = jsonApi.myListCommentsAddPreviewer("Bearer " + PreviewerActivity.token , new AddComentsPreviewerParams(id ,
                String.valueOf(comment.getText())
                ));
        call.enqueue(new Callback<MyListPrevCommentsAddResponse>() {
            @Override
            public void onResponse(Call<MyListPrevCommentsAddResponse> call, Response<MyListPrevCommentsAddResponse> response) {
                PreviewerActivity.dialog.dismiss();
                MyListPrevCommentsAddResponse myListPrevCommentsResponse = response.body();
                if (myListPrevCommentsResponse != null){
                    if (myListPrevCommentsResponse.getCode() == 200) {
                        adapter.notifyDataSetChanged();
                    }
                    comment.setText("");
                    getComments();
                }

            }
            @Override
            public void onFailure(Call<MyListPrevCommentsAddResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getComments(){
        PreviewerActivity.dialog.show();
        Call<MyListPrevCommentsResponse> call = jsonApi.myListCommentsPreviewer("Bearer " + PreviewerActivity.token , new InfoParams(id));
        call.enqueue(new Callback<MyListPrevCommentsResponse>() {
            @Override
            public void onResponse(Call<MyListPrevCommentsResponse> call, Response<MyListPrevCommentsResponse> response) {
                PreviewerActivity.dialog.dismiss();
                MyListPrevCommentsResponse myListPrevCommentsResponse = response.body();
                if (myListPrevCommentsResponse.getCode() == 200) {
                    adapter = new CommentsAdapter(myListPrevCommentsResponse.getData().getComments());
                    LinearLayoutManager  lm = new LinearLayoutManager(getContext());
                    lm.setReverseLayout(true);
                    lm.setStackFromEnd(true);
                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                }

            }

            @Override
            public void onFailure(Call<MyListPrevCommentsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK)
        {
            fileUri = data.getData();

            if(fileUri != null){
                addFile();
            }
        }
    }

}