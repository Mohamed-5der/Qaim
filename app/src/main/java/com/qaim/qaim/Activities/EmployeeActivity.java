package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.SplashScreen.sToken;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Fragments.EmployeeMainFragment;
import com.qaim.qaim.Models.LogoutRespone.LogOutResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yarolegovich.slidingrootnav.SlidingRootNav;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeActivity extends AppCompatActivity {
    BottomNavigationView mbn ;
    private SlidingRootNav slidingRootNav;
    FloatingActionButton fab ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    public static PregressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        token = sToken.getString("token_key" , "");

        // Main full screen hide status bar
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
        EmployeeMainFragment mainFragment = new EmployeeMainFragment ();
        loadFragment(mainFragment);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutCallAPI();
                SplashScreen.signUpEditor.putString("yes" , "");
                SplashScreen.signUpEditor.apply();
                SplashScreen.tokenEditor.putString("token_key" ,"" );
                SplashScreen.tokenEditor.apply();
                Intent i = new Intent(EmployeeActivity.this , SplashScreen.class);
                startActivity(i);
                finishAffinity();
            }
        });

    }

    private void logOutCallAPI(){
        Call<LogOutResponse> call = jsonApi.logOut("Bearer "+EmployeeActivity.token , SplashScreen.spNotiToken.getString("noti" , ""));
        call.enqueue(new Callback<LogOutResponse>() {
            @Override
            public void onResponse(Call<LogOutResponse> call, Response<LogOutResponse> response) {
                EmployeeActivity.dialog.dismiss();
                LogOutResponse logOutResponse = response.body();
                if (logOutResponse.getCode() == 200) {

                }
            }

            @Override
            public void onFailure(Call<LogOutResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().disallowAddToBackStack()
                .replace(R.id.frameLayout , fragment)
                .commit();
    }

}