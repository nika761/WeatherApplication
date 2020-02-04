package com.example.weatherapp.presentation.activityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;


public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ImageView icon;
    private TextView appName,copyright,author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iniUi();
        makeAnimation();
        startApplication();
    }

    private void iniUi(){
        icon = findViewById(R.id.splash_screen_image);
        appName = findViewById(R.id.weather_app_name);
        copyright = findViewById(R.id.copyright);
        author = findViewById(R.id.author);
    }

    private void makeAnimation(){
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_for_splash);
        icon.startAnimation(animation);
        appName.startAnimation(animation);
        copyright.startAnimation(animation);
        author.startAnimation(animation);
    }

    private void startApplication(){
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}

