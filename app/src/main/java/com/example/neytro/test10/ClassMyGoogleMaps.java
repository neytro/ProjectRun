package com.example.neytro.test10;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-10-10.
 */
public class ClassMyGoogleMaps {
    final int LINE_THINKNESS = 10;
    private GoogleMap googleMap;
    private ArrayList<LatLng> coordinateList = new ArrayList<LatLng>();

    public ClassMyGoogleMaps(GoogleMap var1, ArrayList<LatLng> var2) {
        googleMap = var1;
        coordinateList = var2;
    }

    //draw route in google map
    public void drawRouteAndaddMarker() {
        if (coordinateList.size() > 1) {
            showRoute();
            addStartMarker();
        }
    }

    private void showRoute() {
        Polyline polyline;
        PolylineOptions options = new PolylineOptions();
        options.addAll(coordinateList);
        options.width(LINE_THINKNESS).color(Color.BLUE).geodesic(true).visible(true).zIndex(30);
        polyline = googleMap.addPolyline(options);
        polyline.setPoints(coordinateList);
    }

    private void addStartMarker() {
        googleMap.addMarker(new MarkerOptions().position(coordinateList.get(0)).title("START"));
    }

    public void getPoint(Location location) {
        LatLng latLngRoute = new LatLng(location.getLatitude(), location.getLongitude());
        coordinateList.add(latLngRoute);
    }

    public void folowGpsPosition(Location location) {
        LatLng actualPosition = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(actualPosition));
    }

    public void centerCamera() {
        final int BOUND_FRAME = 100;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (coordinateList.size() >= 1) {
            for (LatLng points : coordinateList) {
                builder.include(points);
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), BOUND_FRAME));
        }
    }
}
