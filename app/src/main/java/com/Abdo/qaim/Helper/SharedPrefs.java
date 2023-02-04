package com.Abdo.qaim.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.Abdo.qaim.R;

public class SharedPrefs {
    Context context;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    protected SharedPreferences getPreferences() {
        return context.getSharedPreferences(context.getResources().getString(R.string.shared_prefrences), Activity.MODE_PRIVATE);
    }

    public void set(int key, String Value) {
        SharedPreferences prefs = getPreferences();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(context.getResources().getString(key), Value);
        editor.commit();
    }

    public String get(int key, String defValue) {
        SharedPreferences prefs = getPreferences();
        return prefs.getString(context.getResources().getString(key), defValue);
    }
}
