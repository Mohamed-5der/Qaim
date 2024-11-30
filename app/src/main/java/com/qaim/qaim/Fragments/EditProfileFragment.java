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
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CitiesListParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomCountryAdapter;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CountriesResponse.CountriesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserProfileResponse.User;
import com.qaim.qaim.Models.UserProfileResponse.UserProfileResponse;
import com.qaim.qaim.Models.UserUpdateProfile.UserUpdateProfileResponse;
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


public class EditProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView userName , tittle ;
    EditText addName ,  addPhone ,addPassword , addEmail ;
//    licince ;
    Button editBtn ;
    Spinner countrySpinner ;
    Spinner citySpinner ;
    CountryCodePicker countryCodePicker ;
    CustomCityAdapter cityAdapter ;
    int countryId ;
    int cityId ;
    Uri fileUri;
    ImageView imageView ;
    String imageURL ;
    Bitmap bitmap ;
    CustomCountryAdapter countryAdapter ;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_profile, container, false);
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
        getCountries();
        userName = v.findViewById(R.id.userName);
        addName = v.findViewById(R.id.EditProfileNameEditText);
        countryCodePicker = v.findViewById(R.id.countryCode);
        addPhone = v.findViewById(R.id.phoneEditText);
        addPassword = v.findViewById(R.id.EditProfilePasswordEditText);
        addEmail = v.findViewById(R.id.emailEditText);
//        licince = v.findViewById(R.id.licenceEditText);
        tittle = v.findViewById(R.id.tittle);
        imageView = v.findViewById(R.id.proflieImage);
        userName.setEnabled(false);
        addName.setEnabled(false);
        countryCodePicker.setEnabled(false);
        addPhone.setEnabled(false);
        addPassword.setEnabled(false);
        addEmail.setEnabled(false);
//        licince.setEnabled(false);
        imageView.setEnabled(false);
        countrySpinner = v.findViewById(R.id.countrySpinner);
        citySpinner = v.findViewById(R.id.citySpinner);
        editBtn = v.findViewById(R.id.confirm);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.dialog.show();
        Call<UserProfileResponse> callResponse = jsonApi.getUserProfile("Bearer "+ MainActivity.token);
        callResponse.enqueue(new Callback<UserProfileResponse>() {
           @Override
           public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
               MainActivity.dialog.dismiss();
               UserProfileResponse userProfileResponse = response.body();
               if (response.code() == 200) {
                   MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                   MainActivity.dialog.dismiss();
                   User user = userProfileResponse.getData().getUser();
                   userName.setText(String.valueOf(user.getName()));
                   addName.setText(String.valueOf(user.getName()));
                   countryCodePicker.setCountryForNameCode(String.valueOf(user.getCountryCode()));
                   addPhone.setText(String.valueOf(user.getPhone()));
                   addEmail.setText(String.valueOf(user.getEmail()));
//                   licince.setText(String.valueOf(user.getLicense()));
//                   city.setSelection(user.getCity().getId());
                   getCities(user.getCountryId());
                   addPassword.setText("");
                   Picasso.get().load(user.getImage()).fit().error(R.drawable.icon).into(imageView);

               }else {
                   MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
               }
           }

           @Override
           public void onFailure(Call<UserProfileResponse> call, Throwable t) {

           }
       });

       editBtn.setOnClickListener(view12 -> {
           MultipartBody.Part body = null;
          if (addName.isEnabled()){
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
              if (String.valueOf(addPassword.getText()).isEmpty()){
                  map.put("password", RequestBody.create(MultipartBody.FORM , addPassword.getText().toString()));
              }
                  map.put("name", RequestBody.create(MultipartBody.FORM , addName.getText().toString()));
                  map.put("phone", RequestBody.create(MultipartBody.FORM , addPhone.getText().toString()));
                  map.put("email", RequestBody.create(MultipartBody.FORM , addEmail.getText().toString()));
                  map.put("country_code", RequestBody.create(MultipartBody.FORM , countryCodePicker.getSelectedCountryNameCode()));
                  map.put("country_id", RequestBody.create(MultipartBody.FORM , String.valueOf(countryId)));
                  map.put("city_id", RequestBody.create(MultipartBody.FORM , String.valueOf(cityId)));
//                      map.put("lisence", RequestBody.create(MultipartBody.FORM , licince.getText().toString()));
                  MainActivity.dialog.show();
                  Call<UserUpdateProfileResponse> call = jsonApi.updateUserProfile("Bearer "+ MainActivity.token , map, body);

                  call.enqueue(new Callback<UserUpdateProfileResponse>() {
                      @Override
                      public void onResponse(Call<UserUpdateProfileResponse> call, Response<UserUpdateProfileResponse> response) {
                          MainActivity.dialog.dismiss();
                          UserUpdateProfileResponse response1 = response.body();
//                              Toast.makeText(getContext() , response1.getMessage() , Toast.LENGTH_SHORT).show();
                          if (response.code() == 200) {
                              setEnabled(false);
                              MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
                              Intent i = new Intent(getContext() , MainActivity.class);
                              startActivity(i);
                          }else {
                              MainActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                                  Toast.makeText(getContext() , response1.getMessage() , Toast.LENGTH_SHORT).show();
                          }
                      }
                      @Override
                      public void onFailure(Call<UserUpdateProfileResponse> call, Throwable t) {
                          Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                      }
                  });

          } else {
              setEnabled(true);
          }

       });
        imageView.setOnClickListener(view1 -> openGallory());

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


    public void setEnabled(boolean isEnabled) {

        editBtn.setText(isEnabled ? R.string.confirm : R.string.edit);
        tittle.setText(isEnabled ? R.string.editeProfile : R.string.Profile);
        userName.setEnabled(isEnabled);
        addName.setEnabled(isEnabled);
        countryCodePicker.setEnabled(isEnabled);
        addPhone.setEnabled(isEnabled);
        addPassword.setEnabled(isEnabled);
        addEmail.setEnabled(isEnabled);
//        licince.setEnabled(isEnabled);
        imageView.setEnabled(isEnabled);
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
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_image)),100);
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

    public void getCountries() {
        Call<CountriesResponse> countriesResponseCall = jsonApi.getCountries();
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
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities(new CitiesListParams(countryId));
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