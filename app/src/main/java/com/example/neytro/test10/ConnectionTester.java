package com.example.neytro.test10;
import android.content.Context;
import android.content.res.Resources;
import android.location.LocationManager;
/**
 * Created by Neytro on 2015-10-26.
 */
public class ConnectionTester {
    Context context;
    Resources resources;

    public ConnectionTester(Context context) {
        this.context = context;
        resources = Resources.getSystem();
    }

    public boolean isGpsOn() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
