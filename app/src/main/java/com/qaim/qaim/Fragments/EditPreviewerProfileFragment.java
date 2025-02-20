package com.qaim.qaim.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
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

import com.hbb20.CountryCodePicker;
import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Activities.SplashScreen;
import com.qaim.qaim.Classes.CitiesListParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomCountryAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CountriesResponse.CountriesResponse;
import com.qaim.qaim.Models.GetProviewerProfile.PreviewerProfileResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.Models.UpdatePreviewerProfile.UpdatePreviewerProfileResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class EditPreviewerProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    EditText proflieName ,proflieEmail , profliePhone , profliePassword , addInfo
            , addFees , infoAboutMe , experiance , yearsOfWork ;
    CountryCodePicker contryCod ;
    Button confirm ;
    Spinner countrySpinner, citySpinner ,region1Spinner ,region2Spinner , region3Spinner;
    TextView userName , lblRate;
    ImageView profileImage ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCountryAdapter countryAdapter ;
    CustomCityAdapter cityAdapter ;
    int countryId ;
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
    Boolean isPasswordVisible = false;

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
        imageButton.setOnClickListener(view -> {
            PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment ();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        imageView = v.findViewById(R.id.proflieImage);
        userName = v.findViewById(R.id.userName);
        lblRate = v.findViewById(R.id.lbl_rate);
        proflieName = v.findViewById(R.id.EditProfileNameEditText);
        proflieEmail = v.findViewById(R.id.EditProfileEmailEditText);
        profliePhone = v.findViewById(R.id.phoneEditText);
        profliePassword = v.findViewById(R.id.passwordEditText);
        addInfo = v.findViewById(R.id.addInfo);
        addFees = v.findViewById(R.id.addFees);
        infoAboutMe = v.findViewById(R.id.addInfoAboutMe);
        experiance = v.findViewById(R.id.experienceField);
        yearsOfWork = v.findViewById(R.id.yearsOfWork);
        countrySpinner = v.findViewById(R.id.countrySpinner);
        citySpinner = v.findViewById(R.id.citySpinner);
        region1Spinner  = v.findViewById(R.id.Area1spinner);
        region2Spinner = v.findViewById(R.id.Area2spinner);
        region3Spinner= v.findViewById(R.id.Area3spinner);
        confirm= v.findViewById(R.id.confirm);
        confirm.setOnClickListener(view -> {
            if (proflieName.isEnabled()){
                onEditProfileCallAPI();
            }else {
                enable();
            }
        });
        contryCod = v.findViewById(R.id.countryCode);

        ImageView showHidePassword = v.findViewById(R.id.showHidePassword);
        showHidePassword.setOnClickListener(v1 -> {
            if (isPasswordVisible) {
                profliePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                profliePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            profliePassword.setSelection(profliePassword.getText().length()); // Keep cursor at the end
            isPasswordVisible = !isPasswordVisible;
        });

        callProffileAPI();

        v.findViewById(R.id.changeLanguage).setOnClickListener(v1 -> {
            if (LocaleHelper.getLanguage(getActivity()).equals("en")) {
                LocaleHelper.setLocale(getActivity(), "ar");
            } else {
                LocaleHelper.setLocale(getActivity(), "en");
            }
            restartApp(); // Restart activity to apply language changes
        });
        return v ;
    }

    private void restartApp() {
        Intent intent = new Intent(getActivity(), SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finishAffinity(); // Finish current activity
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setOnClickListener(view1 -> selectImage());
    }

    public void callProffileAPI(){
        PreviewerActivity.dialog.show();
        Call<PreviewerProfileResponse> call = jsonApi.getPreviewerProfile(LocaleHelper.getLanguage(getContext()), "Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<PreviewerProfileResponse>() {
            @Override
            public void onResponse(Call<PreviewerProfileResponse> call, Response<PreviewerProfileResponse> response) {
               PreviewerActivity.dialog.dismiss();
                PreviewerProfileResponse previewerProfileResponse = response.body();
                if (previewerProfileResponse.getCode() == 200) {
                    isEnable();
                    userName.setText(previewerProfileResponse.getData().getPreviewer().getName());
                    lblRate.setText(previewerProfileResponse.getData().getPreviewer().getRate());
                    proflieName.setText(previewerProfileResponse.getData().getPreviewer().getName());
                    proflieEmail.setText(previewerProfileResponse.getData().getPreviewer().getEmail());
                    profliePhone.setText(previewerProfileResponse.getData().getPreviewer().getPhone());
                    addInfo.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getIdentity()));
                    addFees.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getCost()));
                    infoAboutMe.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getAbout()));
                    experiance.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getExperience()));
                    yearsOfWork.setText(String.valueOf(previewerProfileResponse.getData().getPreviewer().getYears()));

                    profileResponse = previewerProfileResponse;
                    getCountries();
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
        getCities(profileResponse.getData().getPreviewer().getCountry().getId());

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

        String password = String.valueOf(profliePassword.getText());
        HashMap<String, RequestBody> map = new HashMap<>();
        if (password.isEmpty()){
            map.put("password", RequestBody.create(MultipartBody.FORM , profliePassword.getText().toString()));
        }
            // create a map of data to pass along
            map.put("name", RequestBody.create(MultipartBody.FORM , proflieName.getText().toString()));
            map.put("phone", RequestBody.create(MultipartBody.FORM , profliePhone.getText().toString()));
            map.put("email", RequestBody.create(MultipartBody.FORM , proflieEmail.getText().toString()));
            map.put("country_code", RequestBody.create(MultipartBody.FORM , contryCod.getSelectedCountryNameCode()));
            map.put("country_id", RequestBody.create(MultipartBody.FORM , String.valueOf(countryId)));
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
            Call<UpdatePreviewerProfileResponse> call = jsonApi.updatePreviewerProfile(LocaleHelper.getLanguage(getContext()), "Bearer " + PreviewerActivity.token ,map , body);
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
        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(LocaleHelper.getLanguage(getContext()), new RegionParams(id));
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

    private void selectImage() {
        Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CROP, true);//default is false
        startActivityForResult(intent, 1213);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode == 1213 && resultCode == RESULT_OK && data != null) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            bitmap = BitmapFactory.decodeFile(filePath);
            fileUri = Uri.fromFile(new File(filePath));
            imageView.setImageBitmap(bitmap);
        }
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

    public void getCountries() {
        Call<CountriesResponse> countriesResponseCall = jsonApi.getCountries(LocaleHelper.getLanguage(getContext()));
        countriesResponseCall.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CountriesResponse> call, @NonNull Response<CountriesResponse> response) {
                CountriesResponse countriesResponse = response.body();
                if (countriesResponse.getCode() == 200) {
                    countryAdapter = new CustomCountryAdapter(response.body().getData().getCountries());
                    countrySpinner.setAdapter(countryAdapter);
                    countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            countryId = (int) countryAdapter.getItemId(i);
                            cityId = 0;
                            getCities(countryId);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }else {
                    Toast.makeText(getContext() , countriesResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CountriesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCities(int countryId) {
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities(LocaleHelper.getLanguage(getContext()), new CitiesListParams(countryId));
        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
                CitiesResponse citiesResponse = response.body();
                if (citiesResponse.getCode() == 200) {
                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
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

                }else {
                    Toast.makeText(getContext() , citiesResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}