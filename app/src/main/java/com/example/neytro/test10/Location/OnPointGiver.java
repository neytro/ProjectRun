package com.example.neytro.test10.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
/**
 * Created by Neytro on 2015-11-05.
 */
public interface OnPointGiver {
    public void getAllPoints(ArrayList<LatLng> listOfPoint);
}
