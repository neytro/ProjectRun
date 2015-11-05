package com.example.neytro.test10.Location;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-10-10.
 */
public class GoogleMapsItems implements OnPointGiver {
    final int LINE_THINKNESS = 10;
    private GoogleMap googleMap;
    static private ArrayList<LatLng> coordinatePoints = new ArrayList<LatLng>();

    public GoogleMapsItems(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        this.coordinatePoints = getCordinateList;
    }

    public GoogleMapsItems() {
//        this.coordinatePoints = getCordinateList;
    }


    //draw route in google map
    public void drawRouteAndaddMarker() {
        if (coordinatePoints.size() > 1) {
            showRoute();
            addStartMarker();
        }
    }

    private void showRoute() {
        Polyline polyline;
        PolylineOptions options = new PolylineOptions();
        options.addAll(coordinatePoints);
        options.width(LINE_THINKNESS).color(Color.BLUE).geodesic(true).visible(true).zIndex(30);
        polyline = googleMap.addPolyline(options);
        polyline.setPoints(coordinatePoints);
    }

    private void addStartMarker() {
        googleMap.addMarker(createMarker());
    }

    private MarkerOptions createMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(coordinatePoints.get(0));
        markerOptions.title("START");
        return markerOptions;
    }

    @Override
    public void getAllPoints(ArrayList<LatLng> listOfPoint) {
        coordinatePoints = listOfPoint;
    }
}
