package com.qaim.qaim.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.otpview.OTPListener;
import com.otpview.OTPTextView;
import com.qaim.qaim.Classes.OTPParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.OtpResponse.OtpResponse;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPActivity extends AppCompatActivity {

    private OTPTextView otpTextView;
    private JsonApi jsonApi;
    private SharedPreferences spSignUp;
    private String authToken;
    String otpCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        otpTextView = findViewById(R.id.otp_view);
        Button confirm = findViewById(R.id.confirmOTp);
        ImageButton back = findViewById(R.id.btnBack);
        spSignUp = getSharedPreferences("signup", MODE_PRIVATE);

        String user_type = spSignUp.getString("yes", "");
        authToken = SplashScreen.sToken.getString("token_key", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonApi = retrofit.create(JsonApi.class);

        confirm.setOnClickListener(v -> {
            if (otpCode.length() < 4) {
                return;
            }
            switch (user_type) {
                case "individualClient":
                    verifyUser(otpCode);
                    break;
                case "company":
                    verifyCompany(otpCode);
                    break;
                case "previewer":
                    verifyPreviewer(otpCode);
                    break;
            }
        });

        otpTextView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                // Do nothing
            }

            @Override
            public void onOTPComplete(String otp) {
                otpCode = otp;
            }
        });
        // Uncomment if you want the back button to finish the activity
        // back.setOnClickListener(v -> finishAffinity());
    }

    private void verifyCompany(String otp) {
        Call<OtpResponse> call = jsonApi.verifyCompany("bearer " + authToken, new OTPParams(otp));
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse != null && otpResponse.getData().getUser().getIsVerified() == 1) {
                        saveVerification(true);
                        startActivity(new Intent(OTPActivity.this, CompanyActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyUser(String otp) {
        Call<OtpResponse> call = jsonApi.verifyUser("bearer " + authToken, new OTPParams(otp));
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse != null && otpResponse.getData().getUser().getIsVerified() == 1) {
                        saveVerification(true);
                        startActivity(new Intent(OTPActivity.this, MainActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyPreviewer(String otp) {
        Call<OtpResponse> call = jsonApi.verifyPreviewer("bearer " + authToken, new OTPParams(otp));
        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if (response.isSuccessful()) {
                    OtpResponse otpResponse = response.body();
                    if (otpResponse != null && otpResponse.getData().getUser().getIsVerified() == 1) {
                        saveVerification(true);
                        startActivity(new Intent(OTPActivity.this, PreviewerActivity.class));
                        finishAffinity();
                    } else {
                        Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(OTPActivity.this, "كود التفعيل غير صحيح", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                Toast.makeText(OTPActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveVerification(boolean isVerified) {
        SplashScreen.signUpEditor.putBoolean("isVerified", isVerified);
        SplashScreen.signUpEditor.apply();
    }
}