package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.SplashScreen.acountType;
import static com.qaim.qaim.Activities.SplashScreen.signUpEditor;
import static com.qaim.qaim.Activities.SplashScreen.tokenEditor;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.qaim.qaim.Classes.LoginParams;
import com.qaim.qaim.Models.LoginResponse.LoginResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private ActivityResultLauncher<String> requestPermissionLauncher ;

    public static SharedPreferences spNotiToken ;
    public static SharedPreferences.Editor spNotiTokenEditor ;
    public static String NOTI_KEY = "noti" ;
    public static String notiToken ;


    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static PregressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = this.getResources();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();dialog = new PregressDialog(this);
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        TextView createAccount = findViewById(R.id.createAccount);
        TextView forget = findViewById(R.id.forgetPassword);
        spNotiToken = getSharedPreferences(NOTI_KEY , MODE_PRIVATE);
        spNotiTokenEditor = spNotiToken.edit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        getNotificationPermission();

        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
//                        Toast.makeText(this, "Notifications permission granted",Toast.LENGTH_SHORT)
//                                .show();
                    } else
                    {
//                        Toast.makeText(this, "FCM can't post notifications without POST_NOTIFICATIONS permission",
//                                Toast.LENGTH_LONG).show();
                    }
                });


        createAccount.setMovementMethod(LinkMovementMethod.getInstance());
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this , CreateNewAccountActivity.class));
            }
        });
        forget.setMovementMethod(LinkMovementMethod.getInstance());
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , ResetPasswordActivity.class));
            }
        });
        EditText email = findViewById(R.id.emailEditText);
        EditText password = findViewById(R.id.passwordEditText);
        Button login =  findViewById(R.id.signUp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                Call<LoginResponse> call = jsonApi.login(new LoginParams(
                        String.valueOf(email.getText()) ,
                        String.valueOf(password.getText()) , spNotiToken.getString(NOTI_KEY , "")));
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        dialog.dismiss();
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getCode() == 200){
                            String token = loginResponse.getData().getUser().getToken();
                            acountType = loginResponse.getData().getAccountType();
                            tokenEditor.putString("token_key" , token);
                            tokenEditor.commit();
                            saveVerification(loginResponse.getData().getUser().getIsVerified() == 1);
                            switch (acountType){
                                case "user_user" :
                                case "user_company" :
                                    signUpEditor.putString("yes" , "individualClient");
                                    signUpEditor.apply();

                                    if (loginResponse.getData().getUser().getIsVerified() == 1) {
                                        Intent i = new Intent(getApplicationContext() , MainActivity.class);
                                        startActivity(i);
                                        finishAffinity();
                                        if (!loginResponse.getMessage().isEmpty()){
                                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent i = new Intent(getApplicationContext() , OTPActivity.class);
                                        startActivity(i);
                                    }
                                    break;
                                case "company" :
                                    signUpEditor.putString("yes" , "company");
                                    signUpEditor.apply();

                                    if (loginResponse.getData().getUser().getIsVerified() == 1) {
                                        Intent i2 = new Intent(getApplicationContext(), CompanyActivity.class);
                                        startActivity(i2);
                                        finishAffinity();
                                        if (!loginResponse.getMessage().isEmpty()) {
                                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent i2 = new Intent(getApplicationContext() , OTPActivity.class);
                                        startActivity(i2);
                                    }
                                    break;
                                case "previewer" :
                                    signUpEditor.putString("first_login" , "" + loginResponse.getData().getUser().getFirstLogin());
                                    signUpEditor.putString("yes" , "previewer");
                                    signUpEditor.apply();

                                    if (loginResponse.getData().getUser().getIsVerified() == 1) {
                                        Intent i3 = new Intent(getApplicationContext(), PreviewerActivity.class);
                                        startActivity(i3);
                                        finishAffinity();
                                        if (!loginResponse.getMessage().isEmpty()) {
                                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Intent i3 = new Intent(getApplicationContext() , OTPActivity.class);
                                        startActivity(i3);
                                    }
                                    break;
                                case "painter" :
                                    Intent i4 = new Intent(getApplicationContext() , EmployeeActivity.class);
                                    signUpEditor.putString("yes" , "painter");
                                    signUpEditor.apply();
                                    startActivity(i4);
                                    finishAffinity();
                                    if (!loginResponse.getMessage().isEmpty()){
                                        Toast.makeText(getApplicationContext(), "login painter error", Toast.LENGTH_LONG).show();
                                    }                                    break;

                                case "reviewer" :
                                    Intent i5 = new Intent(getApplicationContext() , EmployeeActivity.class);
                                    signUpEditor.putString("yes" , "reviewer");
                                    signUpEditor.apply();
                                    startActivity(i5);
                                    finishAffinity();
                                    if (!loginResponse.getMessage().isEmpty()){
                                        Toast.makeText(getApplicationContext(), "login reviewer error", Toast.LENGTH_LONG).show();
                                    }                                    break;
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token

                        notiToken = task.getResult();
                        spNotiTokenEditor.putString(NOTI_KEY ,notiToken);
                        spNotiTokenEditor.commit();

//                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        askNotificationPermission();


    }

    private void askNotificationPermission() {
        // This is only necessary for API Level > 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }

    }

    private void saveVerification(boolean isVerified) {
        SplashScreen.signUpEditor.putBoolean("isVerified", isVerified);
        SplashScreen.signUpEditor.apply();
    }

    public void getNotificationPermission(){
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // allow

                }  else {
                    //deny
                }
                return;

        }

    }
}
