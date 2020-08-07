package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapter.recycler.WeeklyWeatherAdapter;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;

import java.util.List;

public class WeeklyFragment extends Fragment {
    private List<ListItems> weeklyWeather;
    private String cityName;

    public WeeklyFragment(List<ListItems> weeklyWeather, String cityName) {
        this.weeklyWeather = weeklyWeather;
        this.cityName = cityName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_weather, container, false);

        TextView cityNameTxt = view.findViewById(R.id.city_name_weekly_fragment);
        cityNameTxt.setText(cityName);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_layout_fragment);

        WeeklyWeatherAdapter adapter = new WeeklyWeatherAdapter(weeklyWeather, cityNameTxt.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
