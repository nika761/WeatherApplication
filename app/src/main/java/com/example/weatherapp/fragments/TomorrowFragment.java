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
import com.example.weatherapp.adapter.recycler.TomorrowWeatherAdapter;
import com.example.weatherapp.model.ListItems;

import java.util.List;

public class TomorrowFragment extends Fragment {
    private List<ListItems> nextDayWeather;
    private String cityName;

    public TomorrowFragment(List<ListItems> nextDayWeather, String cityName) {
        this.nextDayWeather = nextDayWeather;
        this.cityName = cityName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomorrow_weather, container, false);

        TextView cityNameTxt = view.findViewById(R.id.city_name_tomorrow_fragment);
        cityNameTxt.setText(cityName);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_layout_fragment);
        if (cityName.equals("Tbilisi")) {
            recyclerView.setBackground(getResources().getDrawable(R.drawable.tbilisi_back_1));
        }

        TomorrowWeatherAdapter tomorrowWeatherAdapter = new TomorrowWeatherAdapter(nextDayWeather);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tomorrowWeatherAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
