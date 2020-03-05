package com.example.weatherapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.weatherapp.R;
import com.example.weatherapp.activityInterfaces.ITodayFragmentToMain;
import com.example.weatherapp.activityInterfaces.ITodayFragment;
import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;
import com.example.weatherapp.presenters.TodayFragmentPresenter;
import com.example.weatherapp.utils.DateHelper;
import com.example.weatherapp.utils.ToastHelper;

import java.util.Objects;

public class TodayFragment extends Fragment implements ITodayFragment {

    private ImageView weatherImage;
    private TextView temperature, cityName, humidity, date, windSpeed, precipitation, weatherDescription;
    private TodayFragmentPresenter presenter;
    private ITodayFragmentToMain iTodayFragmentToMain;
    private int cityID;

    public TodayFragment(ITodayFragmentToMain iTodayFragmentToMain) {
        this.iTodayFragmentToMain = iTodayFragmentToMain;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TodayFragmentPresenter(this);
        iniUI(view);
        checkArguments();
    }

    private void iniUI(View v) {
        weatherImage = v.findViewById(R.id.weather_icon);
        temperature = v.findViewById(R.id.weather_temperature);
        cityName = v.findViewById(R.id.city_name_layout);
        humidity = v.findViewById(R.id.humidity);
        date = v.findViewById(R.id.date);
        windSpeed = v.findViewById(R.id.wind);
        precipitation = v.findViewById(R.id.precipitation);
        weatherDescription = v.findViewById(R.id.weather_description);
        date.setText(DateHelper.getCurrentDate());
    }

    private void checkArguments() {
        String lat = this.getArguments().getString("lat");
        String lon = this.getArguments().getString("lon");
        String cityName = this.getArguments().getString("name");

        if (cityName != null) {
            presenter.fetchForecast(cityName);
        } else {
            presenter.fetchForecastByCord(lat, lon);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateWeather(WeatherMainState weather) {
        cityName.setText(weather.getName());
        temperature.setText(weather.getMain().getTemp() + " C\u00B0");
        temperature.setTextColor(getResources().getColor(R.color.white));
        windSpeed.setText(weather.getWind().getSpeed() + " km/h");
        precipitation.setText(weather.getMain().getPressure() + "");
        weatherDescription.setText(weather.getWeather().get(0).getDescription());
        weatherDescription.setTextColor(getResources().getColor(R.color.white));
        humidity.setText(weather.getMain().getHumidity() + "%");
        cityID = weather.getId();
        iTodayFragmentToMain.getCityId(cityID);
        String weatherIconID = weather.getWeather().get(0).getIcon();

        Glide.with(Objects.requireNonNull(getActivity()))
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .apply(new RequestOptions().centerCrop()).into(weatherImage);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onUpdateWeatherByCord(WeatherMainStateByCord weatherMainStateByCord) {
        cityName.setText(weatherMainStateByCord.getName());
        temperature.setText(weatherMainStateByCord.getMain().getTemp() + " C\u00B0");
        temperature.setTextColor(getResources().getColor(R.color.white));
        precipitation.setText(weatherMainStateByCord.getMain().getPressure() + "");
        humidity.setText(weatherMainStateByCord.getMain().getHumidity() + "%");
        windSpeed.setText(weatherMainStateByCord.getWind().getSpeed() + " km/h");
        weatherDescription.setText(weatherMainStateByCord.getWeather().get(0).getDescription());
        weatherDescription.setTextColor(getResources().getColor(R.color.white));
        cityID = weatherMainStateByCord.getId();
        iTodayFragmentToMain.getCityId(cityID);
        String weatherIconIdByCord = weatherMainStateByCord.getWeather().get(0).getIcon();

        Glide.with(Objects.requireNonNull(getActivity()))
                .load("http://openweathermap.org/img/wn/" + weatherIconIdByCord + "@2x.png")
                .apply(new RequestOptions().centerCrop()).into(weatherImage);
    }

    @Override
    public void onFailure() {
        ToastHelper.invalidCityEnteredToast(getContext());
    }

}
