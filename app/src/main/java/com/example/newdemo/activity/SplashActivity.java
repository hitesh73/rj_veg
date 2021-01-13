package com.example.newdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.newdemo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        },2000);
    }

    private void checkUser() {
        if (getSharedPreferences("user",MODE_PRIVATE).getString("email","").isEmpty()){
            startActivity(new Intent(SplashActivity.this,LoginScreen.class));
            finish();
        }
        else {
            startActivity(new Intent(SplashActivity.this,HomeActivity.class));
            finish();
        }
    }
}
