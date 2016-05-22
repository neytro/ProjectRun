package com.example.neytro.test10.Location;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.neytro.test10.Activites.ActivityMain;
import com.example.neytro.test10.AlertDialogs;
import com.example.neytro.test10.Fragments.FragmentMain;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
/**
 * Created by Neytro on 2015-11-01.
 */
public class GoogleMapsMain implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener,
        GoogleMap.OnMapLoadedCallback,
        GoogleMap.SnapshotReadyCallback {
    private GoogleMapsItems googleMapsItems;
    private Context context;
    private FragmentMain fragmentMain;
    private GoogleMap googleMap;
    private Location location;
    private GoogleMapsCamera googleMapsCamera;

    public GoogleMapsMain(Context context) {
        fragmentMain = ActivityMain.getFragmentMain();
        this.context = context;
        googleMapsCamera = new GoogleMapsCamera();
    }

    @Override
    public void onMapReady(GoogleMap g) {
        googleMap = g;
        googleMapsItems = new GoogleMapsItems(googleMap);
        settingsForMap();
        if (fragmentMain.isRestartReady()) {
            Toast.makeText(context, "ready", Toast.LENGTH_SHORT).show();
            activeListenerForMapLoader();
            deactiveLocationChanges();
            drawRoute();
        }
    }

    private void settingsForMap() {
        setUiSettings();
        activeLocationChanges();
        setMapType();
    }

    private void setUiSettings() {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
    }

    private void activeLocationChanges() {
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(this);
    }

    @Override
    public void onMyLocationChange(Location location) {
        this.location = location;
        centerCamera();
        drawRoute();
    }

    private void setMapType() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    private void deactiveLocationChanges() {
        googleMap.setOnMyLocationChangeListener(null);
    }

    private void activeListenerForMapLoader() {
        googleMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onMapLoaded() {
        Toast.makeText(context, "polaczyles sie", Toast.LENGTH_SHORT).show();
        googleMap.snapshot(this);
    }

    @Override
    public void onSnapshotReady(Bitmap bitmap) {
        showAlertDialogMap(bitmap);
    }

    private void showAlertDialogMap(Bitmap bitmap) {
        AlertDialogs alertDialogs = new AlertDialogs(context);
        alertDialogs.alertDialogMap(bitmap);
    }

    private void drawRoute() {
        googleMapsItems.drawRouteAndaddMarker();
    }

    private void moveCameraToCenterOfScreen() {
        if (location != null) {
            googleMapsCamera.folowGpsPosition(googleMap, location);
        }
    }

    private void centerCamera() {
        if (location != null) {
            googleMapsCamera.catchAllItems(googleMap);
        }
    }
}
