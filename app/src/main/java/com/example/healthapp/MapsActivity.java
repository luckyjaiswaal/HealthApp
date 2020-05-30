package com.example.healthapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng doctor1 = new LatLng(-33.917666, 151.074372);
        LatLng doctor2 = new LatLng(-33.917666, 152.074372);
        LatLng doctor3 = new LatLng(-33.917666, 153.074372);
        LatLng doctor4 = new LatLng(-33.917666, 154.074372);

        mMap.addMarker(new MarkerOptions().position(doctor1).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(doctor2).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(doctor3).title("Marker in Sydney"));
        mMap.addMarker(new MarkerOptions().position(doctor4).title("Marker in Sydney"));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(doctor1, 7F));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_layout, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.rtnDashboard:
                startActivity(new Intent(getApplicationContext(), MainDashboard.class));
                return true;


            default:
                return  super.onOptionsItemSelected(item);
        }


    }

}
