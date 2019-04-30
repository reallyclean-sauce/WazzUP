package com.eeepips.wazzup;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.eeepips.wazzup.AnnouncementActivities.VenueEventsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;


import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.widget.Toast;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.List;

public class MapView extends FragmentActivity implements OnInfoWindowClickListener, OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 101;
    private GoogleMap mMap;
    public UiSettings mapSettings;
    static final LatLng EEE_Marker = new LatLng(14.6495422, 121.0683548);
    static final LatLng Vinzons_Marker = new LatLng(14.654091, 121.073603);
    static final LatLng Romulo_Marker = new LatLng(14.6571125, 121.072966);
    private Marker UP_EEEI;
    private Marker Vinzons_Hall;
    private Marker Romulo_Hall;
    Button getDirection;
    private Polyline currentPolyline;
    private MapFragment mapFragment;
    private boolean isFirstTime = true;

    protected void requestPermission(String permissionType,
                                     int requestCode) {

        ActivityCompat.requestPermissions(this,
                new String[]{permissionType}, requestCode
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                            "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else {

                    SupportMapFragment mapFragment =
                            (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

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
        double latitude;
        double longitude;
        List<Address> geocodeMatches = null;

        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);

//        Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (mMap != null) {
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_REQUEST_CODE);
            }
        }

        UP_EEEI = mMap.addMarker(new MarkerOptions()
                .position(EEE_Marker)
                .title("UP EEEI")
                .snippet("Tap to view events"));
        UP_EEEI.setTag(0);

        Romulo_Hall = mMap.addMarker(new MarkerOptions()
                .position(Romulo_Marker)
                .title("Romulo Hall")
                .snippet("Tap to view events"));
        Romulo_Hall.setTag(0);

        Vinzons_Hall = mMap.addMarker(new MarkerOptions()
                .position(Vinzons_Marker)
                .title("Vinzons Hall")
                .snippet("Tap to view events"));
        Vinzons_Hall.setTag(0);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EEE_Marker, 15));

        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
    }

    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, "Info Window Tapped",
//                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MapView.this, VenueEventsActivity.class);
        String temp = marker.getTitle();
        if (temp.equals("UP EEEI")) {
            intent.putExtra("Venue", marker.getTitle());
            startActivity(intent);
        } else if (temp.equals("Vinzons Hall")) {
            intent.putExtra("Venue", marker.getTitle());
            startActivity(intent);
        } else if (temp.equals("Romulo Hall")) {
            intent.putExtra("Venue", marker.getTitle());
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Events Exist",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

