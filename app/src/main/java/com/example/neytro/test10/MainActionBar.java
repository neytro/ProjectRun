package com.example.neytro.test10;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
/**
 * Created by Neytro on 2015-10-25.
 */
public class MainActionBar {
    private View viewCustomActionBar;
    private android.support.v7.app.ActionBar actionBarMain;
    private ListenersForActionBar listener;
    private Context context;

    public MainActionBar(Context context, ActionBar actionBarMain) {
        this.actionBarMain = actionBarMain;
        this.context = context;
    }

    public void displayActionBar() {
        setOptionForActionBar();
    }

    private void setOptionForActionBar() {
        actionBarMain.setDisplayShowHomeEnabled(false);
        actionBarMain.setDisplayShowTitleEnabled(false);
        actionBarMain.setCustomView(getCustomView(context));
        actionBarMain.setDisplayShowCustomEnabled(true);
    }

    private View getCustomView(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        viewCustomActionBar = layoutInflater.inflate(R.layout.custom_actionbar, null);
        addListeners();
        return viewCustomActionBar;
    }

    private void addListeners() {
        listener = new ListenersForActionBar();
        listener.activateListners(viewCustomActionBar);
    }
}
