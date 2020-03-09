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
import com.example.weatherapp.activityInterfaces.ITomorrowFragment;
import com.example.weatherapp.adapter.ForecastAdapter;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.presenters.TomorrowFragmentPresenter;

import java.util.List;

public class TomorrowFragment extends Fragment implements ITomorrowFragment {
    private TomorrowFragmentPresenter presenter;
    private RecyclerView recyclerView;
    private TextView cityName;
    private List<ListItems> listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomorrow_weather, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TomorrowFragmentPresenter(this);
        iniUI(view);
        onGetCityID();
    }

    private void iniUI(View v) {
        cityName = v.findViewById(R.id.city_name_tomorrow_fragment);
        recyclerView = v.findViewById(R.id.recycler_layout_fragment);
    }

    @Override
    public void onUpdateWeatherByID(String s, List<ListItems> listItems) {
        cityName.setText(s);
        if (s.equals("Tbilisi")) {
            recyclerView.setBackground(getResources().getDrawable(R.drawable.tbilisi_back_1));
        }
        this.listItems = listItems;
        iniRecyclerAdapter(listItems);
    }

    private void iniRecyclerAdapter(List updatedListWeather) {
        ForecastAdapter adapter = new ForecastAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setListItems(updatedListWeather);
    }

    private void onGetCityID() {
        int city = this.getArguments().getInt("id");
        presenter.fetchForecast(city);
    }
}
