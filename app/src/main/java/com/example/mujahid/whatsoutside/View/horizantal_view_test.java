package com.example.mujahid.whatsoutside.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.mujahid.whatsoutside.CustomAdapters.Horizantal_recycle;
import com.example.mujahid.whatsoutside.CustomAdapters.MultiForcastRecycle;
import com.example.mujahid.whatsoutside.MulitForcastModel.DataList;
import com.example.mujahid.whatsoutside.MulitForcastModel.GetWeather;
import com.example.mujahid.whatsoutside.R;
import com.example.mujahid.whatsoutside.RESTclient.ApiClient;
import com.example.mujahid.whatsoutside.RESTclient.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class horizantal_view_test extends AppCompatActivity {
    private static final String appid = "49e205a0f7848605b41aa220e12788f3";
    ProgressDialog dialog;
    ApiInterface apiInterface;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Horizantal_recycle adapter;
    List<DataList> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizantal_view_test);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView = findViewById(R.id.horizantal_recycle);
        recyclerView.setLayoutManager(layoutManager);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Getting Data....");
        dialog.show();
        double[] data = getIntent().getDoubleArrayExtra("location");
        getWeather(data[0],data[1],14);

    }
    private void getWeather(double lat, double lon, int cmt){
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<GetWeather> call = apiInterface.getMultiWeather(lon,lat, cmt, appid);
        call.enqueue(new Callback<GetWeather>() {
            @Override
            public void onResponse(Call<GetWeather> call, Response<GetWeather> response) {
                GetWeather body =  response.body();
                if (body != null) {
                    list = body.getList();
                    adapter = new Horizantal_recycle(list,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetWeather> call, Throwable t) {
                Log.d("error",t.getMessage());
            }
        });


    }
}
