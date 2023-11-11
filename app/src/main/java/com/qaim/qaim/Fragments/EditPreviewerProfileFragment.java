package com.qaim.qaim.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.GetProviewerProfile.PreviewerProfileResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.Models.UpdatePreviewerProfile.UpdatePreviewerProfileResponse;
import com.qaim.qaim.R;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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


public class EditPreviewerProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    EditText proflieName ,proflieEmail , profliePhone , profliePassword , addInfo
            , addFees , infoAboutMe , experiance , yearsOfWork ;
    CountryCodePicker contryCod ;
    Button confirm ;
    Spinner citySpinner ,region1Spinner ,region2Spinner , region3Spinner;
    TextView userName ;
    ImageView profileImage ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCityAdapter cityAdapter ;
    int cityId ;
    CustomRegionAdapter regionAdapter;
    int regionId_1;
    int regionId_2;
    int regionId_13;

    Uri fileUri;
    ImageView imageView ;
    String imageURL ;
    Bitmap bitmap ;
    PreviewerProfileResponse profileResponse;


    public EditPreviewerProfileFragment() {
        // Required empty public constructor
    }


    public static EditPreviewerProfileFragment newInstance(String param1, String param2) {
        EditPreviewerProfileFragment fragment = new EditPreviewerProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_previewer_profile, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment ();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
            }
        });
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        imageView = v.findViewById(R.id.proflieImage);
        userName = v.findViewById(R.id.userName);
        proflieName = v.findViewById(R.id.EditProfileNameEditText);
        proflieEmail = v.findViewById(R.id.EditProfileEmailEditText);
        profliePhone = v.findViewById(R.id.phoneEditText);
        profliePassword = v.findViewById(R.id.passwordEditText);
        addInfo = v.findViewById(R.id.addInfo);
        addFees = v.findViewById(R.id.addFees);
        infoAboutMe = v.findViewById(R.id.addInfoAboutMe);
        experiance = v.findViewById(R.id.experienceField);
        yearsOfWork = v.findViewById(R.id.yearsOfWork);
        citySpinner = v.findViewById(R.id.citySpinner);
        region1Spinner  = v.findViewById(R.id.Area1spinner);
        region2Spinner = v.findViewById(R.id.Area2spinner);
        region3Spinner= v.findViewById(R.id.Area3spinner);
        confirm= v.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (proflieName.isEnabled()){
                    onEditProfileCallAPI();

                }else {
                    enable();

                }
            }
        });
        contryCod = v.findViewById(R.id.countryCode);
