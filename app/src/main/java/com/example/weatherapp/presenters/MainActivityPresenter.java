package com.example.weatherapp.presenters;

import com.example.weatherapp.interfaces.MainActivityListener;
import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.network.ApiEndPoint.APPID_CITY_NAME;
import static com.example.weatherapp.network.ApiEndPoint.APPID_CORD;
import static com.example.weatherapp.network.ApiEndPoint.UNIT_METRIC;

public class MainActivityPresenter {
    private MainActivityListener mainActivityListener;
    private ApiService apiService;

    public MainActivityPresenter(MainActivityListener mainActivityListener) {
        this.mainActivityListener = mainActivityListener;
        this.apiService = RetrofitManager.getApiService();
    }

    public void getCurrentWeatherByName(String city) {
        apiService.fetchCurrentWeatherByCityName(city, UNIT_METRIC, APPID_CITY_NAME).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherResponse> call, @NotNull Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mainActivityListener.onUpdateWeather(response.body());
                    getWeeklyWeather(response.body().getId());
                } else {
                    mainActivityListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherResponse> call, @NotNull Throwable t) {
                mainActivityListener.onFailure(t.getMessage());
            }
        });
    }

    public void getCurrentWeatherByCord(String lat, String lon) {
        apiService.fetchCurrentWeatherByCord(lat, lon, UNIT_METRIC, APPID_CORD).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeatherResponse> call, @NotNull Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getWeeklyWeather(response.body().getId());
                    mainActivityListener.onUpdateWeather(response.body());
                } else {
                    mainActivityListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeatherResponse> call, @NotNull Throwable t) {
                mainActivityListener.onFailure(t.getMessage());
            }
        });
    }

    private void getWeeklyWeather(int city) {
        apiService.fetchDailyWeatherByCityId(city, UNIT_METRIC, APPID_CORD).enqueue(new Callback<WeatherMainStatebyID>() {
            @Override
            public void onResponse(@NotNull Call<WeatherMainStatebyID> call, @NotNull Response<WeatherMainStatebyID> response) {
                if (response.isSuccessful()) {
                    mainActivityListener.onUpdateWeeklyWeatherByID(response.body());
                } else {
                    mainActivityListener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherMainStatebyID> call, @NotNull Throwable t) {
                mainActivityListener.onFailure(t.getMessage());
            }
        });
    }


}
