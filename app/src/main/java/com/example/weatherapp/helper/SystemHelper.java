package com.example.weatherapp.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.app.ActivityCompat;

import java.util.Objects;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.LOCATION_SERVICE;

public class SystemHelper {
    public static final int LOCATION = 111;


    public static boolean checkNetworkConnection(Context context) {
        boolean wifiConnected = false;
        boolean mobileDataConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileDataConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return wifiConnected || mobileDataConnected;
    }

    public static boolean checkLocationService(Context context) {
        boolean gps_enabled = false;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        try {
            assert locationManager != null;
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            gps_enabled = true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        try {
//            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            network_enabled = true;
//        } catch (Exception ex) {}
        return gps_enabled;
    }

    public static boolean isLocationPermissionGranted(Activity activity) {

        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    public static void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION);
    }
}
