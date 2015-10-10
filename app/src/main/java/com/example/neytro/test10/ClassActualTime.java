package com.example.neytro.test10;
/**
 * Created by Neytro on 2015-09-12.
 * keep value of actual time
 */
public class ClassActualTime {
    private String time;
    private String date;

    public ClassActualTime(String... var1) {
        time = var1[0];
        date = var1[1];
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }
}
