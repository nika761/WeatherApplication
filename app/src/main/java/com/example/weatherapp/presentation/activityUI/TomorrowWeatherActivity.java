package com.example.weatherapp.presentation.activityUI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activityInterfaces.ITomorrowWeatherView;
import com.example.weatherapp.adapter.ForecastAdapter;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.presenters.TomorrowWeatherActivityPresenter;

import java.util.List;

public class TomorrowWeatherActivity extends AppCompatActivity implements ITomorrowWeatherView {

    private TomorrowWeatherActivityPresenter tomorrowWeatherActivityPresenter;
    private RecyclerView recyclerView;
    private TextView cityName;
    private List <ListItems> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomorrow_weather);
        tomorrowWeatherActivityPresenter = new TomorrowWeatherActivityPresenter(this);
        iniUI();
        onGetCityID();
    }

    public void iniUI(){
        cityName = findViewById(R.id.city_name_tomorrow);
    }

    public void iniRecyclerAdapter (List updatedListWeather){
        ForecastAdapter adapter = new ForecastAdapter(this);
        recyclerView = findViewById(R.id.recycler_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setListItems(updatedListWeather);
    }

    public void onGetCityID (){
        int city = getIntent().getIntExtra("cityID",0);
        tomorrowWeatherActivityPresenter.fetchForecast(city);
    }


    @Override
    public void onUpdateWeatherByID(String s, List<ListItems> listItems) {
        cityName.setText(s);
        this.listItems = listItems;
        iniRecyclerAdapter(listItems);
    }
}
