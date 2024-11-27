package com.qaim.qaim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qaim.qaim.Classes.ResetPasswordParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.ResetPassword.ResetPasswordResponse;
import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPasswordActivity extends AppCompatActivity {
    Retrofit retrofit ;
    JsonApi jsonApi ;
    public static PregressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);

        EditText email = findViewById(R.id.emailEditText);
        Button sendPasswordBtn = findViewById(R.id.signUp);dialog = new PregressDialog(this);
        sendPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText = email.getText().toString();
                    dialog.show();
                    Call<ResetPasswordResponse> call = jsonApi.resetPassword(new ResetPasswordParams(String.valueOf(email.getText())));
                    call.enqueue(new Callback<ResetPasswordResponse>() {
                        @Override
                        public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                            dialog.dismiss();
                            ResetPasswordResponse resetPasswordResponse = response.body();
                            if (resetPasswordResponse.getCode() == 200){
                                String msg = resetPasswordResponse.getData().getMessage();
                                Toast.makeText(getApplicationContext() ,msg , Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), resetPasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });}
        });


        ImageButton imageButton = findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetPasswordActivity.this , LoginActivity.class));
                finishAffinity();
            }
        });
    }
}