package com.example.mujahid.whatsoutside.RESTclient;

import com.example.mujahid.whatsoutside.Model.MainData;
import com.example.mujahid.whatsoutside.Model.Test;
import com.example.mujahid.whatsoutside.MulitForcastModel.GetWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Mujahid on 2/8/2018.
 */

public interface ApiInterface {

    @GET("/data/2.5/weather")
    Call<MainData> getWeather(@Query("lon") double lon, @Query("lat") double lat ,@Query("appid") String appid);

    @GET("/data/2.5/forecast/daily")
    Call<GetWeather> getMultiWeather(@Query("lon") double lon, @Query("lat") double lat , @Query("cnt") int cn, @Query("appid") String appid);

    @GET("/data/2.5/forecast")
    Call<Test> get5DaysData(@Query("lon") double lon, @Query("lat") double lat , @Query("appid") String appid);
}
