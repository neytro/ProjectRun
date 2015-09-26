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
    public void setSpeed(String _speed) {
        if (speed == null) {
            speed = new ArrayList<>();
            speed.add(_speed);
        } else {
            speed.add(_speed);
        }
    }

    //take value distance
    public void setDistance(String _distance) {
        if (distance == null) {
            distance = new ArrayList<>();
            distance.add(_distance);
        } else {
            distance.add(_distance);
        }
    }

    //take value calory
    public void setCalory(String _calory) {
        if (calory == null) {
            calory = new ArrayList<>();
            calory.add(_calory);
        } else {
            calory.add(_calory);
        }
    }

    //take value date
    public void setDate(String _date) {
        if (date == null) {
            date = new ArrayList<>();
            date.add(_date);
        } else {
            date.add(_date);
        }
    }

    //take value time
    public void setTime(String _time) {
        if (time == null) {
            time = new ArrayList<>();
            time.add(_time);
        } else {
            time.add(_time);
        }
    }

    //take value timePeriod
    public void setTimePeriod(String _timePeriod) {
        if (timePeriod == null) {
            timePeriod = new ArrayList<>();
            timePeriod.add(_timePeriod);
        } else {
            timePeriod.add(_timePeriod);
        }
    }

    //take value image
    public void setImage(String _image) {
        if (image == null) {
            image = new ArrayList<>();
            image.add(_image);
        } else {
            image.add(_image);
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
