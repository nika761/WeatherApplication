package com.example.weatherapp.presenters;

import com.example.weatherapp.activityInterfaces.IWeeklyWeatherView;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeeklyWeatherActivityPresenter {
    private IWeeklyWeatherView weeklyWeatherView;

    public WeeklyWeatherActivityPresenter (IWeeklyWeatherView weeklyWeatherView){
        this.weeklyWeatherView = weeklyWeatherView;
    }


    public void fetchWeeklyForecast (int city){
        ApiService service = RetrofitManager.getApiservice();
        service.fetchforecast(city, "metric", "0559b29e30ef329bb28d598ec6bab17d").enqueue(new Callback<WeatherMainStatebyID>() {
            @Override
            public void onResponse(Call<WeatherMainStatebyID> call, Response<WeatherMainStatebyID> response) {
                if (response.isSuccessful()){
                    weeklyWeatherView.onUpdateWeatherByID(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherMainStatebyID> call, Throwable t) {

            }
        });
    }
}
