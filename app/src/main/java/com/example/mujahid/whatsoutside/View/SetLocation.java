package com.example.mujahid.whatsoutside.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mujahid.whatsoutside.Model.MainData;
import com.example.mujahid.whatsoutside.Model.Weather;
import com.example.mujahid.whatsoutside.R;
import com.example.mujahid.whatsoutside.RESTclient.ApiClient;
import com.example.mujahid.whatsoutside.RESTclient.ApiInterface;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetLocation extends AppCompatActivity {
    protected GeoDataClient geoDataClient;
    SimpleLocation simpleLocation;
    int PLACE_PICKER_REQUEST = 1;
    ApiInterface apiInterface;
    ProgressDialog dialog;
    private static final String appid = "49e205a0f7848605b41aa220e12788f3";

    //TEst views by TExtview
    TextView sky,temp,pressure, wind_speed, hum, tm_max, tm_min, sea_level, speed, sunrise, sunset, country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Inerval Started");
        dialog.show();
        geoDataClient = Places.getGeoDataClient(this, null);
        simpleLocation = new SimpleLocation(this, true,false,120*1000,true);
        if(!simpleLocation.hasLocationEnabled()){
            SimpleLocation.openSettings(this);
        }

        simpleLocation.setListener(new SimpleLocation.Listener() {

            public void onPositionChanged() {
/*
                double longitude = simpleLocation.getLongitude();
                double latitute = simpleLocation.getLatitude();
                if(dialog.isShowing()){
                    dialog.dismiss();
                    dialog.cancel();
                }  */
              //  Toast.makeText(getApplicationContext(),String.format("Lon %f and Lat %f",longitude, latitute),Toast.LENGTH_LONG).show();

            }

        });

        //initilize textview
        sky = findViewById(R.id.sky);
        temp = findViewById(R.id.temp);
        pressure = findViewById(R.id.pressure);
        hum = findViewById(R.id.hum);
        tm_min = findViewById(R.id.tmp_min);
        tm_max = findViewById(R.id.tmp_max);
        sea_level = findViewById(R.id.sea_level);
        wind_speed = findViewById(R.id.wind_speed);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        country = findViewById(R.id.country);


    }


    public void onSet(View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                LatLng latLng = place.getLatLng();
                Intent intent = new Intent(this, MapsActivity.class);
                double[] dat = new double[2];
                dat[0] = latLng.latitude;
                dat[1] = latLng.longitude;
                intent.putExtra("location",dat);
                startActivity(intent);
                 dialog = new ProgressDialog(this);
                 /*
                dialog.setMessage("getting data");
                dialog.show();
                Log.d("test:",String.format("Lon: %s Lat %s",latLng.longitude,latLng.latitude));
                getWeather(latLng.longitude,latLng.latitude);
                */
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        simpleLocation.beginUpdates();

        // ...
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        simpleLocation.endUpdates();

        // ...

        super.onPause();
    }

    private void getWeather(double lon, double lan){
    apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
   Call<MainData> dataCall =  apiInterface.getWeather(lon,lan,appid);
   dataCall.enqueue(new Callback<MainData>() {
       @SuppressLint("DefaultLocale")
       @Override
       public void onResponse(Call<MainData> call, Response<MainData> response) {
           MainData data = response.body();

           if (data != null) {
               List<Weather> weatherList = data.getWeather();
               sky.setText(String.format("Sky is %s", weatherList.get(0).getDescription()));
               temp.setText(String.format("Temp: %f",data.getMain().getTemp()));
               pressure.setText(String.format("Pressure: %f",data.getMain().getPressure()));
               hum.setText(String.format("Humadity: %d",data.getMain().getHumidity()));
               tm_max.setText(String.format("Temp Max: %f",data.getMain().getTemp_max()));
               tm_min.setText(String.format("Temp Min %f",data.getMain().getTemp_min()));
               wind_speed.setText(String.format("Speed: %f",data.getWind().getSpeed()));
               sunrise.setText(String.format("%s",getTime(data.getSys().getSunrise())));
               sunset.setText(String.format("%s",getTime(data.getSys().getSunset())));
               country.setText(data.getName());
                dialog.dismiss();
           }


       }

       @Override
       public void onFailure(Call<MainData> call, Throwable t) {
        Log.d("error",t.getMessage());
       }
   });

    }

    private String getTime(int unix){
        @SuppressLint("SimpleDateFormat") String dateFormat = new SimpleDateFormat("HH:MM:SS").format(new Date(unix*1000L));
        return dateFormat;
    }

}