package com.example.weatherapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.helper.ToastHelper;

import static com.example.weatherapp.helper.SystemHelper.LOCATION;
import static com.example.weatherapp.helper.SystemHelper.checkLocationService;
import static com.example.weatherapp.helper.SystemHelper.checkNetworkConnection;
import static com.example.weatherapp.helper.SystemHelper.isLocationPermissionGranted;
import static com.example.weatherapp.helper.SystemHelper.requestLocationPermission;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initUI();
        checkLocationPermission();
    }

    private void initUI() {
        ImageView icon = findViewById(R.id.splash_screen_image);
        TextView appName = findViewById(R.id.weather_app_name);
        TextView copyright = findViewById(R.id.copyright);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_recycler_item);
        icon.startAnimation(animation);
        appName.startAnimation(animation);
        copyright.startAnimation(animation);
    }


    private void startApplication() {
        int SPLASH_DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }


    public void checkLocationPermission() {
        if (isLocationPermissionGranted(this)) {
            if (checkLocationService(this)) {
                if (checkNetworkConnection(this))
                    startApplication();
                else
                    ToastHelper.noConnectionServiceToast(this);

            } else {
                ToastHelper.noLocationServiceToast(this);
            }
        } else {
            requestLocationPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkLocationService(this) && checkNetworkConnection(this))
                        startApplication();
                    else
                        ToastHelper.noConnectionServiceToast(this);
                }
                break;
        }
    }
}

