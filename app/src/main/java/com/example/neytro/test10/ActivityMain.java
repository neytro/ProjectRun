package com.example.neytro.test10;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class ActivityMain extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GpsStatus.Listener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener {
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private android.support.v7.app.ActionBar actionBarMain;
    private View viewCustomActionBar;
    private View viewFragmentmain;
    private FragmentMain fragmentMain = new FragmentMain();
    private ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    private Polyline polyline;
    private LatLng latLngRoute;
    private LatLng sydney;
    private MapFragment mMapFragment;
    private FileOutputStream fileOutputStream;
    private Chronometer chronometer;
    private ClassMyGoogleMaps myGoogleMap;
    private boolean GPSready = false;
    private int updatePosition = 0;
    private float calory = 0;
    private float speed = 0;
    private float kilometry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewFragmentmain = LayoutInflater.from(this).inflate(R.layout.fragment_main, null);
        setMainFragment();
        setCustomActionBar();
        connectGoogleService();
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

    //activate when GoogleService is connected
    @Override
    public void onConnected(Bundle bundle) {
        checkGPSsettings();
        createLocationRequest();
        calculatePosition();
        startLocationUpdates();
    }

    //activate when connection to the GoogleService was supended
    @Override
    public void onConnectionSuspended(int i) {
        showMessageConnectionSuspended();
    }

    //activate when connection to the GoogleService is failed
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showMessageConnectionFailed();
    }

    //activate when location of the user have been changed
    @Override
    public void onLocationChanged(Location location) {
        showKilometers(location);
    }

    //activate when gps is set off.
    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                Toast.makeText(this, "gps ready", Toast.LENGTH_LONG).show();
                GPSready = true;
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                startLocationUpdates();
                break;
        }
    }

    //listener for all smartphone buttons
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            ImageView imageView = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_overflow);
            setOnPupMenu(imageView);
        }
        return super.onKeyUp(keyCode, event);
    }

    //activate when map is ready
    @Override
    public void onMapReady(GoogleMap var1) {
        myGoogleMap = new ClassMyGoogleMaps(var1, coordList);
        googleMap = var1;
        sydney = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        googleMap.getCameraPosition();
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            public void onMapLoaded() {
                ActivityMain.this.googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                    public void onSnapshotReady(Bitmap bitmap) {
                        // Write image to disk
                        if (fragmentMain.isRestartReady()) {
                            alertDialogMap(bitmap);
                            fragmentMain.setRestartFalse();
                        }
                    }
                });
            }
        });
    }

    //save last state of fragment
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            alertDialogExit();
        } else {
            backStack();
        }
    }

    //save last state of fragment
    private void backStack() {
        final ImageView imageViewPostion = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_position);
        final ImageView imageViewMap = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_map);
        imageViewMap.setVisibility(View.INVISIBLE);
        imageViewPostion.setVisibility(View.VISIBLE);
        getFragmentManager().popBackStack();
    }

    //load last state of fragment
    public void loadStack() {
        final ImageView imageViewPostion = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_position);
        final ImageView imageViewMap = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_map);
        imageViewMap.setVisibility(View.VISIBLE);
        imageViewPostion.setVisibility(View.INVISIBLE);
    }

    //load FragmentMain
    private void setMainFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragmentMain).commit();
    }

    //load MapFragment(for GoogleMap)
    public void setMapFragment() {
        mMapFragment = MapFragment.newInstance(getOption());
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim, R.animator.exit_anim);
        fragmentTransaction.add(R.id.fragmentContainer, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
    }

    private GoogleMapOptions getOption() {
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        return options;
    }

    //set actionbar for main activity
    private void setCustomActionBar() {
        actionBarMain = getSupportActionBar();
        actionBarMain.setDisplayShowHomeEnabled(false);
        actionBarMain.setDisplayShowTitleEnabled(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        viewCustomActionBar = layoutInflater.inflate(R.layout.custom_actionbar, null);
        final ImageView imageViewPostion = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_position);
        final ImageView imageViewOverflow = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_overflow);
        final ImageView imageViewMap = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_map);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backStack();
            }
        });
        imageViewOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOnPupMenu(v);
            }
        });
        imageViewPostion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.imageView_position:
                        setMapFragment();
                        loadStack();
                        break;
                }
            }
        });
        actionBarMain.setCustomView(viewCustomActionBar);
        actionBarMain.setDisplayShowCustomEnabled(true);
    }

    //add menu to image in actionbar
    private void setOnPupMenu(View view) {
        Context context = getApplicationContext();
        PopupMenu popup = new PopupMenu(context, view);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.history:
                        Intent intentHistory = new Intent(getApplication(), ActivityHistory.class);
                        startActivity(intentHistory);
                        break;
                    case R.id.settings:
                        Intent intentSettings = new Intent(getApplication(), ActivitySettings.class);
                        startActivity(intentSettings);
                        break;
                    case R.id.exit:
                        System.exit(1);
                        break;
                }
                return false;
            }
        });
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

    //show toast when connection to GoogleMap was failed
    private void showMessageConnectionFailed() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.problemConnection_GoogleService);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    //show kilometers
    private void showKilometers(Location location) {
        location.setSpeed(15);
        if (location != null && location.getSpeed() > (float) 0.5 && fragmentMain.ifRunnerIsReady() && GPSready) {
            sydney = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            updatePosition++;
            if (updatePosition == 1) {
                //todo: check if this is needed
                //lastLocation.set(location);
            } else {
                myGoogleMap.getPoint(location);
                kilometry = round(kilometry + lastLocation.distanceTo(location) / 1000, 2);
                speed = round(location.getSpeed() * (float) 3.6, 2);
                calory = round(calory + calculateCalory(speed), 2);
                fragmentMain.getPredkosc(speed);
                fragmentMain.getDistance(kilometry);
                fragmentMain.getCalory(calory);
                lastLocation.set(location);
            }
        }
    }

    private float round(double f, int places) {
        float temp = (float) (f * (Math.pow(10, places)));
        temp = (Math.round(temp));
        temp = temp / (int) (Math.pow(10, places));
        return temp;
    }

    //reset kilometers
    public void resetKilometry() {
        kilometry = 0;
        calory = 0;
    }

    //calculate burned calory
    private float calculateCalory(float speed) {
        float wynik = 0;
        if (speed < 5) {
            wynik = (float) 0.23;
        } else if (speed < 10) {
            wynik = (float) 0.83;
        } else if (speed > 10) {
            wynik = (float) 0.75;
        }
        return wynik;
    }

    //show toast when connection to GoogleMap was susspended
    private void showMessageConnectionSuspended() {
        Context mContext = getApplicationContext();
        CharSequence text = getString(R.string.disconnected_GoogleService);
        Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    //alertdialog for Exit form application
    private void alertDialogExit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(getString(R.string.exit));
        alertDialog.setMessage(getString(R.string.messageExit));
        alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopLocationUpdates();
                System.exit(1);
            }
        });
        alertDialog.setNegativeButton(getString(R.string.no), null);
        alertDialog.show();
    }

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

    //connect to the Google Services
    private void connectGoogleService() {
        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    //calculate last know position on Map
    private void calculatePosition() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    //add setting to the Google Services
    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    //start Location update
    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    //stop Location update
    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    //show this dialog when gps is off.
    private void checkGPSsettings() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(this);
        boolean isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsOn) {
            alertDialogGps();
        }
    }

    //dialog for gps off.
    private void alertDialogGps() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_DARK);
        alertDialog.setTitle(getString(R.string.GPStitle));
        alertDialog.setMessage(getString(R.string.GPSmessage));
        alertDialog.setPositiveButton(getString(R.string.Settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
                stopLocationUpdates();
            }
        });
        alertDialog.setNeutralButton(getString(R.string.ignor), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    //take screenshot form googleMap
    private void getSnapshot(Bitmap bitmap) {
        try {
            fileOutputStream = new FileOutputStream(ClassLoadingImage.pathForImage());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //save value in database
    private void saveDatabase() {
        String pathForImage = ClassLoadingImage.pathForImage().getAbsolutePath();
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        ClassFeedReaderDbHelper myDatabase = new ClassFeedReaderDbHelper(this);
        SQLiteDatabase database = myDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID, "1");
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_CALORY, calory + " kcal");
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_DISTANCE, kilometry + " km");
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_DATE, getRealTime().getDate());
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_SPEED, speed + " km/h");
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_TIME, getRealTime().getTime());
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_TIME_PERIOD, fragmentMain.setPeriodTime());
        values.put(ClassFeedReaderContract.FeedEntry.COLUMN_NAME_SCREENSHOOT, pathForImage);
        database.insert(ClassFeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    private void resetPeriodTime() {
        if (chronometer != null) {
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
    }

    //get real time
    private ClassActualTime getRealTime() {
        String time;
        String date;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        time = simpleDateFormatTime.format(c.getTime());
        date = simpleDateFormatDate.format(c.getTime());
        ClassActualTime classActualTime = new ClassActualTime(time, date);
        return classActualTime;
    }

    @Override
    public void onMyLocationChange(Location location) {
        myGoogleMap.folowGpsPosition(location);
        myGoogleMap.drawRoute();
    }
}





