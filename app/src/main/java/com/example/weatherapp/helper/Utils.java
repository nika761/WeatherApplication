package com.example.weatherapp.helper;

import android.annotation.SuppressLint;

import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

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

    public static List<Date> getAllDates(WeatherMainStatebyID weatherMainStatebyID) {
        List<Date> dateList = new ArrayList<>();

        for (int i = 0; i < weatherMainStatebyID.getList().size(); i++) {
            String stringDate = weatherMainStatebyID.getList().get(i).getDt_txt();

            SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = formatFrom.parse(stringDate.split(" ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateList.add(date);
        }
        return dateList;
    }

    public static List<ListItems> getFilteredDates(List<ListItems> dates, Date nextDay) {

        List<ListItems> filteredDates = new ArrayList<>();

        for (ListItems currentItem : dates) {
            String stringDate = currentItem.getDt_txt();

            SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = formatFrom.parse(stringDate.split(" ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.equals(nextDay)) {
                filteredDates.add(currentItem);
            }
        }
        return filteredDates;
    }
}
