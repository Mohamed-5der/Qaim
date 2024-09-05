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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Models.EmployeeProfile.Employee;
import com.qaim.qaim.Models.EmployeeProfile.EmpolyeeProfileResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UpdateEmployeeProfile.UpdateEmpolyeeProfileResponse;
import com.qaim.qaim.R;
import com.hbb20.CountryCodePicker;
import com.jakewharton.picasso.OkHttp3Downloader;
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

public class EmployeeProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView userName , tittle ;
    EditText addName ,  addPhone ,addPassword , addEmail  ;
    Button editBtn ;
    CountryCodePicker countryCodePicker ;
    Uri fileUri;
    ImageView imageView ;
    String imageURL ;
    Bitmap bitmap ;

    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    public static EmployeeProfileFragment newInstance(String param1, String param2) {
        EmployeeProfileFragment fragment = new EmployeeProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_employee_profile, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeMainFragment mainFragment = new EmployeeMainFragment ();
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
        userName = v.findViewById(R.id.nameofuser);
        addName = v.findViewById(R.id.EditProfileNameEditText);
        countryCodePicker = v.findViewById(R.id.countryCode);
        addPhone = v.findViewById(R.id.phoneEditText);
        addPassword = v.findViewById(R.id.EditProfilePasswordEditText);
        addEmail = v.findViewById(R.id.emailEditText);
        tittle = v.findViewById(R.id.tittle);
        imageView = v.findViewById(R.id.proflieImage);
        editBtn = v.findViewById(R.id.confirm);
        editBtn.setText(R.string.edit);
        userName.setEnabled(false);
        addName.setEnabled(false);
        countryCodePicker.setEnabled(false);
        addPhone.setEnabled(false);
        addPassword.setEnabled(false);
        addEmail.setEnabled(false);
        imageView.setEnabled(false);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EmployeeActivity.dialog.show();
        Call<EmpolyeeProfileResponse> call = jsonApi.getEmployeeProfile("Bearer "+ EmployeeActivity.token);
        call.enqueue(new Callback<EmpolyeeProfileResponse>() {
            @Override
            public void onResponse(Call<EmpolyeeProfileResponse> call, Response<EmpolyeeProfileResponse> response) {
                EmployeeActivity.dialog.dismiss();
                EmpolyeeProfileResponse companyProfileResponse = response.body();
                if (response.code() == 200) {
                    Employee employee = companyProfileResponse.getData().getEmployee();
                    userName.setText(String.valueOf(employee.getName()));
                    addName.setText(String.valueOf(employee.getName()));
                    countryCodePicker.setCountryForNameCode(String.valueOf(employee.getCountryCode()));
                    addPhone.setText(String.valueOf(employee.getPhone()));
                    addEmail.setText(String.valueOf(employee.getEmail()));
                    Picasso.get().load(employee.getImage()).fit().error(R.drawable.icon).into(imageView);
                    Picasso.Builder builder = new Picasso.Builder(getContext());
                    builder.downloader(new OkHttp3Downloader(getActivity()));
                    builder.build().load(employee.getImage()).error(R.drawable.icon).into(imageView);
//                    addPassword.setText("");
                }
            }
            @Override
            public void onFailure(Call<EmpolyeeProfileResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallory();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addName.isEnabled()){
//                    File file = new File(imageURL);
//                    RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
//                    RequestBody model = RequestBody.create(MediaType.parse("text/plain"), "upload_test");
//
//                    SendImage sendImage = new SendImage(body , model) ;
                    MultipartBody.Part body = null;
                    String name = String.valueOf(addName.getText());
                    String phone = String.valueOf(addPhone.getText());
                    String email = String.valueOf(addEmail.getText());
                    String password = String.valueOf(addPassword.getText());

//                    if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || email.isEmpty()  || countryCodePicker.isSelected()){
//                        Toast.makeText(getContext()  , "ادخل جميع الحقول ", Toast.LENGTH_SHORT ).show();
//                    }
//                    else {
                    if (fileUri != null && getPath(fileUri) != null) {
                        File file = new File(getPath(fileUri));
                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getContext().getContentResolver().getType(fileUri)),
                                        file

                                );
                        body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                    }
                    if (password.isEmpty()){
                        Toast.makeText(getContext() ,"ادخل كلمة المرور" ,Toast.LENGTH_SHORT).show();
                    }else {
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("name", RequestBody.create(MultipartBody.FORM , addName.getText().toString()));
                        map.put("phone", RequestBody.create(MultipartBody.FORM , addPhone.getText().toString()));
                        map.put("email", RequestBody.create(MultipartBody.FORM , addEmail.getText().toString()));
                        map.put("password", RequestBody.create(MultipartBody.FORM , addPassword.getText().toString()));
                        map.put("country_code", RequestBody.create(MultipartBody.FORM , countryCodePicker.getSelectedCountryNameCode()));

                        EmployeeActivity.dialog.show();
                        Call<UpdateEmpolyeeProfileResponse> call = jsonApi.updateEmployeeProfile("Bearer "+ EmployeeActivity.token ,
                                map, body );
                        call.enqueue(new Callback<UpdateEmpolyeeProfileResponse>() {
                            @Override
                            public void onResponse(Call<UpdateEmpolyeeProfileResponse> call, Response<UpdateEmpolyeeProfileResponse> response) {
                                EmployeeActivity.dialog.dismiss();
                                UpdateEmpolyeeProfileResponse response1 = response.body();
                                Toast.makeText(getContext(), response1.getMessage(), Toast.LENGTH_SHORT).show();
                                if (response.code() == 200) {
                                    setEnabled(false);
                                    Intent i = new Intent(getContext() , EmployeeActivity.class);
                                    startActivity(i);
//                                    Toast.makeText(getContext() , response1.getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<UpdateEmpolyeeProfileResponse> call, Throwable t) {

                            }
                        });
                    }
                } else {
                    setEnabled(true);
                }

            }
        });

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






}