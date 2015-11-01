package com.example.neytro.test10;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Neytro on 2015-07-20.
 */
public class DbCreate extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + DbColumns.FeedEntry.TABLE_NAME + " (" +
                    DbColumns.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    DbColumns.FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_DISTANCE + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_CALORY + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_SPEED + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_SCREENSHOOT + TEXT_TYPE + COMMA_SEP +
                    DbColumns.FeedEntry.COLUMN_NAME_TIME_PERIOD + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbColumns.FeedEntry.TABLE_NAME;

    public DbCreate(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //upgrade database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
