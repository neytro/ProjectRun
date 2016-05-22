package com.example.neytro.test10.Location;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.example.neytro.test10.AlertDialogs;
import com.example.neytro.test10.ConnectionTester;
import com.example.neytro.test10.FragmentViewValue;
import com.example.neytro.test10.Fragments.FragmentMain;
import com.example.neytro.test10.RunningValues;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-10-28.
 */
public class MainLocation implements LocationListener, GpsStatus.Listener {
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private ConnectionTester connectionTester;
    private Context context;
    private int numberOfLocationPoint = 0;
    private RunningValues runningValues;
    private FragmentViewValue fragmentValue;
    private ArrayList<LatLng> coordinatedPoints;
    private OnPointGiver onPointGiver;

    public MainLocation(GoogleApiClient googleApiClient, Context context, FragmentMain fragmentMain) {
        this.googleApiClient = googleApiClient;
        this.context = context;
        fragmentValue = fragmentMain;
        connectionTester = new ConnectionTester(context);
        runningValues = new RunningValues();
        coordinatedPoints = new ArrayList<>();
        onPointGiver = null;
    }


    public void startUpdateLocation() {
        createLocationRequest();
        getLastLocation();
        startLocationUpdates();
        activeGpsListener();
        if (!connectionTester.isGpsOn()) {
            showDialogGps();
        }
    }
    private void activeGpsListener() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(this);
    }

    private void showDialogGps() {
        AlertDialogs alertDialogs = new AlertDialogs(context);
        alertDialogs.alertDialogGps();
    }

    private void startLocationUpdates() {
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //stop Location update
    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    private void getLastLocation() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    @Override
    public void onLocationChanged(Location location) {
        calculateValues(location);
    }

    private void calculateValues(Location location) {
        final float MIN_SPEED = (float) 0.5;
        //todo: value for testing
        location.setSpeed(15);
        if (location != null && location.getSpeed() > MIN_SPEED) /*&& fragmentMain.isButtonStartClicked() && isGPSready) */ {
            numberOfLocationPoint++;
            if (numberOfLocationPoint != 1) {
                runningValues.calculateDistance(lastLocation, location);
                runningValues.calculateSpeed(location);
                runningValues.calculateCalory();
                addPoints(location);
                fragmentValue.getRunningValue(runningValues);
                lastLocation.set(location);
            }
        }
    }

    private void addPoints(Location location) {
        LatLng points = new LatLng(location.getLatitude(), location.getLongitude());
        coordinatedPoints.add(points);
        transferPointsToColaborators();
    }

    private void transferPointsToColaborators() {
        onPointGiver.getAllPoints(coordinatedPoints);
    }

    public void setOnPointGiver(OnPointGiver onPointGiver) {
        this.onPointGiver = onPointGiver;

    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                createLocationRequest();
                startLocationUpdates();
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                stopLocationUpdates();
                break;
        }
    }
}
