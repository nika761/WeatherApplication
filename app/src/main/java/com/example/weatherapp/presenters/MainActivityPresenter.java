package com.example.weatherapp.presenters;

import com.example.weatherapp.helper.Utils;
import com.example.weatherapp.interfaces.IMainActivity;
import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.network.ApiEndPoint.APPID_CITY_NAME;
import static com.example.weatherapp.network.ApiEndPoint.APPID_CORD;
import static com.example.weatherapp.network.ApiEndPoint.UNIT_METRIC;

public class MainActivityPresenter {
    private IMainActivity iMainActivity;
    private ApiService apiService;

    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getCurrentWeatherByName(String city) {
        apiService.fetchCurrentWeatherByCityName(city, UNIT_METRIC, APPID_CITY_NAME).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherResponse> call, @NotNull Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    iMainActivity.onUpdateWeatherByCityName(response.body());
                    iMainActivity.onUpdateWeather(response.body());
                    getWeeklyWeather(response.body().getId());
                } else {
                    iMainActivity.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherResponse> call, @NotNull Throwable t) {
                iMainActivity.onFailure(t.getMessage());
            }
        });
    }

    public void getCurrentWeatherByCord(String lat, String lon) {
        apiService.fetchCurrentWeatherByCord(lat, lon, UNIT_METRIC, APPID_CORD).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherResponse> call, @NotNull Response<CurrentWeatherResponse> response) {
                //iTodayFragment.endProgressDialogAndRefreshing();
                if (response.isSuccessful() && response.body() != null) {
                    getWeeklyWeather(response.body().getId());
                    iMainActivity.onUpdateWeather(response.body());
                } else {
                    iMainActivity.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherResponse> call, @NotNull Throwable t) {
                iMainActivity.onFailure(t.getMessage());
            }
        });
    }

    public void getWeeklyWeather(int city) {
        apiService.fetchDailyWeatherByCityId(city, UNIT_METRIC, APPID_CORD).enqueue(new Callback<WeatherMainStatebyID>() {
            @Override
            public void onResponse(@NotNull Call<WeatherMainStatebyID> call, @NotNull Response<WeatherMainStatebyID> response) {
                if (response.isSuccessful()) {
                    iMainActivity.onUpdateWeeklyWeatherByID(response.body());
                } else {
                    iMainActivity.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherMainStatebyID> call, @NotNull Throwable t) {
                iMainActivity.onFailure(t.getMessage());
            }
        });
    }


}
