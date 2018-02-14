package com.example.mujahid.whatsoutside.RESTclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mujahid on 2/8/2018.
 */

public class ApiClient {
  private static final String base_url = "http://api.openweathermap.org/";

  private static Retrofit retrofit = null;

  public static Retrofit getRetrofit(){
      retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
      return retrofit;
  }
}
