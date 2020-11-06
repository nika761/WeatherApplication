package com.example.weatherapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.weatherapp.R;
import com.example.weatherapp.helper.Utils;
import com.example.weatherapp.adapter.pager.DaysPagerAdapter;
import com.example.weatherapp.fragments.TodayFragment;
import com.example.weatherapp.fragments.WeeklyFragment;
import com.example.weatherapp.helper.ToastHelper;
import com.example.weatherapp.model.CurrentWeatherResponse;
import com.example.weatherapp.model.ListItems;
import com.example.weatherapp.model.WeatherMainStatebyID;
import com.example.weatherapp.presenters.MainActivityPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import static com.example.weatherapp.helper.SystemHelper.checkNetworkConnection;

public class MainActivity extends AppCompatActivity implements com.example.weatherapp.interfaces.MainActivityListener, View.OnClickListener {
    private EditText editTextForCity;
    private TextView searchButtonForCity;
    private MainActivityPresenter mainActivityPresenter;
    private FrameLayout loaderContainer;
    private LottieAnimationView loaderAnimation;
    private CurrentWeatherResponse currentWeatherResponse;
    private String city;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
        getLocation();
    }

    private void iniUI() {
        mainActivityPresenter = new MainActivityPresenter(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.c_no_connection_dialog, null);
        Button button = dialogView.findViewById(R.id.no_connection_button);
        button.setOnClickListener(this);
        builder.setView(dialogView);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dialog, null));


        editTextForCity = findViewById(R.id.text_view_city_search);
        editTextForCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        searchButtonForCity = findViewById(R.id.search_button);
        searchButtonForCity.setOnClickListener(this);

        loaderContainer = findViewById(R.id.loader_container);
        loaderAnimation = findViewById(R.id.loader);
    }

    private void getLocation() {
        FusedLocationProviderClient locationProviderClient = new FusedLocationProviderClient(this);
        Task<Location> lastLocation = locationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    mainActivityPresenter.getCurrentWeatherByCord(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                } else {
                    ToastHelper.noLocationServiceToast(this);
                }
            }
        });
    }

    private void setViewPager(CurrentWeatherResponse currentWeather, String cityName, List<ListItems> nextDayWeather, List<ListItems> weeklyWeather) {

        DaysPagerAdapter daysPagerAdapter = new DaysPagerAdapter(getSupportFragmentManager());
        daysPagerAdapter.addFragment(new TodayFragment(currentWeather, nextDayWeather), "Today");
        daysPagerAdapter.addFragment(new WeeklyFragment(weeklyWeather, cityName), "Weekly");

        loaderAnimation.setVisibility(View.GONE);
        loaderContainer.setVisibility(View.GONE);

        ViewPager dayViewPager = findViewById(R.id.days_view_pager);
        dayViewPager.setSaveFromParentEnabled(false);
        dayViewPager.setAdapter(daysPagerAdapter);

        TabLayout dayTabs = findViewById(R.id.day_tabs);
        dayTabs.setupWithViewPager(dayViewPager);

    }

    @Override
    public void onUpdateWeather(CurrentWeatherResponse currentWeatherResponse) {
        this.currentWeatherResponse = currentWeatherResponse;
    }

    @Override
    public void onUpdateWeeklyWeatherByID(WeatherMainStatebyID weatherMainStatebyID) {
        List<ListItems> nextDayWeather = Utils.getFilteredDates(weatherMainStatebyID.getList(), Utils.getNextDay(Utils.getAllDates(weatherMainStatebyID)));
        setViewPager(currentWeatherResponse, weatherMainStatebyID.getCity().getName(), nextDayWeather, weatherMainStatebyID.getList());
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        if (mainActivityPresenter != null) {
            mainActivityPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                city = editTextForCity.getText().toString();
                if (checkNetworkConnection(this)) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }
                    if (city.isEmpty()) {
                        ToastHelper.noCityEnteredToast(this);
                    } else {
                        loaderAnimation.setVisibility(View.VISIBLE);
                        loaderContainer.setVisibility(View.VISIBLE);
                        mainActivityPresenter.getCurrentWeatherByName(city);
                    }
                } else {
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                }
                editTextForCity.getText().clear();
                editTextForCity.clearFocus();
                break;

            case R.id.no_connection_button:
                dialog.cancel();

                editTextForCity.setText(city);
                searchButtonForCity.performClick();

                break;
        }
    }
}
