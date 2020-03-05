package com.example.weatherapp.activityInterfaces;

import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;

import java.util.Date;
import java.util.List;

public interface ITomorrowFragment {
    void onUpdateWeatherByID (String cityName , List<ListItems> listItems);
}
