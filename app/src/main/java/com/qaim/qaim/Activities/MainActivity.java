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
import com.qaim.qaim.Fragments.AddRealStateFragment;
import com.qaim.qaim.Fragments.CompanyVipFragment;
import com.qaim.qaim.Fragments.EditProfileFragment;
import com.qaim.qaim.Fragments.MainFragment;
import com.qaim.qaim.Fragments.NotificationFragment;
import com.qaim.qaim.Fragments.OffersFragment;
import com.qaim.qaim.Fragments.OrdersFragment;
import com.qaim.qaim.Fragments.TermsAndConditionsFragment;
import com.qaim.qaim.Fragments.UserPaymentsFragment;
import com.qaim.qaim.Helper.Alert;
import com.qaim.qaim.Models.LogoutRespone.LogOutResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.UserProfileResponse.User;
import com.qaim.qaim.Models.UserProfileResponse.UserProfileResponse;
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


public class MainActivity extends BaseActivity implements MainFragment.AddRealstateFragmentListner {
    BottomNavigationView mbn ;
    private SlidingRootNav slidingRootNav;
    FloatingActionButton fab ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static String token ;
    public static PregressDialog dialog;
    public static Alert alert ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
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
        mbn = findViewById(R.id.bottomNaviView);
        slidingRootNavInflate(savedInstanceState);
        bbmShow();
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AddRealStateFragment fragment = new AddRealStateFragment();
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.frameLayout , fragment).commit();
        });
        alert = new Alert();
    }

    private void bbmShow (){


        mbn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.main:
                        MainFragment mainFragment = new MainFragment ();
                        loadFragment(mainFragment);
                        return true ;
                    case R.id.offers :
                        OffersFragment offersFragment = new   OffersFragment();
                        loadFragment(offersFragment);
                        return true ;
                    case R.id.orders :
                        OrdersFragment ordersFragment = new OrdersFragment();
                        loadFragment(ordersFragment);
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
                    case R.id.offers :
                        break;
                    case R.id.orders :
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
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//           Toast.makeText(getApplicationContext() , "اضغط على زر الرجوع أعلى الشاشة" , Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
////    }
//@   Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//        moveTaskToBack(true);
//        return true;
//    }
//    return super.onKeyDown(keyCode, event);
//}
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout , fragment).disallowAddToBackStack()
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
        MainActivity.dialog.show();
        Call<UserProfileResponse> call = jsonApi.getUserProfile("Bearer "+MainActivity.token);
        call.enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                MainActivity.dialog.dismiss();
                UserProfileResponse userProfileResponse = response.body();
                if (userProfileResponse.getCode() == 200) {
                    User user = userProfileResponse.getData().getUser();
                    String name = user.getName();
                    String imageURL = user.getImage();
                    TextView nv  = v.findViewById(R.id.userName);
                    ImageView imageView = v.findViewById(R.id.ProfilePhoto);
                    nv.setText(name);
                    Picasso.get().load(imageURL).fit().error(getBaseContext().getDrawable(R.drawable.icon)).into(imageView);
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onSlideRootMenuItemClick(){
        // on user name and profile image click
        LinearLayout profile = findViewById(R.id.profileRel);
        profile.setOnClickListener(view -> {
            EditProfileFragment editProfileFragment = new EditProfileFragment ();
            loadFragment(editProfileFragment);
            slidingRootNav.closeMenu(true);
        });
        ImageButton editProfileBtn = findViewById(R.id.editprofileBtn);
        editProfileBtn.setOnClickListener(view -> {
            EditProfileFragment editProfileFragment = new EditProfileFragment ();
            loadFragment(editProfileFragment);
            slidingRootNav.closeMenu(true);
        });

        ImageView preofilePhoto = findViewById(R.id.ProfilePhoto);
        preofilePhoto.setOnClickListener(view -> {
            EditProfileFragment editProfileFragment = new EditProfileFragment ();
            loadFragment(editProfileFragment);
            slidingRootNav.closeMenu(true);
        });

        TextView vip_company = findViewById(R.id.vipText);
        vip_company.setText(getString(R.string.vip_vcompanies));

        RelativeLayout companyVip = findViewById(R.id.companyVipRel);
        companyVip.setOnClickListener(view -> {
            CompanyVipFragment companyVipFragment = new CompanyVipFragment ();
            loadFragment(companyVipFragment);
            slidingRootNav.closeMenu(true);
        });

        // on payment click
        RelativeLayout payment = findViewById(R.id.paymentRel);
        payment.setOnClickListener(view -> {
            UserPaymentsFragment userPaymentsFragment = new UserPaymentsFragment ();
            loadFragment(userPaymentsFragment);
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
            NotificationFragment notificationFragment = new NotificationFragment ();
            loadFragment(notificationFragment);
            slidingRootNav.closeMenu(true);
        });

        TextView notitv = findViewById(R.id.notitv);
        notitv.setOnClickListener(view -> {
            NotificationFragment notificationFragment = new NotificationFragment ();
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


        // on balance click
        RelativeLayout myBalance = findViewById(R.id.balanceRel);
        myBalance.setVisibility(View.GONE);

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

        RelativeLayout logOut = findViewById(R.id.logOut1);
        logOut.setOnClickListener(view -> {
            logOutCallAPI();
            SplashScreen.signUpEditor.putString("yes" , "");
            SplashScreen.signUpEditor.apply();
            SplashScreen.tokenEditor.putString("token_key" ,"" );
            SplashScreen.tokenEditor.apply();
            SplashScreen.spNotiTokenEditor.putString("noti" , "");
            SplashScreen.spNotiTokenEditor.apply();
            Intent i = new Intent(MainActivity.this , SplashScreen.class);
            startActivity(i);
            finish();
        });


    }


    @Override
    public void onCardClick(int id) {

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
        Call<LogOutResponse> call = jsonApi.logOut("Bearer "+MainActivity.token , SplashScreen.spNotiToken.getString("noti" , ""));
        call.enqueue(new Callback<LogOutResponse>() {
            @Override
            public void onResponse(Call<LogOutResponse> call, Response<LogOutResponse> response) {
                MainActivity.dialog.dismiss();
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
}

