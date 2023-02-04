package com.Abdo.qaim.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.Abdo.qaim.Activities.MainActivity;
import com.Abdo.qaim.Classes.LoginParams;
import com.Abdo.qaim.Helper.SharedPrefs;
import com.Abdo.qaim.Interfaces.ICallback;
import com.Abdo.qaim.Models.LoginResponse.LoginResponse;
import com.Abdo.qaim.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    API API;
    Context context;
    KProgressHUD progressDialog;
    Integer pageSize = 50;
    boolean showDialog;

    public Retrofit(Context appContext, boolean showDialog) {
        this.context = appContext;
        this.showDialog = showDialog;
        final OkHttpClient okHttpClient = new OkHttpClient
                .Builder().readTimeout(120, TimeUnit.SECONDS)
//                .addInterceptor(new RequestInterceptor())
                .connectTimeout(120, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://qaim.app").addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build();
        API = retrofit.create(API.class);

        progressDialog = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    public void login(String email, String password, ICallback callback) {
        if (showDialog) {
            progressDialog.show();
        }

        Call<LoginResponse> call = API.login(new LoginParams(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (showDialog) {
                    progressDialog.dismiss();
                }
                callback.onSuccess(response, response.message());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (showDialog) {
                    progressDialog.dismiss();
                }
                callback.onFail(t.getMessage());
            }
        });
    }
}
