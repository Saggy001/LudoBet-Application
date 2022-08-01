package com.Saggy.ludobet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalshscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intologin = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intologin);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
