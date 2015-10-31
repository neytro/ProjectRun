package com.example.neytro.test10;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
/**
 * Created by Neytro on 2015-10-25.
 */
public class MyMapFragment {
    //// TODO: 2015-10-26 add MapFragment to MainActivity
    private MapFragment mMapFragment;

    public void setMapFragment() {
        /*mMapFragment = MapFragment.newInstance(getOption());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim, R.animator.exit_anim);
        fragmentTransaction.add(R.id.fragmentContainer, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);*/
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
