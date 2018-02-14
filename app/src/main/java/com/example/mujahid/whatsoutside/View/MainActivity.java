package com.example.mujahid.whatsoutside.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mujahid.whatsoutside.Model.Test;
import com.example.mujahid.whatsoutside.Model.Wdata;
import com.example.mujahid.whatsoutside.R;
import com.example.mujahid.whatsoutside.RESTclient.ApiClient;
import com.example.mujahid.whatsoutside.RESTclient.ApiInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
 GoogleApiClient apiClient ;
 ApiInterface apiInterface;
Test test;
    ProgressDialog dialog;
    ListView listView;
List<String> actualDate = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         dialog = new ProgressDialog(this);

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                                  @Override
                                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                                      if(report.areAllPermissionsGranted()){
                                          Toast.makeText(getApplicationContext(),"All permission granted!",Toast.LENGTH_LONG).show();
                                      }else {
                                          showSettingsDialog();
                                      }
                                  }
                                  @Override
                                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                  }
                              }

                ).onSameThread()
                .check();

apiClient = new GoogleApiClient
        .Builder(this)
        .addApi(Places.GEO_DATA_API)
        .addApi(Places.PLACE_DETECTION_API)
        .enableAutoManage(this,this)
        .build();

        listView = findViewById(R.id.listview);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SetLocation.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.multi) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onTest(View view) {
        dialog.setMessage("Loading.....");
        dialog.show();
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<Test> testCall= apiInterface.get5DaysData(89.5546,22.8207,"49e205a0f7848605b41aa220e12788f3");
        testCall.enqueue(new Callback<Test>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(Call<Test> call, Response<Test> response) {
                test = response.body();
                List<Wdata> wdata = null;
                if (test != null) {
                    wdata = test.getList();
                }

                Set<String> keep = new HashSet<>();

                for(int i = 0; i < wdata.size(); i++){

        actualDate.add(DateFormater(wdata.get(i).getDt()));
                }

                keep.addAll(actualDate);
                actualDate.clear();
                actualDate.addAll(keep);
List<Date> list = new ArrayList<>();
                for(int d = 0; d < actualDate.size(); d++){
                    Date test = null;
                    try {
                        test = new SimpleDateFormat("yyyy-MM-dd").parse(actualDate.get(d));
                        list.add(test);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                Collections.sort(list, new Comparator<Date>() {
                    @Override
                    public int compare(Date date, Date t1) {
                        return Integer.valueOf(date.getDate()).compareTo(t1.getDate());
                    }
                });

            final List<List<Wdata>> container = new ArrayList<>();

            List<Wdata> data = new ArrayList<>();


                for(int i = 0; i < list.size(); i++){

                  container.add(data);
                  data.clear();

                    for(int j = 0; j < wdata.size(); j++){

                        if(DateMake(wdata.get(j).getDt()).getDate()==list.get(i).getDate()){

                            data.add(wdata.get(j));

                        }
                    }

                }

                List<String> keeps = new ArrayList<>();
                for(Date l : list){
                    keeps.add(DateFDate(l));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, keeps);
                listView.setAdapter(adapter);
                Log.d("size",String.format("%d",container.size()));
                if(dialog.isShowing()){
                    dialog.dismiss();
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), showDetail.class);
                                intent.putExtra("list", (Serializable) container.get(0));
                                startActivity(intent);
                                break;
                            case 1:

                                break;
                            case 2:

                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<Test> call, Throwable t) {
            Log.d("error",t.getMessage());
            }
        });
    }

    public String DateFormater(int unix){
        Date a = new Date(unix * 1000L);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        return formater.format(a);
    }
    public Date DateMake(int unix){
        return  new Date(unix * 1000L);
    }

    public String DateFDate(Date d){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEEE, yyyy-MM-dd");
       return format.format(d);
    }
}
