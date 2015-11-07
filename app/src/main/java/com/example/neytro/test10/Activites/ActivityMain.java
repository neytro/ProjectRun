package com.example.neytro.test10.Activites;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.example.neytro.test10.ActualTime;
import com.example.neytro.test10.DbColumns;
import com.example.neytro.test10.DbCreate;
import com.example.neytro.test10.Fragments.FragmentMain;
import com.example.neytro.test10.LoadingImage;
import com.example.neytro.test10.Location.GoogleServiceConnection;
import com.example.neytro.test10.MainActionBar;
import com.example.neytro.test10.R;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class ActivityMain extends ActionBarActivity {
    private android.support.v7.app.ActionBar actionBarMain;
    private View viewCustomActionBar;
    private View viewFragmentmain;
    private FragmentMain fragmentMain = new FragmentMain();
    private FileOutputStream fileOutputStream;
    private Chronometer chronometer;

    private ImageView imageViewPosition;
    private ImageView imageViewOverflow;
    private ImageView imageViewMap;
    private MainActionBar mainActionBar;
    private float calory = 0;
    private float speed = 0;
    private float kilometers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFragmentmain = LayoutInflater.from(this).inflate(R.layout.fragment_main, null);
        setMainFragment();
        mainActionBar = new MainActionBar(this, getSupportActionBar());
        mainActionBar.displayActionBar();
        GoogleServiceConnection service = new GoogleServiceConnection(this, fragmentMain);
        service.connectGoogleService();
    }

    //load FragmentMain
    private void setMainFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragmentMain).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //listener for all smartphone buttons
    /*@Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //setOnPupMenu(imageViewOverflow);
        }
        return super.onKeyUp(keyCode, event);
    }*/

    //alertdialog to save history
    public void alertDialogMap(final Bitmap bitmap) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(getString(R.string.saveHistory));
        alertDialog.setMessage(getString(R.string.saveState));
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSnapshot(bitmap);
                saveDatabase();
                resetPeriodTime();
                resetKilometry();
            }
        });
        alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetPeriodTime();
            }
        });
        alertDialog.show();
    }

    //take screenshot form googleMap
    private void getSnapshot(Bitmap bitmap) {
        final int QUALITY = 100;
        try {
            fileOutputStream = new FileOutputStream(LoadingImage.pathForImage());
            bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //save value in database
    private void saveDatabase() {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        DbCreate myDatabase = new DbCreate(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        database.insert(DbColumns.FeedEntry.TABLE_NAME, null, getContentValue());
    }

    private ContentValues getContentValue() {
        String pathForImage = LoadingImage.pathForImage().getAbsolutePath();
        ContentValues values = new ContentValues();
        values.put(DbColumns.FeedEntry.COLUMN_NAME_ENTRY_ID, "1");
        values.put(DbColumns.FeedEntry.COLUMN_NAME_CALORY, calory + getString(R.string.kiloCalory));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_DISTANCE, kilometers + getString(R.string.unitKilometer));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_DATE, getRealTime().getDate());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_SPEED, speed + getString(R.string.unitKilometerPerHour));
        values.put(DbColumns.FeedEntry.COLUMN_NAME_TIME, getRealTime().getTime());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_TIME_PERIOD, fragmentMain.setPeriodTime());
        values.put(DbColumns.FeedEntry.COLUMN_NAME_SCREENSHOOT, pathForImage);
        return values;
    }

    private void resetPeriodTime() {
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

    //save last state of fragment
   /* @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            AlertDialogs alertDialogs = new AlertDialogs(this);
            alertDialogs.alertDialogExit();
        } else {
        }
    }*/

    //reset kilometers
    public void resetKilometry() {
        kilometers = 0;
        calory = 0;
    }

    //alertdialog for Exit form application
    private void alertDialogExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(getString(R.string.exit));
        alertDialog.setMessage(getString(R.string.messageExit));
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.no), null);
        alertDialog.show();
    }
}






