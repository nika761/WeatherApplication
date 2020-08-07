package com.example.weatherapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.fragments.TodayFragment;
import com.example.weatherapp.helper.ToastHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import static com.example.weatherapp.helper.SystemHelper.LOCATION;
import static com.example.weatherapp.helper.SystemHelper.checkLocationService;
import static com.example.weatherapp.helper.SystemHelper.checkNetworkConnection;
import static com.example.weatherapp.helper.SystemHelper.isLocationPermissionGranted;
import static com.example.weatherapp.helper.SystemHelper.requestLocationPermission;


public class SplashScreenActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView appName, copyright, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        iniUi();
        makeAnimation();
        checkLocationPermission();
    }

    private void iniUi() {
        icon = findViewById(R.id.splash_screen_image);
        appName = findViewById(R.id.weather_app_name);
        copyright = findViewById(R.id.copyright);
        author = findViewById(R.id.author);
    }

    private void makeAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_recycler_item);
        icon.startAnimation(animation);
        appName.startAnimation(animation);
        copyright.startAnimation(animation);
        author.startAnimation(animation);
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
            onCheckLocationService();
        } else {
            requestLocationPermission(this);
        }
    }

    public void onCheckNetworkConnection() {
        if (checkNetworkConnection(this)) {
            startApplication();
        } else {
            ToastHelper.noConnectionServiceToast(this);
        }
    }

    public void onCheckLocationService() {
        if (checkLocationService(this)) {
            onCheckNetworkConnection();
        } else {
            ToastHelper.noLocationServiceToast(this);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startApplication();
                }
                break;
        }
    }
}

