package com.phucnguyen.googlemapssearch;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                MarkerOptions markerOptions = new MarkerOptions();

                if (location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(MainActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);


                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address myAddress = addressList.get(i);
                                LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                                markerOptions.position(latLng);
                                map.addMarker(markerOptions);
                                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Location not found!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                else {
                    Toast.makeText(MainActivity.this, "Please write location name", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.851887, 106.759163);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in TDC"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
