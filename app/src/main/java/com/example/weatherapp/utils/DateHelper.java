package com.example.weatherapp.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelper {


    public static String getCurrentDate() {

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return currentDate;
    }

    public static Date getNextDay(List<Date> dates) {
        Calendar calendar = Calendar.getInstance();
        for (Date date : dates) {
            if (date.after(calendar.getTime())) {
                return date;
            }
        }
        return null;
    }
}
