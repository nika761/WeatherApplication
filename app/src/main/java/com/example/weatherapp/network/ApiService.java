package com.example.weatherapp.network;

import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;
import com.example.weatherapp.model.WeatherMainStatebyID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("data/2.5/weather?")
    Call<WeatherMainState> fetchpost(@Query("q") String city, @Query("units") String unit, @Query("appid") String appid);

    @GET("data/2.5/forecast?")
    Call<WeatherMainStatebyID> fetchforecast(@Query("id") int city, @Query("units") String unit, @Query("appid") String appid);

    @GET("data/2.5/weather?")
    Call<WeatherMainStateByCord> fetchForecastByCord (@Query("lat") String lat, @Query("lon") String lon, @Query("units") String unit, @Query("appid") String appid);

}
