package com.example.neytro.test10.Location;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-11-01.
 */
public class GoogleMapsCamera implements PointsGiver {
    GoogleMap googleMap;
    ArrayList<LatLng> coordinatePoints;

    public void folowGpsPosition(GoogleMap googleMap, Location location) {
        LatLng actualPosition = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(actualPosition));
    }

    public void catchAllItems(GoogleMap googleMap) {
        final int BOUND_FRAME = 100;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (coordinatePoints.size() >= 1) {
            for (LatLng points : coordinatePoints) {
                builder.include(points);
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), BOUND_FRAME));
        }
    }

   /* public void centerCamera(GoogleMap googleMap,Location location) {
        final int ZOOM_POSITION = 14;
        LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, ZOOM_POSITION));
        googleMap.getCameraPosition();
    }*/

    @Override
    public void getAllPoints(MainLocation mainLocation) {
        coordinatePoints = mainLocation.getAllPoints();
    }
}
