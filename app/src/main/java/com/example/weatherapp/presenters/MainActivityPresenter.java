package com.example.weatherapp.presenters;

import android.annotation.SuppressLint;

import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;
import com.example.weatherapp.activityInterfaces.IMainActivityView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private IMainActivityView mainActivityView;

    public MainActivityPresenter(IMainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    public void fetchForecast (String city){
        ApiService service = RetrofitManager.getApiservice();
        service.fetchpost(city,"metric","bb3794aaf3b2d98767a4cad30ed32d9f").enqueue(new Callback<WeatherMainState>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<WeatherMainState> call, @NotNull Response<WeatherMainState> response) {
                mainActivityView.endProgressDialog();
                if (response.code() == 404){
                    mainActivityView.onFailure();
                }
                 if (Objects.requireNonNull(response).isSuccessful()){
                         WeatherMainState weather = response.body();
                         mainActivityView.onUpdateWeather(weather);
                     }
                }

            @Override
            public void onFailure(Call<WeatherMainState> call, Throwable t) {

            }
        });
    }
    public void fetchForecastByCord (String lat, String lon) {
        ApiService serviceCord = RetrofitManager.getApiservice();
        serviceCord.fetchForecastByCord(lat,lon,"metric","0559b29e30ef329bb28d598ec6bab17d").enqueue(new Callback<WeatherMainStateByCord>() {
            @Override
            public void onResponse(@NotNull Call<WeatherMainStateByCord> call, @NotNull Response<WeatherMainStateByCord> response) {
                mainActivityView.endProgressDialog();
                if (response.isSuccessful()){
                    WeatherMainStateByCord weatherMainStateByCord = response.body();
                    mainActivityView.onUpdateWeatherByCord(weatherMainStateByCord);
                }
            }

            @Override
            public void onFailure(Call<WeatherMainStateByCord> call, Throwable t) {

            }
        });
    }
}
