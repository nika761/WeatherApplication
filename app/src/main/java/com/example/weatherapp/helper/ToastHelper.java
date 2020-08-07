package com.example.weatherapp.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

//    public static void toastCustomization(){
//        Toasty.Config.getInstance().tintIcon(true).allowQueue(true).apply();
//    }

    public static void noConnectionServiceToast(Context context) {
        Toast.makeText(context ," Network connection not available ", Toast.LENGTH_LONG).show();
    }

    public static void noCityEnteredToast(Context context){
        Toast.makeText(context, " Name of City not entered ", Toast.LENGTH_LONG).show();
    }

    public static void noLocationServiceToast(Context context){
        Toast.makeText(context, " Location services turned off ", Toast.LENGTH_LONG).show();
    }

    public static void invalidCityEnteredToast(Context context){
        Toast.makeText(context, "Invalid city name", Toast.LENGTH_SHORT).show();
    }
}
