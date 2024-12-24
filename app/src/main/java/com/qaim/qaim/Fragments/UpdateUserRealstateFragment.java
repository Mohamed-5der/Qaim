package com.qaim.qaim.Fragments;

import static android.app.Activity.RESULT_OK;
import static com.qaim.qaim.Activities.MainActivity.dialog;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Activities.MapViewActivity;
import com.qaim.qaim.Classes.CitiesListParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomCountryAdapter;
import com.qaim.qaim.Classes.CustomRealstateTypeAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Classes.ImageAdapter;
import com.qaim.qaim.Classes.ImageLoadAdapter;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CountriesResponse.CountriesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.qaim.qaim.Models.RealstateUpdateUserResponse.RealstateUpdateUserResponse;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.Models.TypesResponse.GetTypeResponse;
import com.qaim.qaim.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UpdateUserRealstateFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private int id;

    Retrofit retrofit;
    JsonApi jsonApi;
    CustomCountryAdapter countryAdapter ;
    CustomCityAdapter cityAdapter;
    int cityId;
    int countryId;
    CustomRegionAdapter regionAdapter;
    int regionId;
    CustomRealstateTypeAdapter customRealstateTypeAdapter;
    int typeId;
    Spinner realStateType, neighborhoodSpinner, countrySpinner, citySpinner;
    EditText realstateArea, addAddtionalDetails ,tittle;
    Button addPhotoBtn, addLocationBtn, confirmBtn;
    double latitude;
    double longitude;
    String address ;

    List<Uri> fileUris = new ArrayList<>();
    ImageView imageView ;
    List<Bitmap> bitmaps = new ArrayList<>();
    RecyclerView imageRecycleView  , imageAddRecycleView;
    ImageAdapter imageAdapter ;
    ImageLoadAdapter loadAdapter ;
    MultipartBody.Part body = null  ;
    RealstateShowUserResponse realstateShowUserResponse ;
    public UpdateUserRealstateFragment() {
        // Required empty public constructor
    }

    public static UpdateUserRealstateFragment newInstance(int id) {
        UpdateUserRealstateFragment fragment = new UpdateUserRealstateFragment();
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
        View v = inflater.inflate(R.layout.fragment_update_user_realstate, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            MainFragment fragment = new  MainFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction().
                    replace(R.id.frameLayout , fragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        imageRecycleView = v.findViewById(R.id.imageRecycleView);
        imageAddRecycleView = v.findViewById(R.id.imageAddRecycleView);
        showRealState();

        realstateArea = v.findViewById(R.id.addRealStateArea);
        addAddtionalDetails = v.findViewById(R.id.addAddtionalDetails);
        addPhotoBtn = v.findViewById(R.id.addEstatePhoto);
        addLocationBtn = v.findViewById(R.id.addLocation);
        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity() , MapViewActivity.class);
                i.putExtra("lat" , latitude);
                i.putExtra("long" , longitude);
                startActivity(i);
            }
        });
        realStateType = v.findViewById(R.id.spinner);
        countrySpinner = v.findViewById(R.id.countrySpinner);
        citySpinner = v.findViewById(R.id.citySpinner);
        neighborhoodSpinner = v.findViewById(R.id.neiborhood);
        tittle = v.findViewById(R.id.tittle);
        realStateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeId = (int) customRealstateTypeAdapter.getItemId(i);
                Toast.makeText(getContext(), "" + typeId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        confirmBtn = v.findViewById(R.id.confirm);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                File file = null ;
                RequestBody requestFile = null  ;
                HashMap<String, RequestBody> map = new HashMap<>();
                               List<MultipartBody.Part> imgs = new ArrayList<>();

                    if (bitmaps != null) {
                        for (int i = 0; i < fileUris.size(); i++) {
                            if (fileUris.get(i) != null && getPath(fileUris.get(i)) != null) {
                                file = new File(getPath(fileUris.get(i)));
                                // create RequestBody instance from file
                                requestFile =
                                        RequestBody.create(
                                                MediaType.parse(getContext().getContentResolver().getType(fileUris.get(i))),
                                                file
                                        );
                                body = MultipartBody.Part.createFormData("files[" + i + "]", file.getName(), requestFile);
                                imgs.add(body);
                            }
                        }
                    }

                map.put("real_estate_id", RequestBody.create(MultipartBody.FORM , id+""));
                map.put("title", RequestBody.create(MultipartBody.FORM , tittle.getText().toString()));
                map.put("description", RequestBody.create(MultipartBody.FORM , addAddtionalDetails.getText().toString()));
                map.put("type_id", RequestBody.create(MultipartBody.FORM , "" + typeId));
                map.put("country_id", RequestBody.create(MultipartBody.FORM , "" + countryId));
                map.put("city_id", RequestBody.create(MultipartBody.FORM , "" + cityId));
                map.put("region_id", RequestBody.create(MultipartBody.FORM , "" + regionId));
                map.put("latitude", RequestBody.create(MultipartBody.FORM , "" + latitude));
                map.put("longitude", RequestBody.create(MultipartBody.FORM , "" + longitude));
                map.put("address", RequestBody.create(MultipartBody.FORM , "" + address));
                map.put("distance", RequestBody.create(MultipartBody.FORM , "" + realstateArea.getText().toString()));


                Call<RealstateUpdateUserResponse> call = jsonApi.updateRealstate(LocaleHelper.getLanguage(getContext()), "Bearer " + MainActivity.token,  map, imgs);
                    call.enqueue(new Callback<RealstateUpdateUserResponse>() {
                        @Override
                        public void onResponse(Call<RealstateUpdateUserResponse> call, Response<RealstateUpdateUserResponse> response) {
                            RealstateUpdateUserResponse response1 = response.body();
                            Toast.makeText(getContext(),response1.getMessage() , Toast.LENGTH_SHORT).show();
                            if (response1.getCode() == 200) {
                                MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                                Intent i = new Intent(getContext(), MainActivity.class);
//                                Toast.makeText(getContext(),response1.getMessage() , Toast.LENGTH_SHORT).show();
                                startActivity(i);
                            } else {
//                                Toast.makeText(getContext(),response1.getMessage() , Toast.LENGTH_SHORT).show();
                                MainActivity.alert.crateMsg(response.body().getMessage() , getContext());                            }
                        }

                        @Override
                        public void onFailure(Call<RealstateUpdateUserResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


        });
        addPhotoBtn.setOnClickListener(view1 -> selectImage());
    }

    public void callRegionstype(int id){
        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(LocaleHelper.getLanguage(getContext()), new RegionParams(id));
        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
            @Override
            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
                GetRegionResponse regionResponse = response.body();
                if (response.code() == 200) {
                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
                    neighborhoodSpinner.setAdapter(regionAdapter);
                    if (realstateShowUserResponse.getData().getRow().getRegion().getId() != 0) {
                        neighborhoodSpinner.setAdapter(regionAdapter);
                        neighborhoodSpinner.setSelection(regionAdapter.getIndex(realstateShowUserResponse.getData().getRow().getRegion().getId()));
                    }

                    neighborhoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            regionId = (int) regionAdapter.getItemId(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void callRealstateType(){
        Call<GetTypeResponse> getTypeResponseCall = jsonApi.getTypes(LocaleHelper.getLanguage(getContext()));
        getTypeResponseCall.enqueue(new Callback<GetTypeResponse>() {
            @Override
            public void onResponse(Call<GetTypeResponse> call, Response<GetTypeResponse> response) {
                GetTypeResponse getTypeResponse = response.body();
                if (response.code() == 200) {
                    customRealstateTypeAdapter = new CustomRealstateTypeAdapter(getTypeResponse.getData().getTypes());
                    realStateType.setAdapter(customRealstateTypeAdapter);
                    if (realstateShowUserResponse.getData().getRow().getType().getId() != 0){
                        realStateType.setAdapter(customRealstateTypeAdapter);
                        realStateType.setSelection(customRealstateTypeAdapter.getIndex(realstateShowUserResponse.getData().getRow().getType().getId()));
                    }

                }
            }

            @Override
            public void onFailure(Call<GetTypeResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            fileUris.add(Uri.fromFile(new File(filePath)));
            bitmaps.add(selectedImage);
            imageAdapter = new ImageAdapter(bitmaps);
            LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
            imageAddRecycleView.setLayoutManager(lm);
            imageAddRecycleView.setHasFixedSize(true);
            imageAddRecycleView.setAdapter(imageAdapter);
        }
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

    public void showRealState(){
        MainActivity.dialog.show();
        Call<RealstateShowUserResponse> call = jsonApi.ShowRealstate(LocaleHelper.getLanguage(getContext()), "Bearer " + MainActivity.token , new ShowRealstateUserParams(id));
        call.enqueue(new Callback<RealstateShowUserResponse>() {
            @Override
            public void onResponse(Call<RealstateShowUserResponse> call, Response<RealstateShowUserResponse> response) {
                MainActivity.dialog.dismiss();
                realstateShowUserResponse = response.body();
                if (realstateShowUserResponse.getCode() == 200){
                    getCountries();
                    getCities(realstateShowUserResponse.getData().getRow().getCountry().getId());
                    callRealstateType();
                    }
                    tittle.setText(realstateShowUserResponse.getData().getRow().getTitle());
                    realstateArea.setText((String) realstateShowUserResponse.getData().getRow().getDistance());
                    addAddtionalDetails.setText( realstateShowUserResponse.getData().getRow().getDescription());
                    latitude = Double.parseDouble(realstateShowUserResponse.getData().getRow().getLatitude());
                    longitude = Double.parseDouble(realstateShowUserResponse.getData().getRow().getLongitude());
                    loadAdapter = new ImageLoadAdapter(realstateShowUserResponse.getData().getRow().getFiles() , bitmaps);
                    LinearLayoutManager lm = new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL , true);
                    imageRecycleView.setLayoutManager(lm);
                    imageRecycleView.setHasFixedSize(true);
                    imageRecycleView.setAdapter(loadAdapter);
                    dialog.dismiss();
                }

            @Override
            public void onFailure(Call<RealstateShowUserResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
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
                    callRegionstype(cityId);
                    if (realstateShowUserResponse.getData().getRow().getCity().getId() != 0) {
                        citySpinner.setSelection(cityAdapter.getIndex(realstateShowUserResponse.getData().getRow().getCity().getId()));
                    }
                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            cityId = (int) cityAdapter.getItemId(i);
                            callRegionstype(cityId);
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