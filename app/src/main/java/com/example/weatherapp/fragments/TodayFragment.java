package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.weatherapp.R;
import com.example.weatherapp.helper.Utils;
import com.example.weatherapp.model.CurrentWeatherResponse;

import java.util.Objects;


public class TodayFragment extends Fragment {

    private ImageView weatherImage;
    private TextView date;
    private CurrentWeatherResponse currentWeatherResponse;

    public TodayFragment(CurrentWeatherResponse currentWeatherResponse) {
        this.currentWeatherResponse = currentWeatherResponse;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);

        weatherImage = view.findViewById(R.id.weather_icon);

        TextView temperature = view.findViewById(R.id.weather_temperature);
        temperature.setText(currentWeatherResponse.getMain().getTemp() + " C\u00B0");

        TextView cityName = view.findViewById(R.id.city_name_layout);
        cityName.setText(currentWeatherResponse.getName());

        TextView humidity = view.findViewById(R.id.humidity);
        humidity.setText(currentWeatherResponse.getMain().getHumidity() + "%");

        date = view.findViewById(R.id.date);
        date.setText(Utils.getCurrentDate());

        TextView windSpeed = view.findViewById(R.id.wind);
        windSpeed.setText(currentWeatherResponse.getWind().getSpeed() + " km/h");

//        LottieAnimationView windAnimation = view.findViewById(R.id.wind_animation);
//        windAnimation.setVisibility(View.VISIBLE);

        TextView precipitation = view.findViewById(R.id.precipitation);
        precipitation.setText(currentWeatherResponse.getMain().getPressure() + "");

        TextView weatherDescription = view.findViewById(R.id.weather_description);
        weatherDescription.setText(currentWeatherResponse.getWeather().get(0).getDescription());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String weatherIconID = currentWeatherResponse.getWeather().get(0).getIcon();
        Glide.with(date.getContext())
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .apply(new RequestOptions().centerCrop()).into(weatherImage);
    }
}
