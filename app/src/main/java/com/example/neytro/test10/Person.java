package com.example.neytro.test10;
/**
 * Created by Neytro on 2015-10-25.
 */
public class Person implements SpeedMotion {
    private final float WALK = (float) 0.23;
    private final float FAST_RUN = (float) 0.75;
    private final float RUN = (float) 0.83;

    @Override
    public float slowSpeed() {
        return WALK;
    }

    @Override
    public float middleSpeed() {
        return RUN;
    }

    @Override
    public float fastSpeed() {
        return FAST_RUN;
    }
}
