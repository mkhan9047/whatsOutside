package com.example.mujahid.whatsoutside.Model;

/**
 * Created by Mujahid on 2/8/2018.
 */

public class Sys {
    private String country;
    private int sunrise;
    private int sunset;

    public String getCountry() {
        return country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public Sys(String country, int sunrise, int sunset) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
