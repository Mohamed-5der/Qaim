package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.LoginActivity.NOTI_KEY;
import static com.qaim.qaim.Activities.LoginActivity.spNotiToken;
import static com.qaim.qaim.Activities.SplashScreen.signUpEditor;
import static com.qaim.qaim.Activities.SplashScreen.tokenEditor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import com.hbb20.CountryCodePicker;
import com.qaim.qaim.Classes.CitiesListParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomCountryAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CountriesResponse.CountriesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.PreviewerRegisterResponse.PreviewerRegisterResponse;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

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

public class PreviewerRegisterActivity extends BaseActivity {
//    CheckBox termsChecker ;
    CheckBox termsChecker ;
    Alert alert ;

    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCityAdapter cityAdapter ;
    CustomCountryAdapter countryAdapter ;
    int cityId, countryId ;
    Spinner citySpinner, countrySpinner ;
    CustomRegionAdapter regionAdapter;
    int regionId_1;
    int regionId_2;
    int regionId_13;
    Spinner region1Spinner ,region2Spinner , region3Spinner;
    ImageButton imageButton;
    RelativeLayout pdfFile;
    TextView lblFileName;
    RelativeLayout addPDF ;
    ImageView image ;
    private String pdfPath;
    String type ;
    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";
    public static PregressDialog dialog;

