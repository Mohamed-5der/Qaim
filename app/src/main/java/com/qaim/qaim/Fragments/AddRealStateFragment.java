package com.qaim.qaim.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Activities.MapViewActivity;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomRealstateTypeAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Classes.ImageAdapter;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RealstateStoreUserResponse.RealstateStoreUserResponse;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.Models.TypesResponse.GetTypeResponse;
import com.qaim.qaim.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddRealStateFragment extends Fragment  implements OnMapReadyCallback {

    Retrofit retrofit;
    JsonApi jsonApi;
    CustomCityAdapter cityAdapter;
    int cityId;
    CustomRegionAdapter regionAdapter;
    int regionId;
    CustomRealstateTypeAdapter customRealstateTypeAdapter;
    int typeId;
    Spinner realStateType, neighborhoodSpinner, city;
    EditText realstateArea, addAddtionalDetails , tittle;
    Button addPhotoBtn, addLocationBtn, confirmBtn , addEstatePhoto;
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude;
    double longitude;
    String address ;
    List<Uri> fileUris = new ArrayList<>();
    ImageView imageView ;
    List<String> imageURLs = new ArrayList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    RecyclerView imageRecycleView ;
    ImageAdapter imageAdapter ;
    MapView mapView ;
    LatLng latLng ;
    GoogleMap googleMap;
    private static final int MAP_REQUEST_CODE = 111;

    public AddRealStateFragment() {
        // Required empty public constructor
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            // when permission
            // is granted
            // call method
            selectImage();
        }else
        {
            // when permission is denied
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_real_state, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFragment fragment = new  MainFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , fragment).commit();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        callRealstateType();
        MainActivity.dialog.show();
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
                MainActivity.dialog.dismiss();
                if (response.code() == 200) {
                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
                    city.setAdapter(cityAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        realstateArea = v.findViewById(R.id.addRealStateArea);
        tittle = v.findViewById(R.id.tittle);
        addAddtionalDetails = v.findViewById(R.id.addAddtionalDetails);
        addPhotoBtn = v.findViewById(R.id.addEstatePhoto);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               createDialoge();

            }
        });
        addLocationBtn = v.findViewById(R.id.addLocation);
        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity() , MapViewActivity.class), MAP_REQUEST_CODE);
            }
        });
        addEstatePhoto = v.findViewById(R.id.addEstatePhoto);
        addEstatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallory();
            }
        });
        mapView =  v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        realStateType = v.findViewById(R.id.spinner);
        neighborhoodSpinner = v.findViewById(R.id.neiborhood);
        realStateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeId = (int) customRealstateTypeAdapter.getItemId(i);
         //       Toast.makeText(getContext(), "" + typeId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        city = v.findViewById(R.id.citySpinner);
        city.setAdapter(cityAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = (int) cityAdapter.getItemId(i);
                callRegionstype(cityId);
                neighborhoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        regionId = (int) regionAdapter.getItemId(i);
                   //     Toast.makeText(getContext(), "" + regionId, Toast.LENGTH_SHORT).show();
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
        callRegionstype(cityId);
        confirmBtn = v.findViewById(R.id.confirm);

        imageRecycleView = v.findViewById(R.id.imageRecycleView);

        return v;
    }

//    private void createDialoge() {
//        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
//        builderSingle.setTitle("Select One Name:-");
//
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("Hardik");
//        arrayAdapter.add("Archit");
//        arrayAdapter.add("Jignesh");
//        arrayAdapter.add("Umang");
//        arrayAdapter.add("Gatti");
//
////        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                dialog.dismiss();
////            }
////        });
////        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialog, int which) {
////                String strName = arrayAdapter.getItem(which);
////                AlertDialog.Builder builderInner = new AlertDialog.Builder(getContext());
////                builderInner.setMessage(strName);
////                builderInner.setTitle("Your Selected Item is");
////                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog,int which) {
////                        dialog.dismiss();
////                    }
////                });
////                builderInner.show();
////            }
////        });
////        builderSingle.show();
//    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView.getMapAsync(AddRealStateFragment.this);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

//
//
               String area = String.valueOf(realstateArea.getText()) ;
               String addtional = String.valueOf(addAddtionalDetails.getText());
               String tittleText = String.valueOf(tittle.getText());

