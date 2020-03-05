package com.example.weatherapp.presenters;

import com.example.weatherapp.utils.DateHelper;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.network.ApiService;
import com.example.weatherapp.network.RetrofitManager;
import com.example.weatherapp.activityInterfaces.ITomorrowFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TomorrowFragmentPresenter {
    private ITomorrowFragment tomorrowWeatherView;

    public TomorrowFragmentPresenter(ITomorrowFragment tomorrowWeatherView) {
        this.tomorrowWeatherView = tomorrowWeatherView;
    }

    public void fetchForecast(int city) {
        ApiService service = RetrofitManager.getApiservice();
        service.fetchforecast(city, "metric", "0559b29e30ef329bb28d598ec6bab17d").enqueue(new Callback<WeatherMainStatebyID>() {
            @Override
            public void onResponse(Call<WeatherMainStatebyID> call, Response<WeatherMainStatebyID> response) {
                if (response.isSuccessful()) {
                    tomorrowWeatherView.onUpdateWeatherByID(response.body().getCity().getName(),getFilteredDates(response.body().getList(),DateHelper.getNextDay(getAllDates(response.body()))));
                }
            }

            @Override
            public void onFailure(Call<WeatherMainStatebyID> call, Throwable t) {

            }
        });
    }

    private List<Date> getAllDates(WeatherMainStatebyID weatherMainStatebyID) {
        List<Date> dateList = new ArrayList<>();

        for (int i = 0; i < weatherMainStatebyID.getList().size(); i++) {
            String stringDate = weatherMainStatebyID.getList().get(i).getDt_txt();

            SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = formatFrom.parse(stringDate.split(" ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateList.add(date);
        }
        return dateList;
    }

    private List<ListItems> getFilteredDates(List<ListItems> dates , Date nextDay){

        List<ListItems> filteredDates = new ArrayList<>();

        for (ListItems currentItem : dates){
            String stringDate = currentItem.getDt_txt();

            SimpleDateFormat formatFrom = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = formatFrom.parse(stringDate.split(" ")[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.equals(nextDay)){
                filteredDates.add(currentItem);
            }
        }
        return filteredDates;
    }

}
