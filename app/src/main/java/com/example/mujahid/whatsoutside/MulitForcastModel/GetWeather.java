package com.example.mujahid.whatsoutside.MulitForcastModel;

import com.example.mujahid.whatsoutside.Model.Weather;

import java.util.List;

/**
 * Created by Mujahid on 2/9/2018.
 */

public class GetWeather {

    private List<DataList> list;

    public List<DataList> getList() {
        return list;
    }

    public GetWeather(List<DataList> list) {

        this.list = list;
    }
}
