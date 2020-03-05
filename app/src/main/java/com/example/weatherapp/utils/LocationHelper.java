package com.example.weatherapp.utils;

import android.content.Context;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

public class LocationHelper {

    public static boolean checkLocationService(Context context) {
        boolean gps_enabled = false;

        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        //boolean network_enabled = false;
        try {
            assert locationManager != null;
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            gps_enabled = true;

        } catch (Exception ex) {
        }

//        try {
//            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            network_enabled = true;
//        } catch (Exception ex) {}

        return gps_enabled;
    }
}
