package com.example.weatherapp.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefHelper {
    private static final String WEATHER_PREFERENCES = "weather_preferences";
    private static final String CITY_ID = "city_id";
    private static final String WEATHER_KEY = "city_id";

    public static void saveCityId(Context context, int cityId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CITY_ID, cityId);
        editor.apply();
    }

    public static int getCityId(Context context) {
        int cityId = 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(WEATHER_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(CITY_ID, 0) != 0) {
            cityId = sharedPreferences.getInt(CITY_ID, 0);
        }
        return cityId;
    }
}