    EditText profliePassword;
    EditText confirmprofliePassword;
    Boolean isPasswordVisible = false;
    Boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewer_register);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);
        alert = new Alert();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);

        ImageButton imageButton = findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(PreviewerRegisterActivity.this , CreateNewAccountActivity.class));
            finishAffinity();
        });
        addPDF = findViewById(R.id.addPolicy);
        pdfFile = findViewById(R.id.pdfFile);
        lblFileName = findViewById(R.id.lblFileName);
        image = findViewById(R.id.image);
        addPDF.setOnClickListener(view -> {
            selectPDF();
        });

        countrySpinner = findViewById(R.id.countrySpinner);
        citySpinner = findViewById(R.id.citySpinner);
        getCountries();

        EditText proflieName = findViewById(R.id.profileEditText);
        EditText proflieEmail = findViewById(R.id.emailEditText);
        EditText profliePhone = findViewById(R.id.phoneEditText);
        EditText proflieFess = findViewById(R.id.costEditText);
        CountryCodePicker contryCod = findViewById(R.id.countryCode);
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
        region1Spinner = findViewById(R.id.neiborhood1);
        region2Spinner = findViewById(R.id.neiborhood2);
        region3Spinner = findViewById(R.id.neiborhood3);
        EditText proflieId = findViewById(R.id.previewer_Id_EditText);
        profliePassword = findViewById(R.id.passwordEditText);
        confirmprofliePassword = findViewById(R.id.confirmPasswordEditText);

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
        callRegionstype(cityId);
        Button confirm = findViewById(R.id.confirm);

        termsChecker = findViewById(R.id.termsChecker);
        termsChecker.setOnCheckedChangeListener((compoundButton, b) -> {
            if (termsChecker.isChecked()){
                alert.creatTermsDialog(retrofit , jsonApi , confirm , PreviewerRegisterActivity.this);
            }else {
                confirm.setEnabled(false);
                confirm.setBackgroundResource(R.drawable.custom_sign_up_btn_terms);
                return;
            }
        });

        confirm.setOnClickListener(view -> {
            if (!profliePassword.getText().toString().equals(confirmprofliePassword.getText().toString())) {
                Toast.makeText(getApplicationContext(),R.string.password_does_not_match, Toast.LENGTH_SHORT).show();
            } else {
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("name", RequestBody.create(MultipartBody.FORM, proflieName.getText().toString()));
                map.put("phone", RequestBody.create(MultipartBody.FORM, profliePhone.getText().toString()));
                map.put("email", RequestBody.create(MultipartBody.FORM, proflieEmail.getText().toString()));
                map.put("password", RequestBody.create(MultipartBody.FORM, profliePassword.getText().toString()));
                map.put("country_id", RequestBody.create(MultipartBody.FORM, String.valueOf(countryId)));
                map.put("city_id", RequestBody.create(MultipartBody.FORM, String.valueOf(cityId)));
                map.put("region_1_id", RequestBody.create(MultipartBody.FORM, String.valueOf(regionId_1)));
                map.put("region_2_id", RequestBody.create(MultipartBody.FORM, String.valueOf(regionId_2)));
                map.put("region_3_id", RequestBody.create(MultipartBody.FORM, String.valueOf(regionId_13)));
                map.put("cost", RequestBody.create(MultipartBody.FORM, proflieFess.getText().toString()));
                map.put("identity", RequestBody.create(MultipartBody.FORM, proflieId.getText().toString()));
                map.put("country_code", RequestBody.create(MultipartBody.FORM, String.valueOf(contryCod.getSelectedCountryNameCode())));
                map.put("player_id", RequestBody.create(MultipartBody.FORM, spNotiToken.getString(NOTI_KEY , "")));

                dialog.show();
                Call<PreviewerRegisterResponse> callUserRegister = jsonApi.registerPreviewer(LocaleHelper.getLanguage(this), map, body);
                callUserRegister.enqueue(new Callback<PreviewerRegisterResponse>() {
                    @Override
                    public void onResponse(Call<PreviewerRegisterResponse> call, Response<PreviewerRegisterResponse> response) {
                        dialog.dismiss();
                        PreviewerRegisterResponse userRegisterResponse = response.body();
                        if (userRegisterResponse.getCode() == 200 && userRegisterResponse.getData() != null){
                            Toast.makeText(getApplicationContext() , userRegisterResponse.getMessage() , Toast.LENGTH_SHORT).show();
                            String token = userRegisterResponse.getData().getPreviewer().getToken();
                            tokenEditor.putString("token_key" , token);
                            tokenEditor.commit();
                            signUpEditor.putString("yes" , "previewer");
                            signUpEditor.apply();
                            saveVerification(userRegisterResponse.getData().getPreviewer().getIsVerified() == 1);
                            Toast.makeText(getApplicationContext(),userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext() , OTPActivity.class);
                            startActivity(i);
//                                finishAffinity();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PreviewerRegisterResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        termsChecker = findViewById(R.id.termsChecker);
//        termsChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (termsChecker.isChecked()){
//                    confirm.setEnabled(true);
//                }else {
//                    confirm.setEnabled(false);
//                }
//            }
//        });

    }

    private void saveVerification(boolean isVerified) {
        SplashScreen.signUpEditor.putBoolean("isVerified", isVerified);
        SplashScreen.signUpEditor.apply();
    }

    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(this,uri);
            //Create a file object using file path
            String fName = file.getName();
            if (!fName.isEmpty() && fName != null){
                pdfFile.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                lblFileName.setText(fName);
            }
            body = MultipartBody.Part.createFormData("identity_docs", fName, RequestBody.create(MediaType.parse("*/*"), file));
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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK && data != null) {
            // check condition
            if (data.getData() != null) {
                try {
                    uploadFile(data.getData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void callRegionstype(int id){
        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(LocaleHelper.getLanguage(this), new RegionParams(id));
        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
            @Override
            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
                GetRegionResponse regionResponse = response.body();
                if (response.code() == 200) {
                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
                    region1Spinner.setAdapter(regionAdapter);
                    region2Spinner.setAdapter(regionAdapter);
                    region3Spinner.setAdapter(regionAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCountries() {
        Call<CountriesResponse> countriesResponseCall = jsonApi.getCountries(LocaleHelper.getLanguage(this));
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
                    Toast.makeText(getApplicationContext() , countriesResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CountriesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCities(int countryId) {
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities(LocaleHelper.getLanguage(this), new CitiesListParams(countryId));
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
    }

    public void showHidePassword(View view) {
        if (isPasswordVisible) {
            profliePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            profliePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        profliePassword.setSelection(profliePassword.getText().length()); // Keep cursor at the end
        isPasswordVisible = !isPasswordVisible;
    }

    public void showHideConfirmPassword(View view) {
        if (isConfirmPasswordVisible) {
            confirmprofliePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            confirmprofliePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        confirmprofliePassword.setSelection(confirmprofliePassword.getText().length()); // Keep cursor at the end
        isConfirmPasswordVisible = !isConfirmPasswordVisible;
    }
}