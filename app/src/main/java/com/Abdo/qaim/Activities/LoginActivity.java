package com.Abdo.qaim.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Abdo.qaim.Helper.SharedPrefs;
import com.Abdo.qaim.Interfaces.ICallback;
import com.Abdo.qaim.Models.LoginResponse.LoginResponse;
import com.Abdo.qaim.R;
import com.Abdo.qaim.Retrofit.Retrofit;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class LoginActivity extends ParentActivity implements View.OnClickListener {

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.btnForgetPassword) EditText btnForgetPassword;
    @BindView(R.id.btnSignIn) EditText btnSignIn;
    @BindView(R.id.btnCreateAccount) EditText btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set click listener
        btnForgetPassword.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnForgetPassword:
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            case R.id.btnSignIn:
                retrofit.login(etEmail.getText().toString(), etPassword.getText().toString(), this);
                break;
            case R.id.btnCreateAccount:
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, String msg) {
        super.onSuccess(response, msg);

        if (response instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) response);
        }
    }

    private void handleLoginResponse(LoginResponse response) {
        String token = response.getData().getUser().getToken();
        String accountType = response.getData().getAccountType();
        sharedPrefs.set(R.string.token, token);
        sharedPrefs.set(R.string.user_type, accountType);
        switch (accountType){
            case "user_user" :
                startActivity(new Intent(this , MainActivity.class));
                break;
            case "user_company" :
                break;
            case "company" :
//                        context.startActivity(new Intent(context , CompanyActivity.class));
                break;
            case "previewer" :
//                        context.startActivity(new Intent(context , PreviewerActivity.class));
                break;

            case "painter" :
//                        context.startActivity(new Intent(context , EmployeeActivity.class));
                break;

            case "reviewer" :
//                        context.startActivity(new Intent(context , EmployeeActivity.class));
                break;
        }
    }
}