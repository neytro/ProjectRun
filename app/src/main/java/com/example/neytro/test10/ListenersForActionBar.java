package com.example.neytro.test10;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.neytro.test10.Activites.ActivityHistory;
import com.example.neytro.test10.Activites.ActivitySettings;
import com.example.neytro.test10.Fragments.FragmentGoogleMap;
/**
 * Created by Neytro on 2015-10-25.
 */
public class ListenersForActionBar implements View.OnClickListener, KeyEvent.Callback {
    private ImageView imageViewPosition;
    private ImageView imageViewOverflow;
    private ImageView imageViewMap;
    private FragmentGoogleMap fragmentGoogleMap;
    private Context context;

    public void activateListners(View view, Context context) {
        addReferences(view);
        this.context = context;
        imageViewMap.setOnClickListener(this);
        imageViewOverflow.setOnClickListener(this);
        imageViewPosition.setOnClickListener(this);

    }

    private void addReferences(View view) {
        imageViewMap = (ImageView) view.findViewById(R.id.imageView_map);
        imageViewOverflow = (ImageView) view.findViewById(R.id.imageView_overflow);
        imageViewPosition = (ImageView) view.findViewById(R.id.imageView_position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_position:
                showGoogleMap();
                showStopWatchImage();
                break;
            case R.id.imageView_overflow:
                setOnPupMenu(v);
                break;
            case R.id.imageView_map:
                backToStack();
                showPositionImage();
                break;
        }
    }

    private void showGoogleMap() {
        fragmentGoogleMap = new FragmentGoogleMap(context);
        fragmentGoogleMap.setMapFragment();
    }

    private void showStopWatchImage() {
        imageViewMap.setVisibility(View.VISIBLE);
        imageViewPosition.setVisibility(View.INVISIBLE);
    }

    private void setOnPupMenu(View view) {
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
        Intent intentHistory = new Intent(context, ActivityHistory.class);
        context.startActivity(intentHistory);
    }

    private void showSettings() {
        Intent intentSettings = new Intent(context, ActivitySettings.class);
        context.startActivity(intentSettings);
    }

    private void backToStack() {
        fragmentGoogleMap.addToBackStack();
    }

    private void alertDialogExit() {
        AlertDialogs dialogExit = new AlertDialogs(context);
        dialogExit.alertDialogExit();
    }

    private void showPositionImage() {
        imageViewMap.setVisibility(View.INVISIBLE);
        imageViewPosition.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("numer 1", "");
        return false;
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
        return false;
    }
}
