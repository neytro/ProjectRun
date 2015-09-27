package com.example.neytro.test10;
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

import static com.example.neytro.test10.R.id.textViewDistance;
public class MainFragment extends Fragment implements Chronometer.OnChronometerTickListener {
    private View viewMainFragment;
    private Button buttonRestart;
    private Button buttonStart;
    private Button buttonResume;
    private Button buttonStop;
    private TextView textViewOdleglosc;
    private ProgressBar progressBarRuchomy;
    private ProgressBar progressBarStaly;
    private Chronometer chronometer;
    private String periodTime;
    private boolean isMapReady = false;
    private boolean isRunnerIsReady = false;
    private boolean isRestartReady = false;
    private int updatePosition = 0;
    private int whichCopy = 0;
    private int liczGodziny = 0;
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
            chronometer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogParameters();
                }
            });
            textViewOdleglosc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogParameters();
                }
            });
            buttonStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRing();
                    timerStart();
                }
            });
            buttonResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerResume();
                    showRing();
                }
            });
            buttonRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerRestart();
                    hideRinge();
                }
            });
            buttonStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerStop();
                    hideRinge();
                }
            });
            // Inflate the layout for this fragment
            return viewMainFragment;
        } else
            return viewMainFragment;
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
        textViewOdleglosc = (TextView) viewMainFragment.findViewById(textViewDistance);
        progressBarRuchomy = (ProgressBar) viewMainFragment.findViewById(R.id.progressBarRuchomy);
        progressBarStaly = (ProgressBar) viewMainFragment.findViewById(R.id.progressBarStaly);
        textViewOdleglosc.setPaintFlags(textViewOdleglosc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void alertDialogParameters() {
        AlertDialog.Builder alertDialogParameters = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
        alertDialogParameters
                .setTitle(getString(R.string.dialogTitlePicker))
                .setItems(R.array.dialog_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        whichCopy = which;
                        switch (which) {
                            case 0:
                                textViewOdleglosc.setText(String.valueOf(round(distance, 2)) + " km");
                                return;
                            case 1:
                                textViewOdleglosc.setText(String.valueOf(round(speed, 2)) + " km/h");
                                return;
                            case 2:
                                textViewOdleglosc.setText(String.valueOf(round(calory, 2)) + " kcal");
                                return;
                            default:
                                textViewOdleglosc.setText(String.valueOf(round(distance, 2)) + " km");
                                return;
                        }
                    }
                })
                .show();
    }

    //show blue ring(round progressbar)
    public void showRing() {
        progressBarStaly.setVisibility(View.INVISIBLE);
        progressBarRuchomy.setVisibility(View.VISIBLE);
    }

    //hide blue ring(round progressbar)
    private void hideRinge() {
        progressBarStaly.setVisibility(View.VISIBLE);
        progressBarRuchomy.setVisibility(View.INVISIBLE);
    }

    //check if stopwatch is ready
    public boolean ifRunnerIsReady() {
        return isRunnerIsReady;
    }

    //check if GoogleMap is ready
    public boolean isMapReady() {
        return isMapReady;
    }
    public boolean isRestartReady() {
        return isRestartReady;
    }
    public void setRestartFalse(){
        isRestartReady = false;
    }

    //calcualte hour for stopwatch
    private void addHour() {
        liczGodziny++;
        if (liczGodziny < 10) {
            chronometer.setFormat("0" + String.valueOf(liczGodziny) + ":%s");
        } else chronometer.setFormat(String.valueOf(liczGodziny) + ":%s");
    }

    //add colour for ring
    private void changeColorRing() {
        progressBarStaly.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), PorterDuff.Mode.MULTIPLY);
        progressBarRuchomy.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), PorterDuff.Mode.MULTIPLY);
    }

    //add distance
    public void getDistance(float _distance) {
        distance = _distance;
        if (whichCopy == 0) {
            textViewOdleglosc.setText(String.valueOf(round(_distance, 2)) + " km");
        }
    }

    //add speed
    public void getPredkosc(float _speed) {
        speed = _speed;
        if (whichCopy == 1) {
            textViewOdleglosc.setText(String.valueOf(round(_speed, 2)) + " km/h");
        }
    }

    //add calory
    public void getCalory(float _calory) {
        calory = _calory;
        if (whichCopy == 2) {
            textViewOdleglosc.setText(String.valueOf(round(_calory, 2)) + " kcal");
        }
    }

    private float round(double f, int places) {
        float temp = (float) (f * (Math.pow(10, places)));
        temp = (Math.round(temp));
        temp = temp / (int) (Math.pow(10, places));
        return temp;
    }

    //start stopwatch
    public void timerStart() {
        isRunnerIsReady = true;
        isMapReady = true;
        chronometer.start();
        chronometer.setBase(SystemClock.elapsedRealtime());
        buttonStop.setVisibility(View.VISIBLE);
        buttonStart.setVisibility(View.INVISIBLE);
    }

    //stop stopwatch
    private void timerStop() {
        periodTime = chronometer.getText().toString();
        isRunnerIsReady = false;
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
        isRunnerIsReady = true;
        chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
        chronometer.start();
        buttonStop.setVisibility(View.VISIBLE);
        buttonResume.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);
    }

    //restart stopwatch
    private void timerRestart() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.setMapFragment();
        mainActivity.loadStack();
        mainActivity.resetKilometry();
        buttonStart.setVisibility(View.VISIBLE);
        buttonResume.setVisibility(View.INVISIBLE);
        buttonRestart.setVisibility(View.INVISIBLE);
        lastPause = SystemClock.elapsedRealtime();
        textViewOdleglosc.setText("0 km");
        liczGodziny = 0;
        chronometer.stop();
        chronometer.setFormat("00:%s");
        isMapReady = false;
        isRestartReady=true;
    }
}

