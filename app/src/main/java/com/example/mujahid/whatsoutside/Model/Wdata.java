package com.example.mujahid.whatsoutside.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mujahid on 2/12/2018.
 */

public class Wdata implements Serializable {
    Main main;

    public Wdata(Main main, List<Weather> weather, int dt) {
        this.main = main;
        this.weather = weather;
        this.dt = dt;
    }

   List<Weather> weather;

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public int getDt() {
        return dt;
    }

    private int dt;


}
