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
    public void setSpeed(String speed) {
        if (this.speed == null) {
            this.speed = new ArrayList<>();
            this.speed.add(speed);
        } else {
            this.speed.add(speed);
        }
    }

    //take value distance
    public void setDistance(String distance) {
        if (this.distance == null) {
            this.distance = new ArrayList<>();
            this.distance.add(distance);
        } else {
            this.distance.add(distance);
        }
    }

    //take value calory
    public void setCalory(String calory) {
        if (this.calory == null) {
            this.calory = new ArrayList<>();
            this.calory.add(calory);
        } else {
            this.calory.add(calory);
        }
    }

    //take value date
    public void setDate(String date) {
        if (this.date == null) {
            this.date = new ArrayList<>();
            this.date.add(date);
        } else {
            this.date.add(date);
        }
    }

    //take value time
    public void setTime(String time) {
        if (this.time == null) {
            this.time = new ArrayList<>();
            this.time.add(time);
        } else {
            this.time.add(time);
        }
    }

    //take value timePeriod
    public void setTimePeriod(String timePeriod) {
        if (this.timePeriod == null) {
            this.timePeriod = new ArrayList<>();
            this.timePeriod.add(timePeriod);
        } else {
            this.timePeriod.add(timePeriod);
        }
    }

    //take value image
    public void setImage(String image) {
        if (this.image == null) {
            this.image = new ArrayList<>();
            this.image.add(image);
        } else {
            this.image.add(image);
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
