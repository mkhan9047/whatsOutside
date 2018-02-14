package com.example.mujahid.whatsoutside.Model;

import java.util.List;

/**
 * Created by Mujahid on 2/8/2018.
 */

public class MainData {
    private String name;
   private List<Weather> weather;
   private Main main;
   private Wind wind;
   private Sys sys;

    public MainData(String name,  List<Weather> weather, Main main, Wind wind, Sys sys) {
        this.name = name;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.sys = sys;
    }

    public String getName() {
        return name;
    }

    public  List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Sys getSys() {
        return sys;
    }
}
