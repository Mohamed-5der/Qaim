package com.qaim.qaim.Activities;

import static com.qaim.qaim.Activities.LoginActivity.NOTI_KEY;
import static com.qaim.qaim.Activities.LoginActivity.spNotiToken;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.qaim.qaim.PregressDialog;
import com.qaim.qaim.R;

import java.util.Locale;

public class CreateNewAccountActivity extends AppCompatActivity {
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
        Locale.setDefault(Locale.ENGLISH);
        Resources res = this.getResources();
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());dialog = new PregressDialog(this);
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