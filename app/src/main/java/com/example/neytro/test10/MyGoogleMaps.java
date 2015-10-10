package com.example.neytro.test10;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-10-10.
 */
public class MyGoogleMaps {
    private GoogleMap googleMap;
    private ArrayList<LatLng> coordList = new ArrayList<LatLng>();

    public MyGoogleMaps(GoogleMap var1, ArrayList<LatLng> var2) {
        googleMap = var1;
        coordList = var2;
    }

    //draw route in google map
    public void drawRoute() {
        Polyline polyline;
        PolylineOptions options = new PolylineOptions();
        if (coordList.size() > 1) {
            options.addAll(coordList);
            options.width(10).color(Color.BLUE).geodesic(true).visible(true).zIndex(30);
            polyline = googleMap.addPolyline(options);
            polyline.setPoints(coordList);
            googleMap.addMarker(new MarkerOptions().position(coordList.get(0)).title("START"));
        }
    }

    public void getPoint(Location location) {
        LatLng latLngRoute = new LatLng(location.getLatitude(), location.getLongitude());
        coordList.add(latLngRoute);
    }

    public void folowGpsPosition(Location var1) {
        LatLng position = new LatLng(var1.getLatitude(), var1.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
    }
}