//        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
//        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
//                if (response.code() == 200) {
//                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
//                    citySpinner.setAdapter(cityAdapter);
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
//                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
//            }
//        });
        callProffileAPI();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallory();
            }
        });

    }

    public void callProffileAPI(){
        PreviewerActivity.dialog.show();
        Call<PreviewerProfileResponse> call = jsonApi.getPreviewerProfile("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<PreviewerProfileResponse>() {
            @Override
            public void onResponse(Call<PreviewerProfileResponse> call, Response<PreviewerProfileResponse> response) {
               PreviewerActivity.dialog.dismiss();
                PreviewerProfileResponse previewerProfileResponse = response.body();
                if (previewerProfileResponse.getCode() == 200) {
//                    Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
//                    citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
//                        @Override
//                        public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
//                            if (response.code() == 200) {
//                                cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
//                                citySpinner.setAdapter(cityAdapter);
//                            }
//
//                        }
//
//                        @Override
//                        public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
//                            Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
//                        }
//                    });
                    isEnable();
                    userName.setText(previewerProfileResponse.getData().getPreviewer().getName());
                    proflieName.setText(previewerProfileResponse.getData().getPreviewer().getName());
                    proflieEmail.setText(previewerProfileResponse.getData().getPreviewer().getEmail());
                    profliePhone.setText(previewerProfileResponse.getData().getPreviewer().getPhone());
                    addInfo.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getIdentity()));
                    addFees.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getCost()));
                    infoAboutMe.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getAbout()));
                    experiance.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getExperience()));
                    yearsOfWork.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getYears()));

                    profileResponse = previewerProfileResponse;
                    onEditProfileCallAPI();

                    if (previewerProfileResponse.getData().getPreviewer().getCountryCode() != null){
                        contryCod.setCountryForNameCode(String.valueOf(previewerProfileResponse.getData().getPreviewer().getCountryCode()));
                    }
                    Picasso.get().load(previewerProfileResponse.getData().getPreviewer().getImage()).fit().error(R.drawable.icon).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<PreviewerProfileResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void isEnable(){
        userName.setEnabled(false);
        proflieName.setEnabled(false);
        proflieEmail.setEnabled(false);
        profliePhone.setEnabled(false);
        addInfo.setEnabled(false);
        addFees.setEnabled(false);
        infoAboutMe.setEnabled(false);
        experiance.setEnabled(false);
        yearsOfWork.setEnabled(false);
        citySpinner.setEnabled(false);
        region1Spinner.setEnabled(false);
        region2Spinner.setEnabled(false);
        region3Spinner.setEnabled(false);
        contryCod.setEnabled(false);
        imageView.setEnabled(false);
    }

    public void enable(){
        userName.setEnabled(true);
        proflieName.setEnabled(true);
        proflieEmail.setEnabled(true);
        profliePhone.setEnabled(true);
        addInfo.setEnabled(true);
        addFees.setEnabled(true);
        infoAboutMe.setEnabled(true);
        experiance.setEnabled(true);
        yearsOfWork.setEnabled(true);
        citySpinner.setEnabled(true);
        region1Spinner.setEnabled(true);
        region2Spinner.setEnabled(true);
        region3Spinner.setEnabled(true);
        contryCod.setEnabled(true);
        imageView.setEnabled(true);
        confirm.setText(R.string.confirm);
    }


    public void onEditProfileCallAPI(){
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
                CitiesResponse citiesResponse = response.body();
                if (citiesResponse != null){
                    if (citiesResponse.getCode() == 200) {
                        cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
                        citySpinner.setAdapter(cityAdapter);


                        if (profileResponse.getData().getPreviewer().getCity() != null){

                            citySpinner.setAdapter(cityAdapter);

                            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    cityId = (int) cityAdapter.getItemId(i);
                                    callRegionstype(cityId);
                                    region1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            regionId_1 = (int) regionAdapter.getItemId(i);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                    region2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            regionId_2 = (int) regionAdapter.getItemId(i);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                    region3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            regionId_13 = (int) regionAdapter.getItemId(i);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }



                    }else {
                        Toast.makeText(getContext() ,citiesResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = (int) cityAdapter.getItemId(i);
                callRegionstype(cityId);
                region1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        regionId_1 = (int) regionAdapter.getItemId(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                region2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        regionId_2 = (int) regionAdapter.getItemId(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                region3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        regionId_13 = (int) regionAdapter.getItemId(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        Bitmap bm = profileImage.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String encodedImage = null;
//        encodedImage = Base64.encodeToString(b, Base64.NO_WRAP);
        String name = String.valueOf(proflieName.getText());
        String phone = String.valueOf(profliePhone.getText());
        String email = String.valueOf(proflieEmail.getText());
        String password = String.valueOf(profliePassword.getText());
        String aboutV = String.valueOf(infoAboutMe.getText());
        String experianceV = String.valueOf(experiance.getText());
        String addInfoV = String.valueOf(addInfo.getText());
        String addFessV = String.valueOf(addFees.getText());
        String yearsOfWorkV = String.valueOf(yearsOfWork.getText());
        MultipartBody.Part body = null;

//        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || aboutV.isEmpty() || experianceV.isEmpty() || addInfoV.isEmpty() ||
//            addFessV.isEmpty()||yearsOfWorkV.isEmpty() ||citySpinner.isSelected() || region1Spinner.isSelected() || region2Spinner.isSelected() || region3Spinner.isSelected()
//        ) {
//            Toast.makeText(getContext()  , "ادخل جميع الحقول ", Toast.LENGTH_SHORT ).show();
//        }
//        else {
//        UpdatePreviewerProfileParms parms = new UpdatePreviewerProfileParms(String.valueOf(proflieName.getText())
//        ,String.valueOf(profliePhone.getText()) ,String.valueOf(proflieEmail.getText()) ,String.valueOf(profliePassword.getText()) ,contryCod.getSelectedCountryNameCode(),
//                cityId ,regionId_1,regionId_2,regionId_13,String.valueOf(infoAboutMe.getText()) ,"Doc"
//                ,String.valueOf(addInfo.getText()) ,String.valueOf(addFees.getText()) , String.valueOf(yearsOfWork.getText()) , String.valueOf(experiance.getText()),"encodedImage");
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
        if (password.isEmpty()){
            map.put("password", RequestBody.create(MultipartBody.FORM , profliePassword.getText().toString()));
        }
            // create a map of data to pass along
            map.put("name", RequestBody.create(MultipartBody.FORM , proflieName.getText().toString()));
            map.put("phone", RequestBody.create(MultipartBody.FORM , profliePhone.getText().toString()));
            map.put("email", RequestBody.create(MultipartBody.FORM , proflieEmail.getText().toString()));
            map.put("country_code", RequestBody.create(MultipartBody.FORM , contryCod.getSelectedCountryNameCode()));
            map.put("city_id", RequestBody.create(MultipartBody.FORM , String.valueOf(cityId)));
            map.put("region_1_id", RequestBody.create(MultipartBody.FORM , String.valueOf(regionId_1)));
            map.put("region_2_id", RequestBody.create(MultipartBody.FORM , String.valueOf(regionId_2)));
            map.put("region_3_id", RequestBody.create(MultipartBody.FORM , String.valueOf(regionId_13)));
            map.put("identity", RequestBody.create(MultipartBody.FORM , addInfo.getText().toString()));
            map.put("identity_docs", RequestBody.create(MultipartBody.FORM , "Doc"));
            map.put("about", RequestBody.create(MultipartBody.FORM , infoAboutMe.getText().toString()));
            map.put("extra_about", RequestBody.create(MultipartBody.FORM , infoAboutMe.getText().toString()));
            map.put("cost", RequestBody.create(MultipartBody.FORM , addFees.getText().toString()));
            map.put("years", RequestBody.create(MultipartBody.FORM , yearsOfWork.getText().toString()));
            map.put("experience", RequestBody.create(MultipartBody.FORM , experiance.getText().toString()));
            PreviewerActivity.dialog.show();
            Call<UpdatePreviewerProfileResponse> call = jsonApi.updatePreviewerProfile("Bearer " + PreviewerActivity.token ,map , body);
            call.enqueue(new Callback<UpdatePreviewerProfileResponse>() {
                @Override
                public void onResponse(Call<UpdatePreviewerProfileResponse> call, Response<UpdatePreviewerProfileResponse> response) {
                    PreviewerActivity.dialog.dismiss();
                    UpdatePreviewerProfileResponse previewerProfileResponse = response.body() ;
                    if (previewerProfileResponse.getCode() == 200){
                        isEnable();
                    Toast.makeText(getContext() , previewerProfileResponse.getMessage() , Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getContext() , PreviewerActivity.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(getContext() , previewerProfileResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    }

                }



                @Override
                public void onFailure(Call<UpdatePreviewerProfileResponse> call, Throwable t) {
                    Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();

                }
            });



//        }
    }

    public void callRegionstype(int id){
        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(new RegionParams(id));
        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
            @Override
            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
                GetRegionResponse regionResponse = response.body();
                if (regionResponse != null){
                    if (response.code() == 200) {
                        regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
                        region1Spinner.setAdapter(regionAdapter);
                        region2Spinner.setAdapter(regionAdapter);
                        region3Spinner.setAdapter(regionAdapter);


                        int cityIndex = cityAdapter.getIndex(profileResponse.getData().getPreviewer().getCity().getId());
                        citySpinner.setSelection(cityIndex);

                        if (profileResponse.getData().getPreviewer().getRegion1() != null){
                            int index1 = regionAdapter.getIndex(profileResponse.getData().getPreviewer().getRegion1().getId());
                            region1Spinner.setSelection(index1);
                        }
                        if (profileResponse.getData().getPreviewer().getRegion2() != null){
                            int index2 = regionAdapter.getIndex(profileResponse.getData().getPreviewer().getRegion2().getId());
                            region2Spinner.setSelection(index2);
                        }
                        if (profileResponse.getData().getPreviewer().getRegion3() != null){
                            int index3 = regionAdapter.getIndex(profileResponse.getData().getPreviewer().getRegion3().getId());
                            region3Spinner.setSelection(index3);
                        }


                    } else {
                        Toast.makeText(getContext() ,regionResponse.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void openGallory(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            // when permission is nor granted
            // request permission
            ActivityCompat.requestPermissions(getActivity()
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }
        else
        {
            // when permission
            // is granted
            // create method
            selectImage();
        }

    }
    private void selectImage() {
        // clear previous data
        imageView.setImageBitmap(null);
        // Initialize intent
        Intent intent=new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("image/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // check condition
        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            // when permission
            // is granted
            // call method
            selectImage();
        }
        else
        {
            // when permission is denied
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            // when result is ok
            // initialize uri
            Uri uri=data.getData();
            // Initialize bitmap
            try {
                fileUri = uri;
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                Toast.makeText(getContext() , bitmap + "" , Toast.LENGTH_LONG).show();
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                imageURL= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                bytes=Base64.decode(imageURL,Base64.DEFAULT);
                // Initialize bitmap
                bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        MultipartBody.Part body ;
//        RequestBody model ;
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());
        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContext().getContentResolver().getType(fileUri)),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

}