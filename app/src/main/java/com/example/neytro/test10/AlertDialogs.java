package com.example.neytro.test10;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

import com.example.neytro.test10.Fragments.FragmentMain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by Neytro on 2015-10-26.
 */
public class AlertDialogs {
    private Context context;
    private Chronometer chronometer;
    private float calory = 0;
    private float speed = 0;
    private float kilometers = 0;
    private View view;
    static private FragmentMain fragmentMain = new FragmentMain();

    public AlertDialogs(Context context) {
        this.context = context;
    }

    public void alertDialogGps() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(context.getString(R.string.GPStitle));
        alertDialog.setMessage(context.getString(R.string.GPSmessage));
        alertDialog.setPositiveButton(context.getString(R.string.Settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNeutralButton(context.getString(R.string.ignor), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void alertDialogExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(context.getString(R.string.exit));
        alertDialog.setMessage(context.getString(R.string.messageExit));
        alertDialog.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton(context.getString(R.string.no), null);
        alertDialog.show();
    }

    public void alertDialogMap(final Bitmap bitmap) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(context.getString(R.string.saveHistory));
        alertDialog.setMessage(context.getString(R.string.saveState));
        alertDialog.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                takeScreenShoot();
                saveDatabase();
                resetPeriodTime();
                resetKilometry();
            }

            private void takeScreenShoot() {
                LoadingImage.getSnapshot(bitmap);
            }
        });
        alertDialog.setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetPeriodTime();
            }
        });
        alertDialog.show();
    }

    //save value in database
    private void saveDatabase() {
        DbCreate myDatabase = new DbCreate(context);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        database.insert(DbColumns.FeedEntry.TABLE_NAME, null, getContentValue());
    }

    private ContentValues getContentValue() {
        String pathForImage = LoadingImage.pathForImage().getAbsolutePath();
        ContentValues values = new ContentValues();
        values.put(DbColumns.FeedEntry.COLUMN_NAME_ENTRY_ID, "1");
        values.put(DbColumns.FeedEntry.COLUMN_NAME_CALORY, calory + context.getString(R.string.kiloCalory));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_DISTANCE, kilometers + context.getString(R.string.unitKilometer));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_DATE, getRealTime().getDate());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_SPEED, speed + context.getString(R.string.unitKilometerPerHour));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_TIME, getRealTime().getTime());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_TIME_PERIOD, fragmentMain.setPeriodTime());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_SCREENSHOOT, pathForImage);
        return values;
    }

    private void resetPeriodTime() {
        view = LayoutInflater.from(context).inflate(R.layout.fragment_main, null);
        chronometer = (Chronometer) view.findViewById(R.id.chronometer);
        if (chronometer != null) {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
    }

    //get real time
    private ActualTime getRealTime() {
        String time;
        String date;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        time = simpleDateFormatTime.format(c.getTime());
        date = simpleDateFormatDate.format(c.getTime());
        ActualTime actualTime = new ActualTime(time, date);
        return actualTime;
    }

    //reset kilometers
    public void resetKilometry() {
        kilometers = 0;
        calory = 0;
    }
}
