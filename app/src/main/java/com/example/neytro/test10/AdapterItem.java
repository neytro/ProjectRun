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
    public void setSpeed(String var1) {
        if (speed == null) {
            speed = new ArrayList<>();
            speed.add(var1);
        } else {
            speed.add(var1);
        }
    }

    //take value distance
    public void setDistance(String var1) {
        if (distance == null) {
            distance = new ArrayList<>();
            distance.add(var1);
        } else {
            distance.add(var1);
        }
    }

    //take value calory
    public void setCalory(String var1) {
        if (calory == null) {
            calory = new ArrayList<>();
            calory.add(var1);
        } else {
            calory.add(var1);
        }
    }

    //take value date
    public void setDate(String var1) {
        if (date == null) {
            date = new ArrayList<>();
            date.add(var1);
        } else {
            date.add(var1);
        }
    }

    //take value time
    public void setTime(String var1) {
        if (time == null) {
            time = new ArrayList<>();
            time.add(var1);
        } else {
            time.add(var1);
        }
    }

    //take value timePeriod
    public void setTimePeriod(String var1) {
        if (timePeriod == null) {
            timePeriod = new ArrayList<>();
            timePeriod.add(var1);
        } else {
            timePeriod.add(var1);
        }
    }

    //take value image
    public void setImage(String var1) {
        if (image == null) {
            image = new ArrayList<>();
            image.add(var1);
        } else {
            image.add(var1);
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
