package com.example.neytro.test10.Location;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.example.neytro.test10.Activites.ActivityMain;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-11-01.
 */
public class GoogleMapsCamera {
    private ArrayList<LatLng> coordinatePoints;


    public void folowGpsPosition(GoogleMap googleMap, Location location) {
        LatLng actualPosition = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(actualPosition));
    }


    public void catchAllItems(GoogleMap googleMap) {
        coordinatePoints = GoogleMapsItems.getCordinatePoints();
        final int BOUND_FRAME = 100;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();


                if (coordinatePoints.size() >= 1) {
                    for (LatLng points : coordinatePoints) {
                        builder.include(points);
                    }
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), BOUND_FRAME));
                }

    }




}
