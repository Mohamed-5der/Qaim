package com.qaim.qaim.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomRegionAdapter;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {
    private static final String SINGIN_KEY = "singInKey";
    CardView cleintCard , companyCard , previewerCard;
    public static SharedPreferences spSignUp ;
    public static SharedPreferences.Editor signUpEditor ;
    public static SharedPreferences sToken ;
    public static SharedPreferences.Editor tokenEditor ;

    public static SharedPreferences spNotiToken ;
    public static SharedPreferences.Editor spNotiTokenEditor ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCityAdapter cityAdapter ;
    int cityId ;
    CustomRegionAdapter regionAdapter;
    int regionId_1;
    int regionId_2;
    int regionId_13;
    Spinner region1Spinner ,region2Spinner , region3Spinner;
    public static String acountType;
    public static PregressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        spSignUp = getSharedPreferences("signup" , MODE_PRIVATE);
        sToken = getSharedPreferences("token" , MODE_PRIVATE);
        spNotiToken = getSharedPreferences("noti" , MODE_PRIVATE);
        signUpEditor = spSignUp.edit();
        tokenEditor = sToken.edit();
        spNotiTokenEditor = spNotiToken.edit();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
            @Override
            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
                if (response.code() == 200) {
                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
                }

            }

            @Override
            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

        // splash full screen hide status bar
        if (Build.VERSION.SDK_INT < 16)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else
        {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String sign = spSignUp.getString("yes" , "");

                boolean verified = spSignUp.getBoolean("isVerified",false );

                if(verified) {
                    switch (sign) {
                        case "individualClient":
                            startActivity(new Intent(SplashScreen.this, MainActivity.class));
                            finishAffinity();
                            break;
                        case "company":
                            startActivity(new Intent(SplashScreen.this, CompanyActivity.class));
                            finishAffinity();
                            break;

                        case "previewer":
                            startActivity(new Intent(SplashScreen.this, PreviewerActivity.class));
                            finishAffinity();
                            break;

                        case "painter":
                            startActivity(new Intent(SplashScreen.this, EmployeeActivity.class));
                            Toast.makeText(getApplicationContext(), "splash painter error", Toast.LENGTH_LONG).show();
                            finishAffinity();
                            break;

                        case "reviewer":
                            startActivity(new Intent(SplashScreen.this, EmployeeActivity.class));
                            Toast.makeText(getApplicationContext(), "splash reviewer error", Toast.LENGTH_LONG).show();
                            finishAffinity();
                            break;

                        default:
                            showLoginDialog();
                    }
                }
            }
        } , 0);

        showLoginDialog();


    }
    // show login dialog
    private void showLoginDialog (){
        startActivity(new Intent(SplashScreen.this , LoginActivity.class));
        finishAffinity();
    }

}