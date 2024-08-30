package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.LoginActivity.NOTI_KEY;
import static com.qaim.qaim.Activities.LoginActivity.spNotiToken;
import static com.qaim.qaim.Activities.SplashScreen.signUpEditor;
import static com.qaim.qaim.Activities.SplashScreen.tokenEditor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CompanyRegister.CompanyRegisterResponse;
import com.qaim.qaim.Models.CompanyUserRegisterResponse.CompanyUserRegisterResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.hbb20.CountryCodePicker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompnayUserRegisterActivity extends AppCompatActivity {
    TextView userName;
    TextView licenceName ;
    LinearLayout licenceli ;
//    CheckBox termsChecker ;

    CheckBox termsChecker ;
    Alert alert ;

    ImageButton imageButton;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    int cityId ;
    Spinner citySpinner ;
    CustomCityAdapter cityAdapter ;
    EditText proflieName , proflieEmail , profliePhone ,proflieLicense , profliePassword  , confirmprofliePassword ;
    CountryCodePicker contryCod ;
    Button singUp ;
    public static PregressDialog dialog;
    RelativeLayout addPDF ;
    RelativeLayout pdfFile ;
    TextView lblFileName ;
    private String pdfPath;
    ActivityResultLauncher<Intent> resultLauncher;
    String type ;
    String fName ;
    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compnay_user_register);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        dialog = new PregressDialog(this);
        citySpinner = findViewById(R.id.citySpinner);
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
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
                    Toast.makeText(getApplicationContext() , citiesResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
        dialog = new PregressDialog(this);
        alert = new Alert();
        type = getIntent().getStringExtra("user_type");
        imageButton = findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("user_company")){
                    startActivity(new Intent(CompnayUserRegisterActivity.this , ClientActivity.class));
                    finishAffinity();
                }else {
                    startActivity(new Intent(CompnayUserRegisterActivity.this , CreateNewAccountActivity.class));
                    finishAffinity();
                }
            }
        });
        userName = findViewById(R.id.userName);
        licenceName = findViewById(R.id.licenceName);
        addPDF = findViewById(R.id.addPolicy);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result) {
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {


                            try {
                                uploadFile( data.getData());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }
                });
        proflieName = findViewById(R.id.profileEditText);
        proflieEmail = findViewById(R.id.emailEditText);
        profliePhone = findViewById(R.id.phoneEditText);
        contryCod = findViewById(R.id.countryCode);
        contryCod.registerCarrierNumberEditText(profliePhone);
        contryCod.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                // your code
                if (contryCod.getSelectedCountryNameCode().equals("SA")){
                    profliePhone.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(11) });
                }
                else if (contryCod.getSelectedCountryNameCode().equals("EG")){
                    profliePhone.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(11) });
                }
                else {
                    profliePhone.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(14) });
                }
            }
        });

        proflieLicense = findViewById(R.id.licenceEditText);
        profliePassword =findViewById(R.id.passwordEditText);
        confirmprofliePassword =findViewById(R.id.confirmPasswordEditText);
        singUp = findViewById(R.id.signUp);
        addPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(
                       getApplicationContext(),
                        Manifest.permission
                                .READ_EXTERNAL_STORAGE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions(
                             CompnayUserRegisterActivity.this,
                            new String[] {
                                    Manifest.permission
                                            .READ_EXTERNAL_STORAGE },
                            1);
                }
                else {
                    // When permission is granted
                    // Create method
                    selectPDF();
                }
            }
        });
        pdfFile = findViewById(R.id.pdfFile);
        lblFileName = findViewById(R.id.lblFileName);
        licenceli = findViewById(R.id.licenceli);


        termsChecker = findViewById(R.id.termsChecker);
        termsChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (termsChecker.isChecked()){
                    alert.creatTermsDialog(retrofit , jsonApi , singUp , CompnayUserRegisterActivity.this);
                }else {
                    singUp.setEnabled(false);
                    singUp.setBackgroundResource(R.drawable.custom_sign_up_btn_terms);
                    return;
                }
            }
        });


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contryCod.isValidFullNumber() == false) {
                    Toast.makeText(getApplicationContext(), "رقم الجوال غير صحيح", Toast.LENGTH_SHORT).show();
                } else if (!profliePassword.getText().toString().equals(confirmprofliePassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
                } else {
                    if (type.equals("user_company")){
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("name", RequestBody.create(MultipartBody.FORM, proflieName.getText().toString()));
                        map.put("phone", RequestBody.create(MultipartBody.FORM, profliePhone.getText().toString()));
                        map.put("email", RequestBody.create(MultipartBody.FORM, proflieEmail.getText().toString()));
                        map.put("password", RequestBody.create(MultipartBody.FORM, profliePassword.getText().toString()));
                        map.put("city_id", RequestBody.create(MultipartBody.FORM, String.valueOf(cityId)));
//                        map.put("lisence", RequestBody.create(MultipartBody.FORM, proflieLicense.getText().toString()));
                        map.put("user_type", RequestBody.create(MultipartBody.FORM, type));
                        map.put("country_code", RequestBody.create(MultipartBody.FORM, String.valueOf(contryCod.getSelectedCountryNameCode())));
                        map.put("player_id", RequestBody.create(MultipartBody.FORM, spNotiToken.getString(NOTI_KEY , "")));

                        dialog.show();
                        Call<CompanyUserRegisterResponse> companyUserRegisterParmsCall = jsonApi.registerCompanyUser(map, body);
                        companyUserRegisterParmsCall.enqueue(new Callback<CompanyUserRegisterResponse>() {
                            @Override
                            public void onResponse(Call<CompanyUserRegisterResponse> call, Response<CompanyUserRegisterResponse> response) {
                                dialog.dismiss();
                                CompanyUserRegisterResponse companyUserRegisterResponse = response.body();
                                if (companyUserRegisterResponse != null) {
                                    if (companyUserRegisterResponse.getCode() == 200) {
                                        Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        String token = companyUserRegisterResponse.getData().getUser().getToken();
                                        tokenEditor.putString("token_key", token);
                                        tokenEditor.commit();
                                        signUpEditor.putString("yes", "individualClient");
                                        signUpEditor.apply();
                                        Intent i = new Intent(getApplicationContext(), OTPActivity.class);
                                        startActivity(i);
//                                        finishAffinity();
                                    } else {
                                        Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<CompanyUserRegisterResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        HashMap<String, RequestBody> map = new HashMap<>();
                        map.put("name", RequestBody.create(MultipartBody.FORM, proflieName.getText().toString()));
                        map.put("phone", RequestBody.create(MultipartBody.FORM, profliePhone.getText().toString()));
                        map.put("email", RequestBody.create(MultipartBody.FORM, proflieEmail.getText().toString()));
                        map.put("password", RequestBody.create(MultipartBody.FORM, profliePassword.getText().toString()));
                        map.put("city_id", RequestBody.create(MultipartBody.FORM, String.valueOf(cityId)));
                        map.put("license_doc", RequestBody.create(MultipartBody.FORM, proflieLicense.getText().toString()));
                        map.put("country_code", RequestBody.create(MultipartBody.FORM, String.valueOf(contryCod.getSelectedCountryNameCode())));
                        map.put("player_id", RequestBody.create(MultipartBody.FORM, spNotiToken.getString(NOTI_KEY , "")));

                        dialog.show();
                        Call<CompanyRegisterResponse> companyUserRegisterParmsCall = jsonApi.registerCompany(map, body);
                        companyUserRegisterParmsCall.enqueue(new Callback<CompanyRegisterResponse>() {
                            @Override
                            public void onResponse(Call<CompanyRegisterResponse> call, Response<CompanyRegisterResponse> response) {
                                dialog.dismiss();
                                CompanyRegisterResponse companyUserRegisterResponse = response.body();
                                if (companyUserRegisterResponse != null) {
                                    if (companyUserRegisterResponse.getCode() == 200) {
                                        Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        String token = companyUserRegisterResponse.getData().getCompany().getToken();
                                        tokenEditor.putString("token_key", token);
                                        tokenEditor.commit();
                                        signUpEditor.putString("yes", "company");
                                        signUpEditor.apply();
                                        saveVerification(companyUserRegisterResponse.getData().getCompany().getIsVerified() == 1);
                                        Intent i = new Intent(getApplicationContext(), OTPActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), companyUserRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<CompanyRegisterResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        });

        if (type.equals("user_company")){
            userName.setText("اسم المستخدم");
            licenceName.setVisibility(View.GONE);
            licenceli.setVisibility(View.GONE);
        }else {
            userName.setText("اسم الشركة");
            licenceName.setVisibility(View.VISIBLE);
            licenceli.setVisibility(View.VISIBLE);
        }
    }

    private void saveVerification(boolean isVerified) {
        SplashScreen.signUpEditor.putBoolean("isVerified", isVerified);
        SplashScreen.signUpEditor.apply();
    }

    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(this,uri);
            //Create a file object using file path
            fName = file.getName();
            if (!fName.isEmpty() && fName != null){
                pdfFile.setVisibility(View.VISIBLE);
                lblFileName.setText(fName);
            }
            body = MultipartBody.Part.createFormData("presenter_doc", fName, RequestBody.create(MediaType.parse("*/*"), file));
        }
    }
    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }
    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }
    public String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }
    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }
    private void selectPDF()
    {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
        }
        else {
            // When permission is denied
            // Display toast
            Toast
                    .makeText(this,
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

}