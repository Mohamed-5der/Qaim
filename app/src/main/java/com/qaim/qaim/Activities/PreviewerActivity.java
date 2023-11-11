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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.qaim.qaim.Fragments.AboutUsFragment;
import com.qaim.qaim.Fragments.EditPreviewerProfileFragment;
import com.qaim.qaim.Fragments.MainFragment;
import com.qaim.qaim.Fragments.PreviewerBalance;
import com.qaim.qaim.Fragments.PreviewerBusinessInProgressFragment;
import com.qaim.qaim.Fragments.PreviewerNotiFragment;
import com.qaim.qaim.Fragments.PreviewerOrdersFragment;
import com.qaim.qaim.Fragments.PreviewerPaymentsFragment;
import com.qaim.qaim.Fragments.TermsAndConditionsFragment;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.Models.GetProviewerProfile.Previewer;
import com.qaim.qaim.Models.GetProviewerProfile.PreviewerProfileResponse;
import com.qaim.qaim.Models.LogoutRespone.LogOutResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerActivity extends AppCompatActivity implements MainFragment.AddRealstateFragmentListner {
    BottomNavigationView mbn ;

    private SlidingRootNav slidingRootNav;
    FloatingActionButton fab ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    String imageURL , Name ;
    public static PregressDialog dialog;
    public static Alert alert ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewer);

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        token = sToken.getString("token_key" , "");




        mbn = findViewById(R.id.bottomNaviView);
        slidingRootNavInflate(savedInstanceState);
        bbmShow();
        fab = findViewById(R.id.fab);
        alert = new Alert();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerPaymentsFragment paymentFragment = new PreviewerPaymentsFragment ();
                loadFragment(paymentFragment);
            }
        });
    }
    private void bbmShow (){


        mbn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.main:
                         PreviewerBusinessInProgressFragment previewerBusinessInProgressFragment = new PreviewerBusinessInProgressFragment();
                         loadFragment(previewerBusinessInProgressFragment);
                        return true ;
                    case R.id.noti:
                        PreviewerNotiFragment notificationFragment = new PreviewerNotiFragment ();
                        loadFragment(notificationFragment);
                        return true ;
                    case R.id.orders :
                        PreviewerOrdersFragment previewerOrdersFragment = new PreviewerOrdersFragment();
                        loadFragment(previewerOrdersFragment);
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
                        PreviewerBusinessInProgressFragment previewerBusinessInProgressFragment = new PreviewerBusinessInProgressFragment();
                        loadFragment(previewerBusinessInProgressFragment);
                        break;
                    case R.id.noti:
                        PreviewerNotiFragment notificationFragment = new PreviewerNotiFragment ();
                        loadFragment(notificationFragment);
                        break;
                    case R.id.orders :
                        PreviewerOrdersFragment previewerOrdersFragment = new PreviewerOrdersFragment();
                        loadFragment(previewerOrdersFragment);
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

        onSlideRootMenuItemClick();
        dialog.show();

        Call<PreviewerProfileResponse> call = jsonApi.getPreviewerProfile("Bearer " + PreviewerActivity.token);
        call.enqueue(new Callback<PreviewerProfileResponse>() {
            @Override
            public void onResponse(Call<PreviewerProfileResponse> call, Response<PreviewerProfileResponse> response) {
                dialog.dismiss();
                PreviewerProfileResponse previewerProfileResponse = response.body();
                if (previewerProfileResponse.getCode() == 200) {
                    Previewer previewer = previewerProfileResponse.getData().getPreviewer();
                    String name = previewer.getName();
                    String imageURL = previewer.getImage();
                    TextView nv  = v.findViewById(R.id.userName);
                    ImageView imageView = v.findViewById(R.id.ProfilePhoto);
                    nv.setText(name);
                    Picasso.get().load(imageURL).fit().error(getBaseContext().getDrawable(R.drawable.icon)).into(imageView);
                }
            }
            @Override
            public void onFailure(Call<PreviewerProfileResponse> call, Throwable t) {
                Toast.makeText(PreviewerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onSlideRootMenuItemClick(){
        // on user name and profile image click
        LinearLayout profile = findViewById(R.id.profileRel);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPreviewerProfileFragment editPreviewerProfileFragment = new EditPreviewerProfileFragment ();
                loadFragment(editPreviewerProfileFragment);
                slidingRootNav.closeMenu(true);
            }
        });
        ImageButton editProfileBtn = findViewById(R.id.editprofileBtn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPreviewerProfileFragment editPreviewerProfileFragment = new EditPreviewerProfileFragment ();
                loadFragment(editPreviewerProfileFragment);
                slidingRootNav.closeMenu(true);
            }
        });


        // on payment click
        RelativeLayout payment = findViewById(R.id.paymentRel);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerPaymentsFragment paymentFragment = new PreviewerPaymentsFragment ();
                loadFragment(paymentFragment);
                slidingRootNav.closeMenu(true);
            }
        });
        // terms And Condtions
        RelativeLayout termsAndCondtions = findViewById(R.id.terms);
        termsAndCondtions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsAndConditionsFragment termsAndConditionsFragment = new TermsAndConditionsFragment ();
                loadFragment(termsAndConditionsFragment);
                slidingRootNav.closeMenu(true);
            }
        });
        // on noti icon and on text click
        ImageView noti = findViewById(R.id.noti);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerNotiFragment notificationFragment = new PreviewerNotiFragment ();
                loadFragment(notificationFragment);
                slidingRootNav.closeMenu(true);
            }
        });
        TextView notitv = findViewById(R.id.notitv);
        notitv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerNotiFragment notificationFragment = new PreviewerNotiFragment ();
                loadFragment(notificationFragment);
                slidingRootNav.closeMenu(true);
            }
        });
        // on switch change
        Switch switchNoti = findViewById(R.id.switchNoti);
        switchNoti.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        RelativeLayout myBalance = findViewById(R.id.balanceRel);
        myBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerBalance previewerBalance = new PreviewerBalance();
                loadFragment(previewerBalance);
                slidingRootNav.closeMenu(true);
            }
        });

        // on About Us click
        RelativeLayout aboutUs = findViewById(R.id.aboutUsRel);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutUsFragment aboutUsFragment = new AboutUsFragment ();
                getSupportFragmentManager()
                        .beginTransaction().addToBackStack(null)
                        .replace(R.id.frameLayout , aboutUsFragment)
                        .commit();
                slidingRootNav.closeMenu(true);
            }
        });

        RelativeLayout logOut = findViewById(R.id.logOut1);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutCallAPI();
                SplashScreen.signUpEditor.putString("yes" , "");
                SplashScreen.signUpEditor.apply();
                SplashScreen.tokenEditor.putString("token_key" ,"" );
                SplashScreen.tokenEditor.apply();
                SplashScreen.spNotiTokenEditor.putString("noti" , "");
                SplashScreen.spNotiTokenEditor.apply();
                Intent i = new Intent(PreviewerActivity.this , SplashScreen.class);
                startActivity(i);
                finish();
            }
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
        Call<LogOutResponse> call = jsonApi.logOut("Bearer "+PreviewerActivity.token , SplashScreen.spNotiToken.getString("noti" , ""));
        call.enqueue(new Callback<LogOutResponse>() {
            @Override
            public void onResponse(Call<LogOutResponse> call, Response<LogOutResponse> response) {
                PreviewerActivity.dialog.dismiss();
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
    @Override
    public void onCardClick(int id) {

    }
}