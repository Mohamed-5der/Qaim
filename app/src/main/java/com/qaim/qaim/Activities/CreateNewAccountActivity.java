package com.qaim.qaim.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

public class CreateNewAccountActivity extends BaseActivity {
    CardView cleintCard, companyCard, previewerCard;
    ImageButton imageButton;
    public static PregressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        imageButton = findViewById(R.id.imageBtn);
        cleintCard = findViewById(R.id.client_card_view);
        companyCard = findViewById(R.id.company_card_view);
        previewerCard = findViewById(R.id.previewer_card_view);

        dialog = new PregressDialog(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        // on card views click
        cleintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateNewAccountActivity.this , ClientActivity.class);
                startActivity(i);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateNewAccountActivity.this , LoginActivity.class));
                finishAffinity();
            }
        });



//        String notiToken = LoginActivity.notiToken;

        companyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateNewAccountActivity.this , CompnayUserRegisterActivity.class);
                i.putExtra("user_type" , "company");
                startActivity(i);
            }
        });

        previewerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CreateNewAccountActivity.this , PreviewerRegisterActivity.class);
                startActivity(i);
            }
        });
    }
}