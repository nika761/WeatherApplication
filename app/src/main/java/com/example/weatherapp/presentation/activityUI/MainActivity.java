package com.example.weatherapp.presentation.activityUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.activityInterfaces.ITodayFragmentToMain;
import com.example.weatherapp.fragments.TodayFragment;
import com.example.weatherapp.fragments.TomorrowFragment;
import com.example.weatherapp.fragments.WeeklyFragment;
import com.example.weatherapp.utils.ToastHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import static com.example.weatherapp.utils.ConnectionHelper.checkNetworkConnection;
import static com.example.weatherapp.utils.LocationHelper.checkLocationService;


public class MainActivity extends AppCompatActivity implements ITodayFragmentToMain {
    private FusedLocationProviderClient locationProviderClient;
    private EditText editTextForCity;
    private TextView searchButtonForCity, today, tomorrow, weekly;
    private static final int LOCATION_REQUEST_CODE = 777;
    private int cityID;
    private String lat, lon;
    private SwipeRefreshLayout refreshLayout;
    private TomorrowFragment tomorrowFragment;
    private TodayFragment todayFragment;
    private WeeklyFragment weeklyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
        addClickListeners();
        checkLocationPermission();
        focusHelper();
    }

    private void iniUI() {
        editTextForCity = findViewById(R.id.text_view_city_search);
        tomorrow = findViewById(R.id.tomorrow);
        today = findViewById(R.id.today);
        weekly = findViewById(R.id.weekly);
        searchButtonForCity = findViewById(R.id.search_button);
        refreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    private void setAnimations() {
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.animation_buttons);
        today.startAnimation(myAnim);
        tomorrow.startAnimation(myAnim);
        weekly.startAnimation(myAnim);
    }

    private void addClickListeners() {

        today.setOnClickListener(this::dayClicked);
        tomorrow.setOnClickListener(this::dayClicked);
        weekly.setOnClickListener(this::dayClicked);

        searchButtonForCity.setOnClickListener(v -> {
            final String city = editTextForCity.getText().toString();
            if (city.isEmpty()) {
                ToastHelper.noCityEnteredToast(this);
            } else if (!checkNetworkConnection(this)) {
                ToastHelper.noConnectionServiceToast(this);
            } else {
                //startProgressDialog();
                //presenter.fetchForecast(city);
                todayFragment = new TodayFragment(this);
                loadFragment(todayFragment, 0, null, null, city);
                today.setBackground(getResources().getDrawable(R.drawable.background_for_today));
                tomorrow.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                weekly.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
            }
            editTextForCity.getText().clear();
            editTextForCity.clearFocus();
        });
        refreshLayout.setOnRefreshListener(this::onCheckLocationService);

    }

    private void dayClicked(View v) {
        switch (v.getId()) {
            case R.id.today:
                today.setBackground(getResources().getDrawable(R.drawable.background_for_today));
                tomorrow.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                weekly.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                todayFragment = new TodayFragment(this);
                if (checkNetworkConnection(this)) {
                    loadFragment(todayFragment, 0, lat, lon, null);
                } else {
                    ToastHelper.noConnectionServiceToast(this);
                }
                break;

            case R.id.tomorrow:
                tomorrow.setBackground(getResources().getDrawable(R.drawable.background_for_today));
                today.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                weekly.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                if (checkNetworkConnection(this)) {
                    tomorrowFragment = new TomorrowFragment();
                    loadFragment(tomorrowFragment, cityID, null, null, null);
                } else {
                    ToastHelper.noConnectionServiceToast(this);
                }
                break;

            case R.id.weekly:
                weekly.setBackground(getResources().getDrawable(R.drawable.background_for_today));
                tomorrow.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                today.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                if (checkNetworkConnection(this)) {
                    weeklyFragment = new WeeklyFragment();
                    loadFragment(weeklyFragment, cityID, null, null, null);
                } else {
                    ToastHelper.noConnectionServiceToast(this);
                }
                break;
        }
    }

    private void loadFragment(Fragment fragment, int cityID, String lat, String lon, String cityName) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", cityID);
        bundle.putString("lat", lat);
        bundle.putString("lon", lon);
        bundle.putString("name", cityName);

        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public void focusHelper() {
        editTextForCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
    }

    public void checkLocationPermission() {
        if (isLocationPermissionGranted()) {
            onCheckLocationService();
        } else {
            requestLocationPermission();
        }
    }

    public void onCheckNetworkConnection() {
        if (checkNetworkConnection(this)) {
            getLocation();
        } else {
            refreshLayout.setRefreshing(false);
            ToastHelper.noConnectionServiceToast(this);
        }
    }

    public void onCheckLocationService() {
        if (checkLocationService(this)) {
            onCheckNetworkConnection();
        } else {
            refreshLayout.setRefreshing(false);
            ToastHelper.noLocationServiceToast(this);
        }
    }

    public void getLocation() {
        locationProviderClient = new FusedLocationProviderClient(this);
        @SuppressLint("MissingPermission") Task<Location> lastLocation = locationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null) {
                    double laliTude = location.getLatitude();
                    double longiTude = location.getLongitude();
                    lat = String.valueOf(laliTude);
                    lon = String.valueOf(longiTude);
                    //presenter.fetchForecastByCord(lat, lon);
                    todayFragment = new TodayFragment(this);
                    loadFragment(todayFragment, 0, lat, lon, null);
                    today.setBackground(getResources().getDrawable(R.drawable.background_for_today));
                    tomorrow.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                    weekly.setBackground(getResources().getDrawable(R.drawable.background_for_weeks));
                    setAnimations();
                    refreshLayout.setRefreshing(false);
                    //startProgressDialog();
                } else {
                    refreshLayout.setRefreshing(false);
                    ToastHelper.noLocationServiceToast(this);
                }
            }
        });
    }

    private boolean isLocationPermissionGranted() {
        boolean permissionGranted = false;
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        }
        return permissionGranted;
        //return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                break;
        }
    }

    @Override
    public void getCityId(int idOfCity) {
        cityID = idOfCity;
    }

}
