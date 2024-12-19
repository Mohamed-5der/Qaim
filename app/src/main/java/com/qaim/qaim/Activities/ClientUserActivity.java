package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.LoginActivity.NOTI_KEY;
import static com.qaim.qaim.Activities.LoginActivity.spNotiToken;
import static com.qaim.qaim.Activities.SplashScreen.signUpEditor;
import static com.qaim.qaim.Activities.SplashScreen.tokenEditor;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatDelegate;

import com.hbb20.CountryCodePicker;
import com.qaim.qaim.Classes.CitiesListParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomCountryAdapter;
import com.qaim.qaim.Classes.UserRegisterParms;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CountriesResponse.CountriesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserRegisterResponse.UserRegisterResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUserActivity extends BaseActivity {
    CheckBox termsChecker ;
    ImageButton imageButton;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    Spinner citySpinner, countrySpinner ;
    CustomCountryAdapter countryAdapter ;
    CustomCityAdapter cityAdapter ;
    int countryId ;
    int cityId ;
    public static PregressDialog dialog;
    Alert alert ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_user);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        countrySpinner = findViewById(R.id.countrySpinner);
        citySpinner = findViewById(R.id.citySpinner);

        getCountries();

        imageButton = findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
           startActivity(new Intent(ClientUserActivity.this , ClientActivity.class));
           finishAffinity();
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


        singUp.setOnClickListener(view -> {

            if (contryCod.isValidFullNumber() == false) {
                Toast.makeText(getApplicationContext(), R.string.phone_number_incorrect, Toast.LENGTH_SHORT).show();
            } else if (!profliePassword.getText().toString().equals(confirmprofliePassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), R.string.password_does_not_match, Toast.LENGTH_SHORT).show();
            } else {
                UserRegisterParms userRegisterParms = new UserRegisterParms(
                        String.valueOf(proflieName.getText()),
                        String.valueOf(profliePhone.getText()),
                        String.valueOf(proflieEmail.getText()),
                        String.valueOf(profliePassword.getText()),
                        String.valueOf(contryCod.getSelectedCountryNameCode()),
                        "user_user", countryId , cityId , spNotiToken.getString(NOTI_KEY , ""));

                dialog.show();
                Call<UserRegisterResponse> callUserRegister = jsonApi.registerUser(LocaleHelper.getLanguage(this), userRegisterParms);
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
                            saveVerification(userRegisterResponse.getData().getUser().getIsVerified() == 1);
                            Toast.makeText(getApplicationContext(), userRegisterResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), OTPActivity.class);
                            startActivity(i);
//                                finishAffinity();
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
        });

    }

    private void saveVerification(boolean isVerified) {
        SplashScreen.signUpEditor.putBoolean("isVerified", isVerified);
        SplashScreen.signUpEditor.apply();
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
}