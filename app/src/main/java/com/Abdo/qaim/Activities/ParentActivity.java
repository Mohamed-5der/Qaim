package com.Abdo.qaim.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.Abdo.qaim.Helper.SharedPrefs;
import com.Abdo.qaim.Interfaces.ICallback;
import com.Abdo.qaim.Models.LoginResponse.LoginResponse;
import com.Abdo.qaim.Retrofit.Retrofit;

public class ParentActivity extends AppCompatActivity, ICallback {

    SharedPrefs sharedPrefs;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = new SharedPrefs(this);
        retrofit = new Retrofit(this, true);
    }

    @Override
    public void onSuccess(Object response, String msg) {
        if (!msg.isEmpty()) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFail(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}