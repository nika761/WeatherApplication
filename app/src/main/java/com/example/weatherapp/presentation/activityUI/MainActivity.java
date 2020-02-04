package com.example.weatherapp.presentation.activityUI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.DateHelper;
import com.example.weatherapp.utils.ToastHelper;
import com.example.weatherapp.activityInterfaces.IMainActivityView;
import com.example.weatherapp.model.WeatherMainState;
import com.example.weatherapp.model.WeatherMainStateByCord;
import com.example.weatherapp.presenters.MainActivityPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements IMainActivityView {
    private MainActivityPresenter presenter;
    private FusedLocationProviderClient locationProviderClient;
    private ProgressDialog progressDialog;
    private EditText editTextForCity;
    private TextView searchButtonForCity,temperature,cityName,humidity,windSpeed,weatherDescription,date,precipitation,tomorrow,weekly;
    private ImageView weatherIcon;
    private int cityID;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
        addListeners();
        presenter = new MainActivityPresenter(this);
        onGetLocation();
        focusHelper();
    }


    private void iniUI(){
        editTextForCity = findViewById(R.id.text_view_city_search);
        temperature = findViewById(R.id.weather_temperature);
        cityName = findViewById(R.id.city_name_layout);
        humidity = findViewById(R.id.humidity);
        date = findViewById(R.id.date);
        windSpeed = findViewById(R.id.wind);
        precipitation = findViewById(R.id.precipitation);
        weatherIcon = findViewById(R.id.weather_icon);
        weatherDescription = findViewById(R.id.weather_description);
        tomorrow = findViewById(R.id.tomorrow);
        weekly = findViewById(R.id.weekly);
        searchButtonForCity = findViewById(R.id.search_button);
        date.setText(DateHelper.getCurrentDate());
    }

    private void addListeners(){
        tomorrow.setOnClickListener(v ->{
            startTomorrowWeatherActivity();
        });

        weekly.setOnClickListener(v ->{
            startWeeklyWeatherActivity();
        });

        searchButtonForCity.setOnClickListener(v -> {
            startProgressDialog();
            final String city = editTextForCity.getText().toString();
            presenter.fetchForecast(city);
            editTextForCity.getText().clear();
            editTextForCity.clearFocus();
        });
    }

    public void focusHelper(){
        editTextForCity.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert inputMethodManager != null;
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }

    @Override
    public void onUpdateWeather(WeatherMainState weather) {

        cityName.setText(weather.getName());
        temperature.setText(weather.getMain().getTemp()+" C\u00B0");
        temperature.setTextColor(getResources().getColor(R.color.white));
        windSpeed.setText(weather.getWind().getSpeed()+" km/h");
        precipitation.setText(weather.getMain().getPressure()+"");
        weatherDescription.setText(weather.getWeather().get(0).getDescription());
        weatherDescription.setTextColor(getResources().getColor(R.color.white));
        humidity.setText(weather.getMain().getHumidity()+"%");
        cityID = weather.getId();
        String weatherIconID = weather.getWeather().get(0).getIcon();

        Glide.with(MainActivity.this)
                .load("http://openweathermap.org/img/wn/"+weatherIconID+"@2x.png")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return true;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .apply(new RequestOptions().centerCrop()).into(weatherIcon);
    }

    @Override
    public void onUpdateWeatherByCord(WeatherMainStateByCord weatherMainStateByCord) {
        cityName.setText(weatherMainStateByCord.getName());
        temperature.setText(weatherMainStateByCord.getMain().getTemp()+" C\u00B0");
        temperature.setTextColor(getResources().getColor(R.color.white));
        precipitation.setText(weatherMainStateByCord.getMain().getPressure()+"");
        humidity.setText(weatherMainStateByCord.getMain().getHumidity()+"%");
        windSpeed.setText(weatherMainStateByCord.getWind().getSpeed()+" km/h");
        weatherDescription.setText(weatherMainStateByCord.getWeather().get(0).getDescription());
        weatherDescription.setTextColor(getResources().getColor(R.color.white));
        cityID = weatherMainStateByCord.getId();
        String weatherIconIdByCord = weatherMainStateByCord.getWeather().get(0).getIcon();

        Glide.with(MainActivity.this)
                .load("http://openweathermap.org/img/wn/"+weatherIconIdByCord+"@2x.png")
                .apply(new RequestOptions().centerCrop()).into(weatherIcon);

    }

    @Override
    public void onFailure() {
        //ToastHelper.toastCustomization();
        //Toasty.error(this, "Invalid city name", Toast.LENGTH_SHORT, true).show();
        Toast.makeText(this,"Invalid city name",Toast.LENGTH_SHORT).show();
    }

    public void startProgressDialog(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Please wait. ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    public void endProgressDialog(){
        progressDialog.dismiss();
    }

    public void startTomorrowWeatherActivity (){
        Intent intent = new Intent(this,TomorrowWeatherActivity.class);
        intent.putExtra("cityID", cityID);
        startActivity(intent);
    }

    public void startWeeklyWeatherActivity(){
        Intent intent = new Intent(this,WeeklyWeatherActivity.class);
        intent.putExtra("cityID", cityID);
        startActivity(intent);
    }

    public void onGetLocation() {
        if (isLocationPermissionGranted()) {
           getLocation();
        } else{
            requestLocationPermission();
        }
    }

    public void getLocation(){
        locationProviderClient = new FusedLocationProviderClient(this);
        @SuppressLint("MissingPermission") Task<Location> lastLocation = locationProviderClient.getLastLocation();
        lastLocation.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                if (location != null){
                    double laliTude = location.getLatitude();
                    double longiTude = location.getLongitude();
                    lat = String.valueOf(laliTude);
                    lon = String.valueOf(longiTude);
                    presenter.fetchForecastByCord(lat,lon);
                    startProgressDialog();
                }else {
                    Toast.makeText(this, "Location services turned off ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isLocationPermissionGranted() {
        int result = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private static final int LOCATION_REQUEST_CODE = 777;
    private void requestLocationPermission (){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
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
}
