package com.example.neytro.test10.Fragments;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neytro.test10.Activites.ActivityMain;
import com.example.neytro.test10.FragmentViewValue;
import com.example.neytro.test10.R;
import com.example.neytro.test10.RunningValues;
public class FragmentMain extends Fragment implements Chronometer.OnChronometerTickListener, FragmentViewValue {
    private final int DISTANCE = 0;
    private final int SPEED = 1;
    private final int CALORY = 2;
    private View viewMainFragment;
    private Button buttonRestart;
    private Button buttonStart;
    private Button buttonResume;
    private Button buttonStop;
    private TextView textViewDistance;
    private ProgressBar progressBarMoveable;
    private ProgressBar progressBarConstant;
    private Chronometer chronometer;
    private String periodTime;
    private boolean isMapReady = false;
    private boolean isRunnerReady = false;
    private boolean isRestartReady = false;
    private int updatePosition = 0;
    private int whichCategory = 0;
    private int countHours = 0;
    private long lastPause;
    private float distance = 0;
    private float calory = 0;
    private float speed = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (viewMainFragment == null) {
            viewMainFragment = inflater.inflate(R.layout.fragment_main, container, false);
            setData();
            changeColorRing();
            addListeners();
            return viewMainFragment;
        } else
            return viewMainFragment;
    }

    private void addListeners() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.chronometer:
                        alertDialogParameters();
                        break;
                    case R.id.textViewDistance:
                        alertDialogParameters();
                        break;
                    case R.id.btnStart:
                        showRing();
                        timerStart();
                        break;
                    case R.id.btnResume:
                        timerResume();
                        showRing();
                        break;
                    case R.id.btnRestart:
                        timerRestart();
                        hideRinge();
                        break;
                    case R.id.btnStop:
                        timerStop();
                        hideRinge();
                        break;
                }
            }
        };
        activeListeners(listener);
    }

    private void activeListeners(View.OnClickListener listener) {
        chronometer.setOnClickListener(listener);
        textViewDistance.setOnClickListener(listener);
        buttonStart.setOnClickListener(listener);
        buttonResume.setOnClickListener(listener);
        buttonRestart.setOnClickListener(listener);
        buttonStop.setOnClickListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {
        addHour();
    }

    private void setData() {
        chronometer = (Chronometer) viewMainFragment.findViewById(R.id.chronometer);
        buttonStart = (Button) viewMainFragment.findViewById(R.id.btnStart);
        buttonRestart = (Button) viewMainFragment.findViewById(R.id.btnRestart);
        buttonResume = (Button) viewMainFragment.findViewById(R.id.btnResume);
        buttonStop = (Button) viewMainFragment.findViewById(R.id.btnStop);
        textViewDistance = (TextView) viewMainFragment.findViewById(R.id.textViewDistance);
        progressBarMoveable = (ProgressBar) viewMainFragment.findViewById(R.id.progressBarRuchomy);
        progressBarConstant = (ProgressBar) viewMainFragment.findViewById(R.id.progressBarStaly);
        textViewDistance.setPaintFlags(textViewDistance.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void alertDialogParameters() {
        AlertDialog.Builder alertDialogParameters = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        alertDialogParameters
                .setTitle(getString(R.string.dialogTitlePicker))
                .setItems(R.array.dialog_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whichCategory = which;
                        switch (which) {
                            case DISTANCE:
                                textViewDistance.setText(String.valueOf(distance) + getString(R.string.unitKilometer));
                                return;
                            case SPEED:
                                textViewDistance.setText(String.valueOf(speed) + getString(R.string.unitKilometerPerHour));
                                return;
                            case CALORY:
                                textViewDistance.setText(String.valueOf(calory) + getString(R.string.kiloCalory));
                                return;
                            default:
                                textViewDistance.setText(String.valueOf(distance) + getString(R.string.unitKilometer));
                                return;
                        }
                    }
                })
                .show();
    }

    //show blue ring(round progressbar)
    public void showRing() {
        progressBarConstant.setVisibility(View.INVISIBLE);
        progressBarMoveable.setVisibility(View.VISIBLE);
    }

    //hide blue ring(round progressbar)
    private void hideRinge() {
        progressBarConstant.setVisibility(View.VISIBLE);
        progressBarMoveable.setVisibility(View.INVISIBLE);
    }

    //check if stopwatch is ready
    public boolean isButtonStartClicked() {
        return isRunnerReady;
    }

    public boolean isRestartReady() {
        return isRestartReady;
    }

    public void setRestartFalse() {
        isRestartReady = false;
    }

    //calcualte hour for stopwatch
    private void addHour() {
        final int END_OF_CHRONOMETER = 10;
        countHours++;
        if (countHours < END_OF_CHRONOMETER) {
            chronometer.setFormat("0" + String.valueOf(countHours) + ":%s");
        } else chronometer.setFormat(String.valueOf(countHours) + ":%s");
    }

    //add colour for ring
    private void changeColorRing() {
        progressBarConstant.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), PorterDuff.Mode.MULTIPLY);
        progressBarMoveable.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), PorterDuff.Mode.MULTIPLY);
    }

 /*   //todo: add km to reseources
    //add distance
    public void getDistance(float distance) {
        this.distance = distance;
        if (whichCategory == 0) {
            textViewDistance.setText(String.valueOf(distance) + getString(R.string.unitKilometer));
        }
    }

    //todo: add km/h to reseources
    //add speed
    public void getPredkosc(float speed) {
        this.speed = speed;
        if (whichCategory == 1) {
            textViewDistance.setText(String.valueOf(speed) + getString(R.string.unitKilometerPerHour));
        }
    }*/


    /*//todo: add kcal to reseources
    //add calory
    public void getCalory(float calory) {
        this.calory = calory;
        if (whichCategory == 2) {
            textViewDistance.setText(String.valueOf(calory) + getString(R.string.kiloCalory));
        }
    }*/

    //start stopwatch
    public void timerStart() {
        isRunnerReady = true;
        isMapReady = true;
        chronometer.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        buttonStop.setVisibility(View.VISIBLE);
        buttonStart.setVisibility(View.INVISIBLE);
    }

    //stop stopwatch
    private void timerStop() {
        periodTime = chronometer.getText().toString();
        isRunnerReady = false;
        updatePosition = 0;
        lastPause = SystemClock.elapsedRealtime();
        chronometer.stop();
        buttonStop.setVisibility(View.INVISIBLE);
        buttonResume.setVisibility(View.VISIBLE);
        buttonRestart.setVisibility(View.VISIBLE);
    }

    public String setPeriodTime() {
        return periodTime;
    }

    //resume stopwatch
    private void timerResume() {
        isRunnerReady = true;
        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
        chronometer.start();
        buttonStop.setVisibility(View.VISIBLE);
        buttonResume.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);
    }

    //restart stopwatch
    private void timerRestart() {
        ActivityMain activityMain = (ActivityMain) getActivity();
        //activityMain.setMapFragment();
        activityMain.hidePositionImageAndShowMapImage();
        buttonStart.setVisibility(View.VISIBLE);
        buttonResume.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);
        lastPause = SystemClock.elapsedRealtime();
        textViewDistance.setText("0 km");
        countHours = 0;
        chronometer.stop();
        chronometer.setFormat("00:%s");
        isMapReady = false;
        isRestartReady = true;
    }

    @Override
    public void getRunningValue(RunningValues values) {
        calory = values.getCalory();
        speed = values.getSpeed();
        distance = values.getDistance();
        Toast.makeText(getActivity(), "maszyna ruszyla", Toast.LENGTH_LONG).show();
        switch (whichCategory) {
            case 0:
                textViewDistance.setText(String.valueOf(distance) + getString(R.string.unitKilometer));
                break;
            case 1:
                textViewDistance.setText(String.valueOf(speed) + getString(R.string.unitKilometerPerHour));
                break;
            case 2:
                textViewDistance.setText(String.valueOf(calory) + getString(R.string.kiloCalory));
                break;
        }
    }
}

