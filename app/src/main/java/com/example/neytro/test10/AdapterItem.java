package com.example.neytro.test10;

import java.util.ArrayList;

/**
 * Created by Neytro on 2015-09-12.
 */
public class AdapterItem {
    private ArrayList<String> speed;
    private ArrayList<String> distance;
    private ArrayList<String> calory;
    private ArrayList<String> date;
    private ArrayList<String> time;
    private ArrayList<String> timePeriod;
    private ArrayList<String> image;

    //take value speed
    public void setSpeed(String s) {
        if (speed == null) {
            speed = new ArrayList<>();
            speed.add(s);
        } else {
            speed.add(s);
        }
    }

    //take value distance
    public void setDistance(String d) {
        if (distance == null) {
            distance = new ArrayList<>();
            distance.add(d);
        } else {
            distance.add(d);
        }
    }

    //take value calory
    public void setCalory(String c) {
        if (calory == null) {
            calory = new ArrayList<>();
            calory.add(c);
        } else {
            calory.add(c);
        }
    }

    //take value date
    public void setDate(String d) {
        if (date == null) {
            date = new ArrayList<>();
            date.add(d);
        } else {
            date.add(d);
        }
    }

    //take value time
    public void setTime(String t) {
        if (time == null) {
            time = new ArrayList<>();
            time.add(t);
        } else {
            time.add(t);
        }
    }

    //take value timePeriod
    public void setTimePeriod(String t) {
        if (timePeriod == null) {
            timePeriod = new ArrayList<>();
            timePeriod.add(t);
        } else {
            timePeriod.add(t);
        }
    }

    //take value image
    public void setImage(String i) {
        if (image == null) {
            image = new ArrayList<>();
            image.add(i);
        } else {
            image.add(i);
        }
    }

    //take value speed
    public ArrayList<String> getSpeed() {
        return speed;
    }

    public ArrayList<String> getDistance() {
        return distance;
    }

    public ArrayList<String> getCalory() {
        return calory;
    }

    public ArrayList<String> getDate() {
        return date;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public ArrayList<String> getTimePeriod() {
        return timePeriod;
    }

    public ArrayList<String> getImage() {
        return image;
    }
}
