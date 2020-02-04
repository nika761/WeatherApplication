package com.example.weatherapp.utils;

import es.dmoral.toasty.Toasty;

public class ToastHelper {
    public static void toastCustomization(){
        Toasty.Config.getInstance().tintIcon(true).allowQueue(true).apply();
    }
}
