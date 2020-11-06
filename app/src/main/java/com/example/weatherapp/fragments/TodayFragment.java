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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.weatherapp.R;
import com.example.weatherapp.adapter.recycler.TomorrowWeatherAdapter;
import com.example.weatherapp.helper.Utils;
import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.ListItems;

import java.util.List;
import java.util.Objects;


public class TodayFragment extends Fragment {

    private CurrentWeatherResponse currentWeatherResponse;
    private List<ListItems> nextDayWeather;

    public TodayFragment(CurrentWeatherResponse currentWeatherResponse, List<ListItems> nextDayWeather) {
        this.currentWeatherResponse = currentWeatherResponse;
        this.nextDayWeather = nextDayWeather;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);

        ImageView weatherImage = view.findViewById(R.id.weather_icon);

        TextView temperature = view.findViewById(R.id.weather_temperature);
        temperature.setText(String.format("%s C°", currentWeatherResponse.getMain().getTemp()));

        TextView cityName = view.findViewById(R.id.city_name_layout);
        cityName.setText(currentWeatherResponse.getName());

        TextView humidity = view.findViewById(R.id.humidity);
        humidity.setText(currentWeatherResponse.getMain().getHumidity() + "%");

        TextView date = view.findViewById(R.id.date);
        date.setText(Utils.getCurrentDate());

        TextView windSpeed = view.findViewById(R.id.wind);
        windSpeed.setText(String.format("%s km/h", currentWeatherResponse.getWind().getSpeed()));

        RecyclerView recyclerView = view.findViewById(R.id.recycler_tomorrow);
        TomorrowWeatherAdapter tomorrowWeatherAdapter = new TomorrowWeatherAdapter(nextDayWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tomorrowWeatherAdapter);

        TextView weatherDescription = view.findViewById(R.id.weather_description);
        weatherDescription.setText(currentWeatherResponse.getWeather().get(0).getDescription());

        String weatherIconID = currentWeatherResponse.getWeather().get(0).getIcon();
        Glide.with(date.getContext())
                .load("http://openweathermap.org/img/wn/" + weatherIconID + "@2x.png")
                .apply(new RequestOptions().centerCrop()).into(weatherImage);

        return view;
    }
}
