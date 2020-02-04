package com.example.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {
    @Expose
    @SerializedName("temp_kf")
    private double temp_kf;
    @Expose
    @SerializedName("humidity")
    private int humidity;
    @Expose
    @SerializedName("grnd_level")
    private int grnd_level;
    @Expose
    @SerializedName("sea_level")
    private int sea_level;
    @Expose
    @SerializedName("pressure")
    private int pressure;
    @Expose
    @SerializedName("temp_max")
    private double temp_max;
    @Expose
    @SerializedName("temp_min")
    private double temp_min;
    @Expose
    @SerializedName("feels_like")
    private double feels_like;
    @Expose
    @SerializedName("temp")
    private double temp;

    public double getTemp_kf() {
        return temp_kf;
    }

    public void setTemp_kf(double temp_kf) {
        this.temp_kf = temp_kf;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(int grnd_level) {
        this.grnd_level = grnd_level;
    }

    public int getSea_level() {
        return sea_level;
    }

    public void setSea_level(int sea_level) {
        this.sea_level = sea_level;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public int getTemp() {
        int c = (int) Math.round(temp);
        return c;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
