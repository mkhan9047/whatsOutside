package com.example.mujahid.whatsoutside.MulitForcastModel;

import com.example.mujahid.whatsoutside.Model.Weather;

import java.util.List;

/**
 * Created by Mujahid on 2/9/2018.
 */

public class DataList {
    List<Weather> weather;
    Temp temp;
    private int dt;

    public Temp getDay() {
        return temp;
    }

    private int humidity;
    private int clouds;

    public DataList(List<Weather> weather, int dt, int humidity, int clouds, Temp day) {
        this.weather = weather;
        this.dt = dt;
        this.humidity = humidity;
        this.clouds = clouds;
        this.temp = day;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public int getDt() {
        return dt;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getClouds() {
        return clouds;
    }
}
