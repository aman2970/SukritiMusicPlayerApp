package com.example.sukriti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
            boolean isOnboard = sharedPreferences.getBoolean("isOnboard", false);

            if (isOnboard) {
                Intent intent = new Intent(this, MusicListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, IntroScreens.class);
                startActivity(intent);
                finish();
            }

        }, 1000);
    }
}