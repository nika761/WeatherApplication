package com.example.weatherapp.activityInterfaces;

import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;

public interface ITodayFragment {
    void onUpdateWeather(WeatherMainState weather);
    void onUpdateWeatherByCord (WeatherMainStateByCord weatherMainStateByCord);
    void onFailure();
}
