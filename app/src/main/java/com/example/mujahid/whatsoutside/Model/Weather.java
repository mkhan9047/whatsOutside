package com.example.mujahid.whatsoutside.Model;

/**
 * Created by Mujahid on 2/8/2018.
 */

public class Weather {
    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    private String main;
    private String description;
    private String icon;

    public Weather(String main, String description, String icon) {
        this.main = main;
        this.icon = icon;
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }
}
