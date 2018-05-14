package com.example.ensai.switchbetweenactivities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import xdroid.toaster.Toaster;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double xArret = 5, yArret = -30,xBus=0.0,yBus=0.0;
    private String[] tokens;
    public String arret,bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent i = getIntent();
        // Receiving the Data
        xArret = i.getDoubleExtra("coordx", 10.0);
        yArret = i.getDoubleExtra("coordy", 10.0);
        arret = i.getStringExtra("arret");
        xBus = i.getDoubleExtra("coordBusx", 10.0);
        yBus = i.getDoubleExtra("coordBusy", 10.0);
        bus = i.getStringExtra("bus");
        String data=i.getStringExtra("data");



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

        //Toaster.toast(xArret+" "+yArret);

        //mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        //LatLng ARRET = new LatLng(xArret, yArret);
        /*mMap.addMarker(new MarkerOptions()
                .title("Arret")
                .snippet(arret)
                .position(ARRET))
                ;*/

        LatLng ARRET = new LatLng(xArret, yArret);
        mMap.addMarker(new MarkerOptions()
                .position(ARRET)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        //mMap.addMarker(new MarkerOptions().position(arret).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ARRET, 17));


        /*LatLng BUS = new LatLng(xBus, yBus);
        mMap.addMarker(new MarkerOptions()
                .title("Bus")
                .snippet(bus)
                .position(BUS))
        ;*/

        /*LatLng BUS = new LatLng(xBus, yBus);
        mMap.addMarker(new MarkerOptions()
                .position(BUS)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(ARRET)
                .include(BUS)
                .build();


        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BUS, 11));

        int padding = 40; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.moveCamera(cu);

        /*while(j<tokens.length){
            b = new LatLng(Double.parseDouble(tokens[0]),Double.parseDouble(tokens[1]));
            mMap.addMarker(new MarkerOptions()
                    //.title(this.arret)
                    //.snippet(this.arret)
                    .position(b));
            j=j+2;
        }*/


    }
}
