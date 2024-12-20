package com.qaim.qaim.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import java.util.concurrent.Executor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends BaseActivity {
    private static final String SINGIN_KEY = "singInKey";
    public static SharedPreferences spSignUp ;
    public static SharedPreferences.Editor signUpEditor ;
    public static SharedPreferences sToken ;
    public static SharedPreferences.Editor tokenEditor ;

    public static SharedPreferences spNotiToken ;
    public static SharedPreferences.Editor spNotiTokenEditor ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
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
        new Handler().postDelayed(() -> {
            String sign = spSignUp.getString("yes" , "");
            boolean verified = spSignUp.getBoolean("isVerified",false );

            if (sign.isEmpty() || !verified) {
                showLoginDialog();
            } else {
//                startBio();
                checkRedirect();
            }

        }, 0);
    }
    // show login dialog
    private void showLoginDialog (){
        startActivity(new Intent(SplashScreen.this , LoginActivity.class));
        finishAffinity();
    }

    private void checkRedirect() {
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
                    Toast.makeText(getApplicationContext(), R.string.splash_painter_error, Toast.LENGTH_LONG).show();
                    finishAffinity();
                    break;

                case "reviewer":
                    startActivity(new Intent(SplashScreen.this, EmployeeActivity.class));
                    Toast.makeText(getApplicationContext(), R.string.splash_reviewer_error, Toast.LENGTH_LONG).show();
                    finishAffinity();
                    break;

                default:
                    showLoginDialog();
            }
        }
    }

    private void startBio() {
        // Check if biometric authentication is available
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                initBiometricPrompt();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, R.string.no_biometric_hardware_available, Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, R.string.biometric_hardware_unavailable, Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, R.string.no_biometric_data_enrolled, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode,
                                                      @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(SplashScreen.this, getString(R.string.authentication_error) + errString,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationSucceeded(
                            @NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Toast.makeText(SplashScreen.this, R.string.authentication_succeeded,
                                Toast.LENGTH_SHORT).show();
                        // Proceed with the login logic
                        checkRedirect();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(SplashScreen.this, R.string.authentication_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_login))
                .setSubtitle(getString(R.string.log_in_using_your_biometric_credential))
                .setNegativeButtonText(getString(R.string.cancel))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                .build();

        biometricPrompt.authenticate(promptInfo);
    }

}