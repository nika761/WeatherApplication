package com.example.weatherapp.network;

import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.WeatherMainStatebyID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("data/2.5/weather?")
    Call<CurrentWeatherResponse> fetchCurrentWeatherByCityName(@Query("q") String city, @Query("units") String unit, @Query("appid") String appid);

    @GET("data/2.5/forecast?")
    Call<WeatherMainStatebyID> fetchDailyWeatherByCityId(@Query("id") int city, @Query("units") String unit, @Query("appid") String appid);

    @GET("data/2.5/weather?")
    Call<CurrentWeatherResponse> fetchCurrentWeatherByCord(@Query("lat") String lat, @Query("lon") String lon, @Query("units") String unit, @Query("appid") String appid);

}
