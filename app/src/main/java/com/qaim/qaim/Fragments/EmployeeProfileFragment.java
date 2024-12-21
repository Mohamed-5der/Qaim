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

import com.hbb20.CountryCodePicker;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Activities.SplashScreen;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.EmployeeProfile.Employee;
import com.qaim.qaim.Models.EmployeeProfile.EmpolyeeProfileResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UpdateEmployeeProfile.UpdateEmpolyeeProfileResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
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
    Bitmap bitmap ;
    Boolean isPasswordVisible = false;

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
        imageButton.setOnClickListener(view -> {
            EmployeeMainFragment mainFragment = new EmployeeMainFragment ();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
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

        ImageView showHidePassword = v.findViewById(R.id.showHidePassword);
        showHidePassword.setOnClickListener(v1 -> {
            if (isPasswordVisible) {
                addPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                addPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
            addPassword.setSelection(addPassword.getText().length()); // Keep cursor at the end
            isPasswordVisible = !isPasswordVisible;
        });

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
        EmployeeActivity.dialog.show();
        Call<EmpolyeeProfileResponse> call = jsonApi.getEmployeeProfile(LocaleHelper.getLanguage(getContext()), "Bearer "+ EmployeeActivity.token);
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
        imageView.setOnClickListener(view1 -> selectImage());
        editBtn.setOnClickListener(view2 -> {
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
                    Toast.makeText(getContext() , R.string.enter_password ,Toast.LENGTH_SHORT).show();
                }else {
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("name", RequestBody.create(MultipartBody.FORM , addName.getText().toString()));
                    map.put("phone", RequestBody.create(MultipartBody.FORM , addPhone.getText().toString()));
                    map.put("email", RequestBody.create(MultipartBody.FORM , addEmail.getText().toString()));
                    map.put("password", RequestBody.create(MultipartBody.FORM , addPassword.getText().toString()));
                    map.put("country_code", RequestBody.create(MultipartBody.FORM , countryCodePicker.getSelectedCountryNameCode()));

                    EmployeeActivity.dialog.show();
                    Call<UpdateEmpolyeeProfileResponse> call1 = jsonApi.updateEmployeeProfile(LocaleHelper.getLanguage(getContext()), "Bearer "+ EmployeeActivity.token ,
                            map, body );
                    call1.enqueue(new Callback<UpdateEmpolyeeProfileResponse>() {
                        @Override
                        public void onResponse(Call<UpdateEmpolyeeProfileResponse> call1, Response<UpdateEmpolyeeProfileResponse> response) {
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
                        public void onFailure(Call<UpdateEmpolyeeProfileResponse> call1, Throwable t) {

                        }
                    });
                }
            } else {
                setEnabled(true);
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
        if (requestCode==1213 && resultCode==RESULT_OK && data!=null) {
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