package com.example.shubhamchauhan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude;
    Double longitude;

    public static void startIntent(Context context) {
        context.startActivity(new Intent(context, MapsActivity.class));
    }

    public static void startIntent(Context context, Bundle bundle) {
        Intent i = new Intent(context, MapsActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Bundle b = getIntent().getExtras();
        latitude = Double.parseDouble(b.getString("latitude"));
        longitude = Double.parseDouble(b.getString("longitude"));
            Log.d("latlong", b.getString("latitude") );
            Log.d("latlong", b.getString("longitude") );
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //MapTypeId.ROADMAP
        //MapTypeId.SATELLITE
        //MapTypeId.HYBRID
        //MapTypeId.TERRAI
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(false);
    }
}
