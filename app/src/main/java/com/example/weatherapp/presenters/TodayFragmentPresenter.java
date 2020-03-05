package com.example.weatherapp.presenters;

import android.annotation.SuppressLint;

import com.example.weatherapp.activityInterfaces.ITodayFragment;
import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayFragmentPresenter {
    private ITodayFragment iTodayFragment;

    public TodayFragmentPresenter(ITodayFragment iTodayFragment) {
        this.iTodayFragment = iTodayFragment;
    }

    public void fetchForecast(String city) {
        ApiService service = RetrofitManager.getApiservice();
        service.fetchpost(city, "metric", "bb3794aaf3b2d98767a4cad30ed32d9f").enqueue(new Callback<WeatherMainState>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<WeatherMainState> call, @NotNull Response<WeatherMainState> response) {
                if (response.code() == 404) {
                    iTodayFragment.onFailure();
                } else if (Objects.requireNonNull(response).isSuccessful()) {
                    WeatherMainState weather = response.body();
                    iTodayFragment.onUpdateWeather(weather);
                }
            }

            @Override
            public void onFailure(Call<WeatherMainState> call, Throwable t) {
                iTodayFragment.onFailure();
            }
        });
    }

    public void fetchForecastByCord(String lat, String lon) {
        ApiService serviceCord = RetrofitManager.getApiservice();
        serviceCord.fetchForecastByCord(lat, lon, "metric", "0559b29e30ef329bb28d598ec6bab17d").enqueue(new Callback<WeatherMainStateByCord>() {
            @Override
            public void onResponse(@NotNull Call<WeatherMainStateByCord> call, @NotNull Response<WeatherMainStateByCord> response) {
                //iTodayFragment.endProgressDialogAndRefreshing();
                if (response.isSuccessful()) {
                    WeatherMainStateByCord weatherMainStateByCord = response.body();
                    iTodayFragment.onUpdateWeatherByCord(weatherMainStateByCord);
                }
            }

            @Override
            public void onFailure(Call<WeatherMainStateByCord> call, Throwable t) {
            }
        });
    }

}
