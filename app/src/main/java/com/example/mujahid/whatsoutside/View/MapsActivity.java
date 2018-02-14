package com.example.mujahid.whatsoutside.View;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mujahid.whatsoutside.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;
import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double[] getdata;
    String OWM_TILE_URL = "http://tile.openweathermap.org/map/{layer}/{z}/{x}/{y}.png?appid={49e205a0f7848605b41aa220e12788f3}";
    String tileType = "clouds_new";
    private TileOverlay tileOver;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getdata = getIntent().getDoubleArrayExtra("location");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getdata[0], getdata[1]);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Place"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,8.0f));
        TileProvider tileProvider = new UrlTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {

    /* Define the URL pattern for the tile images */
                String s = String.format(Locale.US, "http://tile.openweathermap.org/map/temp_new/%d/%d/%d.png?appid=49e205a0f7848605b41aa220e12788f3",
                        zoom, x, y);


                try {
                    return new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
            }
        };

        TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions()
                .tileProvider(tileProvider));
    }
}
