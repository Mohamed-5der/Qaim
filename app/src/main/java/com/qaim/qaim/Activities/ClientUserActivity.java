package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.LoginActivity.NOTI_KEY;
import static com.qaim.qaim.Activities.LoginActivity.spNotiToken;
import static com.qaim.qaim.Activities.SplashScreen.signUpEditor;
import static com.qaim.qaim.Activities.SplashScreen.tokenEditor;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.UserRegisterParms;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserRegisterResponse.UserRegisterResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.hbb20.CountryCodePicker;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUserActivity extends AppCompatActivity {
    CheckBox termsChecker ;
    ImageButton imageButton;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCityAdapter cityAdapter ;
    int cityId ;
    public static PregressDialog dialog;
    Alert alert ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_user);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = this.getResources();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Spinner citySpinner = findViewById(R.id.citySpinner);
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
                CitiesResponse citiesResponse = response.body();
                if (citiesResponse.getCode() == 200) {
                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
                    citySpinner.setAdapter(cityAdapter);
                    Toast.makeText(getApplicationContext() , citiesResponse.getMessage() , Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
        imageButton = findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ClientUserActivity.this , ClientActivity.class));
               finishAffinity();
            }
        });
        dialog = new PregressDialog(this);
        alert = new Alert();
        EditText proflieName = findViewById(R.id.profileEditText);
        EditText proflieEmail = findViewById(R.id.emailEditText);
        EditText profliePhone = findViewById(R.id.phoneEditText);
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

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId = (int) cityAdapter.getItemId(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        citySpinner.setAdapter(cityAdapter);
//        EditText proflieLicense = findViewById(R.id.licenceEditText);
        EditText profliePassword = findViewById(R.id.passwordEditText);
        EditText confirmprofliePassword = findViewById(R.id.confirmPasswordEditText);
        Button singUp = findViewById(R.id.signUp);

        termsChecker = findViewById(R.id.termsChecker);
        termsChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (termsChecker.isChecked()){
                    alert.creatTermsDialog(retrofit , jsonApi , singUp , ClientUserActivity.this);
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
                    UserRegisterParms userRegisterParms = new UserRegisterParms(
                            String.valueOf(proflieName.getText()),
                            String.valueOf(profliePhone.getText()),
                            String.valueOf(proflieEmail.getText()),
                            String.valueOf(profliePassword.getText()),
                            String.valueOf(contryCod.getSelectedCountryNameCode()),
                            "user_user", cityId , spNotiToken.getString(NOTI_KEY , ""));

                    dialog.show();
                    Call<UserRegisterResponse> callUserRegister = jsonApi.registerUser(userRegisterParms);
                    callUserRegister.enqueue(new Callback<UserRegisterResponse>() {
                        @Override
                        public void onResponse(Call<UserRegisterResponse> call, Response<UserRegisterResponse> response) {
                           dialog.dismiss();
                            UserRegisterResponse userRegisterResponse = response.body();
                            if (userRegisterResponse.getCode() == 200) {
                                String token = userRegisterResponse.getData().getUser().getToken();
                                tokenEditor.putString("token_key", token);
                                tokenEditor.commit();
                                signUpEditor.putString("yes", "individualClient");
                                signUpEditor.apply();
                                Toast.makeText(getApplicationContext(), userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finishAffinity();
                            } else {
                                Toast.makeText(getApplicationContext(), userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<UserRegisterResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}