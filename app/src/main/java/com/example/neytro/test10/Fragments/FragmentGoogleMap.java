package com.example.neytro.test10.Fragments;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import com.example.neytro.test10.Location.GoogleMapsMain;
import com.example.neytro.test10.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
/**
 * Created by Neytro on 2015-11-01.
 */
public class FragmentGoogleMap {
    private MapFragment mapFragment;
    private OnMapReadyCallback listenerForMap;
    private Context context;
    private FragmentManager fragmentManager;

    public FragmentGoogleMap(Context context) {
        this.context = context;
        listenerForMap = new GoogleMapsMain(context);
    }

    public void setMapFragment() {
        mapFragment = MapFragment.newInstance(getOption());
        fragmentManager = ((Activity) context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim, R.animator.exit_anim);
        fragmentTransaction.add(R.id.fragmentContainer, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(listenerForMap);
    }

    public void addToBackStack() {
        fragmentManager.popBackStack();
    }

    public FragmentManager getFragmentmanager() {
        return fragmentManager;

    }

    private GoogleMapOptions getOption() {
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        return options;
    }
}
