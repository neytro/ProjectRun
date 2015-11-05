package com.example.neytro.test10.Location;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import com.example.neytro.test10.ConnectionTester;
import com.example.neytro.test10.Fragments.FragmentMain;
import com.example.neytro.test10.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
/**
 * Created by Neytro on 2015-10-26.
 */
public class GoogleServiceConnection implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private Context context;
    private ConnectionTester connectionTester;
    private FragmentMain fragmentMain;
    private MainLocation mainLocation;


    public GoogleServiceConnection(Context context, FragmentMain fragmentMain) {
        this.fragmentMain = fragmentMain;
        this.context = context;
    }

    public void connectGoogleService() {
        googleApiClient = new GoogleApiClient.Builder(context).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocation();
    }

    private void startLocation() {
        OnPointGiver onPointGiver = new GoogleMapsItems();
        mainLocation = new MainLocation(googleApiClient, context, fragmentMain);
        mainLocation.startUpdateLocation();
        mainLocation.setOnPointGiver(onPointGiver);
    }

    @Override
    public void onConnectionSuspended(int i) {
        showMessageConnectionSuspended();
    }

    private void showMessageConnectionSuspended() {
        Resources resources = Resources.getSystem();
        CharSequence text = resources.getString(R.string.disconnected_GoogleService);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showMessageConnectionFailed();
    }

    private void showMessageConnectionFailed() {
        Resources resources = Resources.getSystem();
        CharSequence text = resources.getString(R.string.problemConnection_GoogleService);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
