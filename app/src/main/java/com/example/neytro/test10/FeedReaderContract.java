package com.example.neytro.test10;
import android.provider.BaseColumns;
/**
 * Created by Neytro on 2015-07-20.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "data";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_TIME_PERIOD = "timePeriod";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DISTANCE = "distance";
        public static final String COLUMN_NAME_CALORY = "calory";
        public static final String COLUMN_NAME_SPEED = "speed";
        public static final String COLUMN_NAME_SCREENSHOOT = "screenshoot";
    }
}
