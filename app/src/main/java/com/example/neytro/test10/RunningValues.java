package com.example.neytro.test10;
import android.location.Location;
/**
 * Created by Neytro on 2015-10-31.
 */
public class RunningValues {
    private float kilometers;
    private float speed;
    private float calory;
    private SpeedMotion speedMotion = new Person();
    private final float KILOMETER_FACTOR = (float) 3.6;

    public void calculateKilometers(Location lastLocation, Location currentLocation) {
        kilometers = round(kilometers + lastLocation.distanceTo(currentLocation) / 1000, 2);
    }

    public void calculateSpeed(Location location) {
        float speedInKilometers = location.getSpeed() * KILOMETER_FACTOR;
        speed = round(speedInKilometers, 2);
    }

    public void calculateCalory() {
        float wynik = 0;
        if (speed < 5) {
            wynik = speedMotion.slowSpeed();
        } else if (speed < 10) {
            wynik = speedMotion.middleSpeed();
        } else if (speed > 10) {
            wynik = speedMotion.fastSpeed();
        }
        calory = round(calory + wynik, 2);
        //fragmentMain.getCalory(calory);
    }

    private float round(double f, int places) {
        float temp = (float) (f * (Math.pow(10, places)));
        temp = (Math.round(temp));
        temp = temp / (int) (Math.pow(10, places));
        return temp;
    }
}