//               if (tittleText.isEmpty()) {
//                   Toast.makeText(getContext(), "ادخل الاسم", Toast.LENGTH_SHORT).show();
//               } else if (area.isEmpty()) {
//                   Toast.makeText(getContext(), "ادخل المساحه", Toast.LENGTH_SHORT).show();
//               } else if (area.isEmpty()) {
//                   Toast.makeText(getContext(), "ادخل البيانات الاضافية", Toast.LENGTH_SHORT).show();
//               }
////               else if (latitude == 0.0 || longitude == 0.0){
////                   Toast.makeText(getContext(), "ادخل الموقع", Toast.LENGTH_SHORT).show();
////               }
//               else
//               {
                   HashMap<String, RequestBody> map = new HashMap<>();
                   map.put("title", RequestBody.create(MultipartBody.FORM , tittle.getText().toString()));
                   map.put("description", RequestBody.create(MultipartBody.FORM , addAddtionalDetails.getText().toString()));
                   map.put("type_id", RequestBody.create(MultipartBody.FORM , "" + typeId));
                   map.put("city_id", RequestBody.create(MultipartBody.FORM , "" + cityId));
                   map.put("region_id", RequestBody.create(MultipartBody.FORM , "" + regionId));
                   map.put("latitude", RequestBody.create(MultipartBody.FORM , "" + latitude));
                   map.put("longitude", RequestBody.create(MultipartBody.FORM , "" + longitude));
                   map.put("address", RequestBody.create(MultipartBody.FORM , "" + address));
                   map.put("distance", RequestBody.create(MultipartBody.FORM , "" + area));


                   List<MultipartBody.Part> imgs = new ArrayList<>();

                   for (int i = 0; i < fileUris.size(); i++) {
                       File file = new File(getPath(fileUris.get(i)));
                       // create RequestBody instance from file
                       RequestBody requestFile =
                               RequestBody.create(
                                       MediaType.parse(getContext().getContentResolver().getType(fileUris.get(i))),
                                       file
                               );
//
//               // MultipartBody.Part is used to send also the actual file name
                       MultipartBody.Part body = MultipartBody.Part.createFormData("files["+i+"]", file.getName(), requestFile);
                       imgs.add(body);
                   }

//                   StoreUserRealstateParams realstateParams = new StoreUserRealstateParams(String.valueOf(tittle.getText()), String.valueOf(addAddtionalDetails.getText()), typeId, cityId, regionId, latitude, longitude, address, Integer.parseInt((String.valueOf(realstateArea.getText()))));
                   MainActivity.dialog.show();
                   Call<RealstateStoreUserResponse> call = jsonApi.storeRealstate("Bearer " + MainActivity.token, map, imgs);


                   call.enqueue(new Callback<RealstateStoreUserResponse>() {
                       @Override
                       public void onResponse(Call<RealstateStoreUserResponse> call, Response<RealstateStoreUserResponse> response) {
                           MainActivity.dialog.dismiss();
                           RealstateStoreUserResponse response1 = response.body();
//                           Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();

                           if (response1.getCode() == 200) {
                               MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                               Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                               Intent i = new Intent(getContext(), MainActivity.class);
                               startActivity(i);
                           } else {
                               MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                               Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<RealstateStoreUserResponse> call, Throwable t) {
                           Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   });
           }

//           }
       });


    }
    public void callRegionstype(int id){
        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(new RegionParams(id));
        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
            @Override
            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
                GetRegionResponse regionResponse = response.body();
                if (response.code() == 200) {
                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
                    neighborhoodSpinner.setAdapter(regionAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void callRealstateType(){
        Call<GetTypeResponse> getTypeResponseCall = jsonApi.getTypes();
        getTypeResponseCall.enqueue(new Callback<GetTypeResponse>() {
            @Override
            public void onResponse(Call<GetTypeResponse> call, Response<GetTypeResponse> response) {
                GetTypeResponse getTypeResponse = response.body();
                if (response.code() == 200) {
                    customRealstateTypeAdapter = new CustomRealstateTypeAdapter(getTypeResponse.getData().getTypes());
                    realStateType.setAdapter(customRealstateTypeAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetTypeResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
//        imageView.setImageBitmap(null);
        // Initialize intent
        Intent intent=new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("image/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_image)),100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode == MAP_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                this.latitude = data.getDoubleExtra("latitude", 0);
                this.longitude = data.getDoubleExtra("longitude", 0);
                latLng = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 12));
                mapView.setVisibility(View.VISIBLE);
                // Use the latitude and longitude here
            }
        } else if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            // when result is ok
            // initialize uri
            Uri uri=data.getData();
            // Initialize bitmap
            try {
                fileUris.add(uri);// = uri;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                Toast.makeText(getContext() , bitmap + "" , Toast.LENGTH_LONG).show();
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                String imageURL= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                bytes=Base64.decode(imageURL,Base64.DEFAULT);
                // Initialize bitmap
                bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
//                .setImageBitmap(bitmap);
//                imageView
                bitmaps.add(bitmap);

                imageURLs.add(imageURL);
                imageAdapter = new ImageAdapter(bitmaps);
                LinearLayoutManager lm = new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL , true);
                imageRecycleView.setLayoutManager(lm);
                imageRecycleView.setHasFixedSize(true);
                imageRecycleView.setAdapter(imageAdapter);
//                imageAdapter.notifyDataSetChanged();

                // reload

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        MultipartBody.Part body ;
//        RequestBody model ;
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}