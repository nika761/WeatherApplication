package com.example.weatherapp.presentation.activityUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activityInterfaces.IWeeklyWeatherView;
import com.example.weatherapp.adapter.WeeklyForecastAdapter;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.presenters.WeeklyWeatherActivityPresenter;

import java.util.List;

public class WeeklyWeatherActivity extends AppCompatActivity implements IWeeklyWeatherView {

    private WeeklyWeatherActivityPresenter weeklyWeatherActivityPresenter;
    private RecyclerView recyclerView;
    private TextView cityName;
    private List<ListItems> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_weather);
        weeklyWeatherActivityPresenter = new WeeklyWeatherActivityPresenter(this::onUpdateWeatherByID);
        onGetCityID();
        iniUI();
    }

    public void iniUI(){
        cityName = findViewById(R.id.city_name_weekly);
    }

    public void iniRecyclerAdapter (List listItems){
        WeeklyForecastAdapter adapter = new WeeklyForecastAdapter(this);
        recyclerView = findViewById(R.id.recycler_layout_weekly);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setListItems(listItems);
    }

    @Override
    public void onUpdateWeatherByID(WeatherMainStatebyID weatherMainStatebyID) {
        cityName.setText(weatherMainStatebyID.getCity().getName());
        listItems = weatherMainStatebyID.getList();
        iniRecyclerAdapter(listItems);
    }

    public void onGetCityID (){
        int city = getIntent().getIntExtra("cityID",0);
        weeklyWeatherActivityPresenter.fetchWeeklyForecast(city);
    }
}
