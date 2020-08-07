package com.example.weatherapp.interfaces;

import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.WeatherMainStatebyID;

import java.util.List;

public interface IMainActivity {


    void onUpdateWeather(CurrentWeatherResponse currentWeatherResponse);

//    void onUpdateWeatherByCityName(CurrentWeatherResponse currentWeatherResponse);

    void onUpdateWeeklyWeatherByID(WeatherMainStatebyID weatherMainStatebyID);

    void onFailure(String message);

}
