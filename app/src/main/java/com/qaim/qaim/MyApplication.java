package com.qaim.qaim;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.onCreate(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleHelper.onCreate(this);
    }
}