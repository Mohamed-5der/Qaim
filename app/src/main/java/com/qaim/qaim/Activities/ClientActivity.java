package com.qaim.qaim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

public class ClientActivity extends BaseActivity {



    CardView invidualCardView , companyCardView ;
    ImageButton imageButton;
    public static PregressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        invidualCardView= findViewById(R.id.individual_card_view);
        companyCardView= findViewById(R.id.client_company_card_view);
        imageButton = findViewById(R.id.imageBtn);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        dialog = new PregressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClientActivity.this , CreateNewAccountActivity.class));
                finishAffinity();
            }
        });

        invidualCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(ClientActivity.this , ClientUserActivity.class));
            }
        });

        companyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientActivity.this , CompnayUserRegisterActivity.class);
                i.putExtra("user_type" , "user_company");
                startActivity(i);
            }
        });
    }
}