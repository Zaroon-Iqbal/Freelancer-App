package com.freelancer.ui.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.freelancer.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/*
This is the activity responsible for accessing the Google Map API and displaying the current
location the of the user. Currently it only shows the location of the user and not the
location of the contractor as well. Worked on by Edward Kuoch
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    FrameLayout map;
    Location currentLocation;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE = 101;
    //When created it creates a fused client and determines permissions.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);
        map = findViewById(R.id.maps);
        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }

    //Method that checks if the user grants the permission to use their exact location.
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    //Provides the Longitude and Latitude coordinates for the map and creates a marker.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My Current Location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        googleMap.addMarker(markerOptions);
    }

    //Checks to see if the user grants permission to use their location.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation();
            }
        }
    }
}