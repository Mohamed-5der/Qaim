package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.SplashScreen.sToken;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qaim.qaim.Fragments.AboutUsFragment;
import com.qaim.qaim.Fragments.CompanyBalance;
import com.qaim.qaim.Fragments.CompanyListOfRealstateFragment;
import com.qaim.qaim.Fragments.CompanyPaymentsFragment;
import com.qaim.qaim.Fragments.CompanyProfileFragment;
import com.qaim.qaim.Fragments.CompanyProjectsFragment;
import com.qaim.qaim.Fragments.CompanySetTeamFragment;
import com.qaim.qaim.Fragments.NotificationCompanyFragment;
import com.qaim.qaim.Fragments.TermsAndConditionsFragment;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.Models.CompanyProfile.Company;
import com.qaim.qaim.Models.CompanyProfile.CompanyProfileResponse;
import com.qaim.qaim.Models.LogoutRespone.LogOutResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.vip.VipRequestResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends BaseActivity {

    BottomNavigationView mbn ;

    private SlidingRootNav slidingRootNav;
    FloatingActionButton fab ;

    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    String imageURL , name ;
    public static PregressDialog dialog;
    public static Alert alert ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        token = sToken.getString("token_key" , "");
        alert = new Alert();
        // full screen hide status bar
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
        mbn = findViewById(R.id.bottomNaviView);
        slidingRootNavInflate(savedInstanceState);
        bbmShow();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            CompanyPaymentsFragment companyPaymentsFragment = new CompanyPaymentsFragment ();
            loadFragment(companyPaymentsFragment);
        });
    }
    private void bbmShow (){


        mbn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.main:
                        CompanyListOfRealstateFragment mainFragment = new CompanyListOfRealstateFragment ();
                        loadFragment(mainFragment);
                        return true ;
                    case R.id.team:
                       CompanySetTeamFragment setTeamFragment = new CompanySetTeamFragment ();
                       loadFragment(setTeamFragment);
                        return true ;
                    case R.id.projects:
                        CompanyProjectsFragment companyProjectsFragment = new CompanyProjectsFragment();
                        loadFragment(companyProjectsFragment);
                        return true ;
                    case R.id.more :
                        slidingRootNav.openMenu(true);
                        return true ;
                }

                return false;
            }
        });

        mbn.setSelectedItemId(R.id.main);

        mbn.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.main:

                    break;
                    case R.id.team:

                        break;
                    case R.id.projects:

                        break;
                    case R.id.more :
                        if (slidingRootNav.isMenuClosed()){
                            slidingRootNav.openMenu(true);
                        }else {
                            slidingRootNav.closeMenu(true);
                        }
                        break;
                }

            }
        });

    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().disallowAddToBackStack()
                .replace(R.id.frameLayout , fragment)
                .commit();
    }
    private void slidingRootNavInflate(Bundle savedInstanceState){
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.menu_left_drawer, null ,false);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuView(v)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        CompanyActivity.dialog.show();
        Call<CompanyProfileResponse> call = jsonApi.getCompanyProfile("Bearer "+CompanyActivity.token);
        call.enqueue(new Callback<CompanyProfileResponse>() {
            @Override
            public void onResponse(Call<CompanyProfileResponse> call, Response<CompanyProfileResponse> response) {
                CompanyActivity.dialog.dismiss();
                CompanyProfileResponse companyProfileResponse = response.body();
                if (response.code() == 200) {
                    Company company = companyProfileResponse.getData().getCompany();
                    if (company != null) {
                        name = company.getName();
                        imageURL = company.getImage();
                        TextView nv = v.findViewById(R.id.userName);
                        ImageView imageView = v.findViewById(R.id.ProfilePhoto);
                        nv.setText(name);
                        Picasso.get().load(imageURL).fit().error(getBaseContext().getDrawable(R.drawable.icon)).into(imageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyProfileResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

        onSlideRootMenuItemClick();



    }
    public void onSlideRootMenuItemClick(){
        // on user name and profile image click
        LinearLayout profile = findViewById(R.id.profileRel);
        profile.setOnClickListener(view -> {
            CompanyProfileFragment companyProfileFragment = new CompanyProfileFragment ();
            loadFragment(companyProfileFragment);
            slidingRootNav.closeMenu(true);
        });
        ImageButton editProfileBtn = findViewById(R.id.editprofileBtn);
        editProfileBtn.setOnClickListener(view -> {
            CompanyProfileFragment companyProfileFragment = new CompanyProfileFragment ();
            loadFragment(companyProfileFragment);
            slidingRootNav.closeMenu(true);
        });

        TextView vip_company = findViewById(R.id.vipText);
        vip_company.setText(getString(R.string.vip_company));

        // on payment click
        RelativeLayout companyVipRel = findViewById(R.id.companyVipRel);
        companyVipRel.setOnClickListener(view -> requestVipCompany());
        // on payment click
        RelativeLayout payment = findViewById(R.id.paymentRel);
        payment.setOnClickListener(view -> {
            CompanyPaymentsFragment companyPaymentsFragment = new CompanyPaymentsFragment ();
            loadFragment(companyPaymentsFragment);
            slidingRootNav.closeMenu(true);
        });

        // terms And Condtions
        RelativeLayout termsAndCondtions = findViewById(R.id.terms);
        termsAndCondtions.setOnClickListener(view -> {
            TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment ();
            loadFragment(termsAndConditionsFragment);
            slidingRootNav.closeMenu(true);
        });
        // on noti icon and on text click
        ImageView noti = findViewById(R.id.noti);
        noti.setOnClickListener(view -> {
            NotificationCompanyFragment notificationFragment =new NotificationCompanyFragment();
            loadFragment(notificationFragment);
            slidingRootNav.closeMenu(true);
        });
        TextView notitv = findViewById(R.id.notitv);
        notitv.setOnClickListener(view -> {
            NotificationCompanyFragment notificationFragment =new NotificationCompanyFragment();
            loadFragment(notificationFragment);
            slidingRootNav.closeMenu(true);
        });
        // on switch change
        Switch switchNoti = findViewById(R.id.switchNoti);
        switchNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        // on About Us click
        RelativeLayout aboutUs = findViewById(R.id.aboutUsRel);
        aboutUs.setOnClickListener(view -> {
            AboutUsFragment aboutUsFragment = new AboutUsFragment ();
            getSupportFragmentManager()
                    .beginTransaction().addToBackStack(null)
                    .replace(R.id.frameLayout , aboutUsFragment)
                    .commit();
            slidingRootNav.closeMenu(true);

        });


        // on balance click
        RelativeLayout myBalance = findViewById(R.id.balanceRel);
        myBalance.setOnClickListener(view -> {
             CompanyBalance companyBalance = new CompanyBalance();
             loadFragment(companyBalance);
            slidingRootNav.closeMenu(true);
        });

        RelativeLayout logOut = findViewById(R.id.logOut1);
        logOut.setOnClickListener(view -> {
            logOutCallAPI();
            SplashScreen.signUpEditor.putString("yes" , "");
            SplashScreen.signUpEditor.apply();
            SplashScreen.tokenEditor.putString("token_key" ,"" );
            SplashScreen.tokenEditor.apply();
            SplashScreen.spNotiTokenEditor.putString("noti" , "");
            SplashScreen.spNotiTokenEditor.apply();
            Intent i = new Intent(CompanyActivity.this , SplashScreen.class);
            startActivity(i);
            finish();
        });
    }
    @Override
    public void onBackPressed() {

        if (slidingRootNav.isMenuOpened()){
            slidingRootNav.closeMenu(true);
        }else {
            super.onBackPressed();
        }
        return;
    }
    private void logOutCallAPI(){
        Call<LogOutResponse> call = jsonApi.logOut("Bearer "+CompanyActivity.token , SplashScreen.spNotiToken.getString("noti" , ""));
        call.enqueue(new Callback<LogOutResponse>() {
            @Override
            public void onResponse(Call<LogOutResponse> call, Response<LogOutResponse> response) {
                CompanyActivity.dialog.dismiss();
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
    public void requestVipCompany(){
        slidingRootNav.closeMenu(true);
        Call<VipRequestResponse> call = jsonApi.sendVipRequest("Bearer "+CompanyActivity.token);
        call.enqueue(new Callback<VipRequestResponse>() {
            @Override
            public void onResponse(Call<VipRequestResponse> call, Response<VipRequestResponse> response) {
                CompanyActivity.dialog.dismiss();
                VipRequestResponse vipRequestResponse = response.body();
                if (vipRequestResponse != null && vipRequestResponse.getMessage() != null) {
                    Toast.makeText(getApplicationContext() , vipRequestResponse.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VipRequestResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }
}