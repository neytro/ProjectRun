package com.example.neytro.test10.Activites;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.neytro.test10.ActualTime;
import com.example.neytro.test10.DbColumns;
import com.example.neytro.test10.DbCreate;
import com.example.neytro.test10.Fragments.FragmentMain;
import com.example.neytro.test10.Location.GoogleMapsItems;
import com.example.neytro.test10.Location.GoogleServiceConnection;
import com.example.neytro.test10.LoadingImage;
import com.example.neytro.test10.MainActionBar;
import com.example.neytro.test10.Person;
import com.example.neytro.test10.R;
import com.example.neytro.test10.SpeedMotion;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
public class ActivityMain extends ActionBarActivity implements

        OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener {
    private final float MIN_SPEED = (float) 0.5;
    private final float KILOMETER_FACTOR = (float) 3.6;
    private SpeedMotion speedMotion = new Person();
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private android.support.v7.app.ActionBar actionBarMain;
    private View viewCustomActionBar;
    private View viewFragmentmain;
    private FragmentMain fragmentMain = new FragmentMain();
    private ArrayList<LatLng> coordinateList = new ArrayList<LatLng>();
    private LatLng coordinates;
    private MapFragment mMapFragment;
    private FileOutputStream fileOutputStream;
    private Chronometer chronometer;
    private GoogleMapsItems googleMapsItems;
    private ImageView imageViewPosition;
    private ImageView imageViewOverflow;
    private ImageView imageViewMap;
    private MainActionBar mainActionBar;
    private boolean isGPSready = false;
    private int numberOfLocationPoint = 0;
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
        //connectGoogleService();
    }

    //load FragmentMain
    private void setMainFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragmentMain).commit();
    }

    //set actionbar for main activity
    private void setCustomActionBar() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.imageView_position:
                        setMapFragment();
                        hidePositionImageAndShowMapImage();
                        break;
                    case R.id.imageView_overflow:
                        setOnPupMenu(v);
                        break;
                    case R.id.imageView_map:
                        saveLastViewOfFragment();
                        break;
                }
            }
        };
        setOptionForActionBar();
        setListeners(listener);
        displayActionBar();
    }

    private void setOptionForActionBar() {
        actionBarMain = getSupportActionBar();
        actionBarMain.setDisplayShowHomeEnabled(false);
        actionBarMain.setDisplayShowTitleEnabled(false);
    }

    private void setListeners(View.OnClickListener listener) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        viewCustomActionBar = layoutInflater.inflate(R.layout.custom_actionbar, null);
        imageViewPosition = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_position);
        imageViewOverflow = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_overflow);
        imageViewMap = (ImageView) viewCustomActionBar.findViewById(R.id.imageView_map);
        imageViewMap.setOnClickListener(listener);
        imageViewOverflow.setOnClickListener(listener);
        imageViewPosition.setOnClickListener(listener);
    }

    private void displayActionBar() {
        actionBarMain.setCustomView(viewCustomActionBar);
        actionBarMain.setDisplayShowCustomEnabled(true);
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

    //load last state of fragment
    public void hidePositionImageAndShowMapImage() {
        imageViewMap.setVisibility(View.VISIBLE);
        imageViewPosition.setVisibility(View.INVISIBLE);
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
                        showHistory();
                        break;
                    case R.id.settings:
                        showSettings();
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

    private void showHistory() {
        Intent intentHistory = new Intent(getApplication(), ActivityHistory.class);
        startActivity(intentHistory);
    }

    private void showSettings() {
        Intent intentSettings = new Intent(getApplication(), ActivitySettings.class);
        startActivity(intentSettings);
    }

    //save last state of fragment
    private void saveLastViewOfFragment() {
        imageViewMap.setVisibility(View.INVISIBLE);
        imageViewPosition.setVisibility(View.VISIBLE);
        getFragmentManager().popBackStack();
    }

   /* private void connectGoogleService() {
        googleApiClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).build();
        googleApiClient.connect();
    }*/

    //activate when GoogleService is connected
  /* @Override
    public void onConnected(Bundle bundle) {
        //checkGPSsettings();
        // createLocationRequest();
        // getLastLocation();
        //startLocationUpdates();
    }*/

    //show this dialog when gps is off.
  /*  private void checkGPSsettings() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager.addGpsStatusListener(this);
        boolean isGpsOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGpsOn) {
            alertDialogGps();
        }
    }*/

    //dialog for gps off.
 /*   private void alertDialogGps() {
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
    }*/

    //stop Location update
    /*private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }*/

   /* private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLastLocation() {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }*/

  /*  @Override
    public void onLocationChanged(Location location) {
        calculateValues(location);
    }*/

    //show kilometers
  /*  private void calculateValues(Location location) {
        //todo: value for testing
        location.setSpeed(15);
        if (location != null && location.getSpeed() > MIN_SPEED && fragmentMain.isButtonStartClicked() && isGPSready) {
            coordinates = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            numberOfLocationPoint++;
            if (numberOfLocationPoint != 1) {
                googleMapsItems.getPoint(location);
                calculateKilometers(location);
                calculateSpeedAndCalory(location);
                lastLocation.set(location);
            }
        }
    }

    private void calculateKilometers(Location location) {
        kilometers = round(kilometers + lastLocation.distanceTo(location) / 1000, 2);
        // fragmentMain.getDistance(kilometers);
    }

    private void calculateSpeedAndCalory(Location location) {
        float speedInKilometers = location.getSpeed() * KILOMETER_FACTOR;
        speed = round(speedInKilometers, 2);
        // fragmentMain.getPredkosc(speed);
        calculateCalory(speed);
    }

    private void calculateCalory(float speed) {
        float wynik = 0;
        if (speed < 5) {
            wynik = speedMotion.slowSpeed();
        } else if (speed < 10) {
            wynik = speedMotion.middleSpeed();
        } else if (speed > 10) {
            wynik = speedMotion.fastSpeed();
        }
        calory = round(calory + wynik, 2);
        //fragmentMain.getCalory(calory);
    }

    private float round(double f, int places) {
        float temp = (float) (f * (Math.pow(10, places)));
        temp = (Math.round(temp));
        temp = temp / (int) (Math.pow(10, places));
        return temp;
    }*/
/*
    @Override
    public void onConnectionSuspended(int i) {
        showMessageConnectionSuspended();
    }

    private void showMessageConnectionSuspended() {
        Context mContext = getApplicationContext();
        CharSequence text = getString(R.string.disconnected_GoogleService);
        Toast toast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        showMessageConnectionFailed();
    }

    private void showMessageConnectionFailed() {
        Context context = getApplicationContext();
        CharSequence text = getString(R.string.problemConnection_GoogleService);
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }*/

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

    //activate when gps is set off.
    /*@Override
    public void onGpsStatusChanged(int event) {
        *//*switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                Toast.makeText(this, "gps ready", Toast.LENGTH_LONG).show();
                isGPSready = true;
                break;
            case GpsStatus.GPS_EVENT_STOPPED:
                startLocationUpdates();
                break;
        }*//*
    }*/

    //listener for all smartphone buttons
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            setOnPupMenu(imageViewOverflow);
        }
        return super.onKeyUp(keyCode, event);
    }

    //activate when map is ready
    @Override
    public void onMapReady(GoogleMap var1) {
        //googleMapsItems = new GoogleMapsItems(var1, coordinateList);
        settingsForMap(var1);
        if (fragmentMain.isRestartReady()) {
            googleMap.setOnMyLocationChangeListener(null);
            googleMapsItems.drawRouteAndaddMarker();
            googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                public void onMapLoaded() {
                    ActivityMain.this.googleMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
                        public void onSnapshotReady(Bitmap bitmap) {
                            // Write image to disk
                            alertDialogMap(bitmap);
                            fragmentMain.setRestartFalse();
                        }
                    });
                }
            });
        }
    }

    private void settingsForMap(GoogleMap map) {
        final int ZOOM_POSITION = 14;
        coordinates = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(this);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, ZOOM_POSITION));
        googleMap.getCameraPosition();
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
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
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            alertDialogExit();
        } else {
            saveLastViewOfFragment();
        }
    }

    private GoogleMapOptions getOption() {
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);
        return options;
    }

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

    @Override
    public void onMyLocationChange(Location location) {
        googleMapsItems.drawRouteAndaddMarker();
    }
}






