package com.example.mujahid.whatsoutside.Model;

/**
 * Created by Mujahid on 2/8/2018.
 */

public class Main {

    private double temp;
    private double pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;
    private double sea_level;

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public Main(double temp, double pressure, int humidity, double temp_min, double temp_max, double sea_level) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.sea_level = sea_level;
    }
}
