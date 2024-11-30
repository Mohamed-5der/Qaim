package com.qaim.qaim.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qaim.qaim.Models.AppInfo.AppInfoResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Alert {



    public Alert() {

    }


    public void creatDialog(String msg , Context context){

        if (msg != null && !msg.equals("")){

            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert_dialog);
            TextView alertMsg = dialog.findViewById(R.id.alertMsg);
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.alertAnimation ;
            dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
            alertMsg.setText(msg);
        }

    }
    public void creatTermsDialog(Retrofit retrofit ,JsonApi jsonApi , Button b , Context context){

            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_terms_dialog);
            TextView terms = dialog.findViewById(R.id.terms);
            Button acceptBtn = dialog.findViewById(R.id.acceptBtn);
            Button rejectBtn = dialog.findViewById(R.id.rejectBtn);
            Call<AppInfoResponse> call = jsonApi.getAppInfo();
            call.enqueue(new Callback<AppInfoResponse>() {
                @Override
                public void onResponse(Call<AppInfoResponse> call, Response<AppInfoResponse> response) {
                    AppInfoResponse appInfoResponse = response.body();
                    if (response.code() == 200) {
                        terms.setText(Html.fromHtml("" +appInfoResponse.getData().getInfo().getTerms()));
                    }else {
                        Toast.makeText(context , appInfoResponse.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AppInfoResponse> call, Throwable t) {
                    Toast.makeText(context , t.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            acceptBtn.setOnClickListener(view -> {
                b.setEnabled(true);
                b.getBackground().setColorFilter(Color.parseColor("#9A7232") , PorterDuff.Mode.MULTIPLY);
                dialog.dismiss();
            });
            rejectBtn.setOnClickListener(view -> {
                b.setEnabled(false);
                dialog.dismiss();
            });

    }

    public void crateMsg(String msg , Context context){
        if (msg != null && !msg.equals("")){
            Toast.makeText(context , msg , Toast.LENGTH_SHORT).show();
        }
    }
}
